package net.alanwei.robots.implementations;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import net.alanwei.robots.http.QuestionRequest;
import net.alanwei.robots.interfaces.*;
import net.alanwei.robots.models.*;
import net.alanwei.robots.utils.Util;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class RabbitMqQuestionJobConsumer implements QuestionJobConsumer {

    private static final String QUEUE_NAME = "queue1";
    private static final String EXCHANGE_NAME = "/question/consume";
    private final RabbitMqUse mqUse;
    @Autowired
    private QuestionRequest request;
    @Autowired
    private QuestionParser questionParser;
    @Autowired
    private AnswerParser answerParser;
    @Autowired
    private QuestionJobResultPublish publish;

    public RabbitMqQuestionJobConsumer() {
        this.mqUse = new DefaultRabbitMqUse();
    }

    @Override
    public void consume() {
        this.mqUse.use("amqp://root:654231@47.52.157.46:32825/FILES", channel -> {
            channel.basicQos(1);
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, false, null);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, QUEUE_NAME);

            DefaultConsumer consumerInstance = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                    QuestionJobResult result = new QuestionJobResult();
                    try {
                        String data = new String(body, StandardCharsets.UTF_8);
                        QuestionJob job = Util.fromJson(data, QuestionJob.class);
                        System.out.println("Receive " + job.getQuestionId());

                        QuestionAnswers qa = parseQuestion(job.getQuestionId());

                        result.setQuestionId(job.getQuestionId());
                        result.setAnswers(qa.getAnswers());
                        result.setQuestion(qa.getQuestion());
                    } catch (Throwable ex) {
                        result.setMessage("消费发生异常: " + ex.getMessage());
                    }
                    publish.publish(result);

                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };

            channel.basicConsume(QUEUE_NAME, false, consumerInstance);
        });
    }

    private QuestionAnswers parseQuestion(Long questionId) {
        Document doc = request.requestThenParse(questionId);
        if (doc == null) {
            System.out.println("doc is null");
            return null;
        }

        Question question = questionParser.parse(doc);
        List<Answer> answers = answerParser.parse(doc);

        QuestionAnswers aq = new QuestionAnswers();
        aq.setAnswers(answers);
        aq.setQuestion(question);

        return aq;
    }

}
