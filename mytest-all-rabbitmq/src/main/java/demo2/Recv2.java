package demo2;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 没有关闭Connection与各个Channel
 */
public class Recv2 {
  private final static String QUEUE_NAME = "hellohh";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    Connection connection = factory.newConnection();

    Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);

    System.out.println("recv ok!");
  }
}
