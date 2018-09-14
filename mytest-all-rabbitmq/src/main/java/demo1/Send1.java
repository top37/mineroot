package demo1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send1 {
  private final static String QUEUE_NAME = "hello";
  private final static String QUEUE_NAME1 = "hello1";
  private final static String QUEUE_NAME2 = "hello2";
  private final static String QUEUE_NAME3 = "hello3";
  private final static String message = "Hello World sq!";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    Connection connection = factory.newConnection();

    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
    channel.queueDeclare(QUEUE_NAME2, false, false, false, null);
    channel.queueDeclare(QUEUE_NAME3, false, false, true, null);

    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
    channel.basicPublish("", QUEUE_NAME1, null, message.getBytes("UTF-8"));
    channel.basicPublish("", QUEUE_NAME2, null, message.getBytes("UTF-8"));
    channel.basicPublish("", QUEUE_NAME3, null, message.getBytes("UTF-8"));

//    channel.close();
//    connection.close();
  }
}