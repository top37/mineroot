package demo;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 没有关闭Connection与各个Channel
 */
public class Recv {
  private final static String QUEUE_NAME = "hello";
  private final static String QUEUE_NAME1 = "hello1";
  private final static String QUEUE_NAME2 = "hello2";
  private final static String QUEUE_NAME3 = "hello3";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    Connection connection = factory.newConnection();

    Channel channel = connection.createChannel();
    Channel channel1 = connection.createChannel();
    Channel channel2 = connection.createChannel();
    Channel channel3 = connection.createChannel();
    System.out.println("channel@"+channel.hashCode()+"\n"+"channel1@"+channel1.hashCode()+"\n"+"channel2@"+channel2.hashCode());
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    channel1.queueDeclare(QUEUE_NAME1, false, false, false, null);
    channel2.queueDeclare(QUEUE_NAME2, false, false, false, null);
    channel3.queueDeclare(QUEUE_NAME3, false, false, true, null);

    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    Consumer consumer1 = new DefaultConsumer(channel1) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
              throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    Consumer consumer2 = new DefaultConsumer(channel2) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
              throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    Consumer consumer3 = new DefaultConsumer(channel3) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
              throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);
    channel.basicConsume(QUEUE_NAME1, true, consumer1);
    channel.basicConsume(QUEUE_NAME2, true, consumer2);
    channel3.basicConsume(QUEUE_NAME3, true, consumer3);
  }
}
