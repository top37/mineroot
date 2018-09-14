package demo4;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv4 {
    private static final String EXCHANGE_NAME = "logs";
    private static final String QUEUE_NAME = "hello";
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.31.128");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        /**
         * 声明了一个交换机type:direct( 默认 )
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /**
         * 声明了一个交换机type：fanout
         */
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
