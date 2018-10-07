package net.alanwei.robot;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMqUse {
    private Connection connection;
    private com.rabbitmq.client.Channel channel;
    private long waitSecond = 0;

    public void use(String connectionName, String uri, ConsumerAllowException<Channel> consumer) {
        this.close();
        this.init(connectionName, uri);
        try {
            consumer.accept(this.channel);
            waitSecond = 0;
        } catch (Throwable ex) {
            ex.printStackTrace();
            try {
                Thread.sleep((long) Math.pow(waitSecond, 2) * 1000);
                ++waitSecond;
                this.use(connectionName, uri, consumer);
            } catch (Throwable inter) {
            }
        }
    }


    private void init(String connectionName, String uri) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(uri);
            this.connection = factory.newConnection(connectionName);
            this.channel = connection.createChannel();
        } catch (Throwable ex) {
        }
    }

    private void close() {
        try {
            if (this.channel != null) this.channel.close();
            if (this.connection != null) this.connection.close();
        } catch (Throwable ex) {
        }
    }
}
