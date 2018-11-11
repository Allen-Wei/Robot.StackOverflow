package net.alanwei.robot;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import net.alanwei.robot.models.QuestionJobResult;
import org.bson.Document;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
@ComponentScan
public class App {
    public static void main(String[] args) throws Throwable {

        MongoClient client = MongoClients.create("mongodb://root:654231@192.168.1.111:32769");
        MongoDatabase db = client.getDatabase("stackoverflow");
        MongoCollection<Document> questions = db.getCollection("questions");
        MongoCollection<Document> answers = db.getCollection("answers");
        Gson gson = new Gson();

        RabbitMqUse mqUse = new RabbitMqUse();
        mqUse.use("JavaConsumerMongoDB", "amqp://root:654231@47.52.157.46:32825/FILES", channel -> {
            channel.basicQos(0, 1, false);

            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String json = new String(body, StandardCharsets.UTF_8);
                    System.out.println("receive: " + json);
                    QuestionJobResult result = gson.fromJson(json, QuestionJobResult.class);
                    if (result.getQuestion() != null) {
                        Document docQuestion = new Document()
                                .append("id", result.getQuestion().getId())
                                .append("name", result.getQuestion().getName())
                                .append("link", result.getQuestion().getLink())
                                .append("asked", result.getQuestion().getAsked())
                                .append("viewed", result.getQuestion().getViewed())
                                .append("upVote", result.getQuestion().getUpVote())
                                .append("favorite", result.getQuestion().getFavorite())
                                .append("postContent", result.getQuestion().getPostContent())
                                .append("tags", result.getQuestion().getTags());
                        questions.insertOne(docQuestion);
                    }
                    if (result.getAnswers() != null && result.getAnswers().size() > 0) {
                        List<Document> docAnswers = result.getAnswers()
                                .stream()
                                .map(answer -> new Document()
                                        .append("id", answer.getId())
                                        .append("questionId", answer.getQuestionId())
                                        .append("upVote", answer.getUpVote())
                                        .append("bountyAward", answer.getBountyAward())
                                        .append("postContent", answer.getPostContent())
                                        .append("answerTime", answer.getAnswerTime())
                                        .append("userLink", answer.getUserLink())
                                        .append("userAvatar", answer.getUserAvatar())
                                )
                                .collect(Collectors.toList());
                        answers.insertMany(docAnswers);
                    }

                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };
            channel.basicConsume("queue-question-result", false, consumer);
        });

    }
}
