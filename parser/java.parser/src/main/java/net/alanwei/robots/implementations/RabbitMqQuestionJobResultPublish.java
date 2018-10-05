package net.alanwei.robots.implementations;

import net.alanwei.robots.interfaces.QuestionJobResultPublish;
import net.alanwei.robots.interfaces.RabbitMqUse;
import net.alanwei.robots.models.QuestionJobResult;
import net.alanwei.robots.utils.Util;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class RabbitMqQuestionJobResultPublish implements QuestionJobResultPublish {
    private static final String QUEUE_NAME = "queue2";
    private static final String EXCHANGE_NAME = "/question/consume";

    private RabbitMqUse mqUse;

    public RabbitMqQuestionJobResultPublish() {
        this.mqUse = new DefaultRabbitMqUse();
    }

    @Override
    public void publish(QuestionJobResult result) {
        this.mqUse.use("amqp://root:654231@47.52.157.46:32825/FILES", channel -> {
            channel.basicQos(1);
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, false, null);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, QUEUE_NAME);

            byte[] data = Util.toJson(result).getBytes(StandardCharsets.UTF_8);
            channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME, null, data);
            System.out.println("publish " + result.getQuestionId());
        });
    }
}
