package net.alanwei.robots.interfaces;

import com.rabbitmq.client.Channel;
import net.alanwei.robots.utils.ConsumerAllowException;

public interface RabbitMqUse {
    void use(String connectionName, String uri, ConsumerAllowException<Channel> consumer);
}
