package demo1;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 没有关闭Connection与各个Channel
 */
public class Recv1 {
  private final static String QUEUE_NAME = "hello";
  private final static String QUEUE_NAME1 = "hello1";
  private final static String QUEUE_NAME2 = "hello2";
  private final static String QUEUE_NAME3 = "hello3";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    Connection connection = factory.newConnection();

    Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
    channel.queueDeclare(QUEUE_NAME2, false, false, false, null);
    channel.queueDeclare(QUEUE_NAME3, false, false, true, null);

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);
    channel.basicConsume(QUEUE_NAME1, true, consumer);
    channel.basicConsume(QUEUE_NAME2, true, consumer);
    channel.basicConsume(QUEUE_NAME3, true, consumer);
  }
}
