package demo5;

import com.rabbitmq.client.*;

import java.io.IOException;

public class WorkerAutoAck {
    private static final String TASK_QUEUE_NAME = "task_queue";
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.31.128");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        channel.basicQos(1);
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                try {Thread.sleep(10000);}
                catch (Exception e){}
                finally {
                    System.out.println(envelope.getDeliveryTag()+" [x] Done");
                    channel.basicAck(envelope.getDeliveryTag(), true);
                }
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);

    }
}
