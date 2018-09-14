package demo4;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send4 {
    private static final String EXCHANGE_NAME = "logs";
    private static final String QUEUE_NAME = "hello";
    private static final String dmessage = "sq love hs";
    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.31.128");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        /**
         * 声明了一个交换机type：fanout
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String message = "hehehe";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
        System.out.println(" channel.exchange = logs Sent '" + message + "'");
        /**
         * 声明了一个交换机type:direct( 默认 )
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, dmessage.getBytes("UTF-8"));
        channel.close();
        connection.close();
    }
}
