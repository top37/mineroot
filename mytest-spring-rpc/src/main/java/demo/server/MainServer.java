package demo.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainServer {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
        new ClassPathXmlApplicationContext("test-rpc-server.xml"); 
    }

}
