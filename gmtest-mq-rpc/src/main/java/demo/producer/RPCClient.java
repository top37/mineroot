package demo.producer;

import com.rabbitmq.client.ConnectionFactory;

import com.rabbitmq.client.Connection;

import com.rabbitmq.client.Channel;

import com.rabbitmq.client.DefaultConsumer;

import com.rabbitmq.client.AMQP;

import com.rabbitmq.client.Envelope;



import java.io.IOException;

import java.util.UUID;

import java.util.concurrent.ArrayBlockingQueue;

import java.util.concurrent.BlockingQueue;

import java.util.concurrent.TimeoutException;



public class RPCClient {
	
  private Connection connection;
  private Channel channel;
  private String requestQueueName = "rpc_queue";
  private String replyQueueName;

  public RPCClient() throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    //创建一个TCP连接
    connection = factory.newConnection();
    //创建一个信道  
    channel = connection.createChannel();
    //为每一个客户端获取一个随机的回调队列
    replyQueueName = channel.queueDeclare().getQueue();
  }

  public String call(String message) throws IOException, InterruptedException {

    final String corrId = UUID.randomUUID().toString();

    AMQP.BasicProperties props = new AMQP.BasicProperties
            .Builder()
            .correlationId(corrId)
            .replyTo(replyQueueName)
            .build();
    channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

    //阻塞队列，无返回值则阻塞
    final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);
    //等待回调
    channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        if (properties.getCorrelationId().equals(corrId)) {
          response.offer(new String(body, "UTF-8"));
        }
      }
    });
    return response.take();

  }

  public void close() throws IOException {
    connection.close();
  }

  public static void main(String[] argv) {
    RPCClient fibonacciRpc = null;
    String response = null;
    try {
      fibonacciRpc = new RPCClient();
      System.out.println(" [x] Requesting fib(30)");
      //若无返回，会在此处阻塞
      response = fibonacciRpc.call("30");
      System.out.println(" [.] Got '" + response + "'");
    }
    catch  (IOException | TimeoutException | InterruptedException e) {
      e.printStackTrace();
    }

    finally {
      if (fibonacciRpc!= null) {
        try {
          fibonacciRpc.close();
        }
        catch (IOException _ignore) {}
      }

    }

  }

}