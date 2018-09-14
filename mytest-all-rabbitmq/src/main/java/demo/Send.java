package demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
  private final static String QUEUE_NAME = "hello";
  private final static String QUEUE_NAME1 = "hello1";
  private final static String QUEUE_NAME2 = "hello2";
  private final static String QUEUE_NAME3 = "hello3";
  private final static String message = "Hello World sq!";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    Connection connection = factory.newConnection();

    Channel channel = connection.createChannel();
    Channel channel1 = connection.createChannel();
    Channel channel2 = connection.createChannel();
    Channel channel3 = connection.createChannel();

    System.out.println("channel@"+channel.hashCode()+"\n"+"channel1@"+channel1.hashCode()+"\n"+"channel2@"+channel2.hashCode()+"\n"+"channel3@"+channel3.hashCode());

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    channel1.queueDeclare(QUEUE_NAME1, false, false, false, null);
    channel2.queueDeclare(QUEUE_NAME2, false, false, false, null);
    channel3.queueDeclare(QUEUE_NAME3, false, false, true, null);

    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
    channel1.basicPublish("", QUEUE_NAME1, null, message.getBytes("UTF-8"));
    channel2.basicPublish("", QUEUE_NAME2, null, message.getBytes("UTF-8"));
    channel3.basicPublish("", QUEUE_NAME3, null, message.getBytes("UTF-8"));

    channel.close();channel1.close();channel2.close();channel3.close();
    connection.close();
  }
}