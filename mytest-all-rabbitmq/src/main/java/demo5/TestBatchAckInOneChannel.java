package demo5;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class TestBatchAckInOneChannel{
    private static final String QUEUE_NAME = "helloklg";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.31.128");
        Connection connection = factory.newConnection();
        final Channel consumerChannel1 = connection.createChannel();
        consumerChannel1.queueDeclare(QUEUE_NAME, false, false, false, null);
        consumerChannel1.basicQos(8);
        Consumer consumer1 = new DefaultConsumer(consumerChannel1) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.printf("in consumer A (delivery tag is %d): %s\n", envelope.getDeliveryTag(), message);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                }
//                consumerChannel1.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        final Consumer consumer2 = new DefaultConsumer(consumerChannel1) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.printf("in consumer B (delivery tag is %d): %s\n", envelope.getDeliveryTag(), message);
                try {
                    TimeUnit.MILLISECONDS.sleep(1500);
                } catch (InterruptedException e) {
                }
                consumerChannel1.basicAck(envelope.getDeliveryTag(), true);
            }
        };
        new Thread() {
              public void run() {
                  System.out.println("------------in Thread------------");
                  try {
                      Thread.sleep(5000);
                      System.out.println("------------5000 Thread------------");
                      consumerChannel1.basicConsume(QUEUE_NAME, false, consumer2);
                  }catch (Exception e){}
                  }
         }.start();
        System.out.println("------------out Thread------------");
        consumerChannel1.basicConsume(QUEUE_NAME, false, consumer1);
        /**
         * producer
         */
        Channel senderChannel = connection.createChannel();
        for (int i = 0; i < 10; ) {
            String message = "NO. " + ++i;
            TimeUnit.MILLISECONDS.sleep(100);
            senderChannel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        }
        senderChannel.close();
    }
}
