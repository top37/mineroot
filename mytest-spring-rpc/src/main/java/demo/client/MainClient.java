package demo.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClient {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-rpc-client.xml");  
        RMICalcService accountService = (RMICalcService) ctx.getBean("myCalcService");  
        int result = accountService.add(1, 2);
        System.out.println(result);
    }

}
