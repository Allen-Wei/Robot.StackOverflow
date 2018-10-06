package net.alanwei.robots.implementations;

import net.alanwei.robots.interfaces.QuestionJobResultPublish;
import net.alanwei.robots.interfaces.RabbitMqUse;
import net.alanwei.robots.models.QuestionJobResult;
import net.alanwei.robots.utils.Util;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class RabbitMqQuestionJobResultPublish implements QuestionJobResultPublish {
    private static final String FANOUT_EXCHANGE_NAME = "/question/result/fanout";
    private static final String DIRECT_EXCHANGE_NAME = "/question/result/direct";
    private static final String DIRECT_QUEUE_NAME = "queue-question-result";

    private RabbitMqUse mqUse;

    public RabbitMqQuestionJobResultPublish() {
        this.mqUse = new DefaultRabbitMqUse();
    }

    @Override
    public void publish(QuestionJobResult result) {
        this.mqUse.use(RabbitMqQuestionJobResultPublish.class.getSimpleName(), "amqp://root:654231@47.52.157.46:32825/FILES", channel -> {
            channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, "fanout", false, false, false, null);

            channel.queueDeclare(DIRECT_QUEUE_NAME, false, false, false, null);
            channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, "direct", false, false, false, null);
            channel.queueBind(DIRECT_QUEUE_NAME, DIRECT_EXCHANGE_NAME, DIRECT_QUEUE_NAME);

            byte[] data = Util.toJson(result).getBytes(StandardCharsets.UTF_8);
            channel.basicPublish(FANOUT_EXCHANGE_NAME, "", null, data);
            channel.basicPublish(DIRECT_EXCHANGE_NAME, DIRECT_QUEUE_NAME, null, data);
            System.out.println("publish " + result.getQuestionId());
        });
    }
}
