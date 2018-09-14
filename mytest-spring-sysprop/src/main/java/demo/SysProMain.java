package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.util.Properties;

public class SysProMain {


    public static void main(String[] args) throws Exception {
//        Resource resources = new InputStreamResource(new FileInputStream("E:\\ideaTest\\ideajpademo\\mineroot\\mytest-spring-sysprop\\src\\main\\resources\\env.properties"));
        Properties props = System.getProperties();

        props.put("aaa","ahehe");
        props.put("bbb","bhehe");
        props.put("ccc","chehe");
        props.put("env.files","classpath:/dev/*.properties");

        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("service-context.xml");
//        System.out.println(ctx.getBean("systemProperties"));
//        System.out.println("--------------");
//        System.out.println(System.getenv());
        Properties env = (Properties)ctx.getBean("env");
        System.out.println(env);
        TestEnv testEnv = (TestEnv)ctx.getBean("testEnv");
        System.out.println(testEnv.AA);
        ctx.registerShutdownHook();
    }
}
