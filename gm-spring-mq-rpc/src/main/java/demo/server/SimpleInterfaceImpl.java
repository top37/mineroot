package demo.server;

import java.util.UUID;

import demo.common.SimpleInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;




/**

 * Created by Daniil Molchanov on 28.02.17.

 */

@Component
public class SimpleInterfaceImpl implements SimpleInterface {

    private final int nodeId = 3;

    private final static Logger log = LoggerFactory.getLogger(SimpleInterfaceImpl.class);

    @Override
    public String doJob(int clientId) {

        String uuid = UUID.randomUUID().toString();

        log.info("UUID {} generated on node {} upon request from client {}", uuid, nodeId, clientId);

        return uuid;

    }

}
