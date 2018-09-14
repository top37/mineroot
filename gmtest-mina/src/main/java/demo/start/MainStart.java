package demo.start;

import java.io.FileNotFoundException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MainStart {
	public static void main(String[] args) throws FileNotFoundException {
		@SuppressWarnings("resource")
		ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(getContextFilename(args));
		ctx.registerShutdownHook();
	}
	
	public static String getContextFilename(String args[])
	{
		String filename = "/socket-context.xml";
		return filename;
	}
}
