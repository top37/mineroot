package demo2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Send2 {
  private final static String QUEUE_NAME = "hellohh";
//  private final static String message = "Hello World sq!";


  private static String getBigStr(){
//    StringBuilder sb = new StringBuilder();
    String str = "";
    for (int i = 0;i < 100000;i++){
      str+="hssq";
    }
    return str;
  }

  public static void main(String[] argv) throws Exception {

    String message = getBigStr();

    ConnectionFactory factory = new ConnectionFactory();
    Connection connection = factory.newConnection();

    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    for(int i = 0;i<100;i++)
    channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));

    System.out.println("ok!");

//    channel.close();
//    connection.close();
  }
}