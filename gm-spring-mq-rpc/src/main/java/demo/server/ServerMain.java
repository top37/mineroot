package demo.server;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;



/**

 * Created by Daniil Molchanov on 28.02.17.

 */
public class ServerMain {

    private static final Logger log = LoggerFactory.getLogger(ServerMain.class);
    @SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args) {
        log.info("Started");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

    }

}
