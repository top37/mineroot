package demo.http.socket;

import demo.http.filter.HttpRequestMessage;
import demo.http.filter.HttpResponseMessage;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SocketServerHandler extends IoHandlerAdapter{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void messageReceived(IoSession session, Object message) {
		logger.info("收到消息，开始执行messageReceived方法");
		
		HttpRequestMessage httpMessage = (HttpRequestMessage) message;
		logger.info(httpMessage.getContext());
		
		long start=System.currentTimeMillis();
		
		logger.info("session"+session);
		logger.info("message"+message);
		
//		logger.debug(httpMessage.getContext());
//		String retJson = callNfsJsonProcess.onMessage(httpMessage.getContext());
		
//		logger.debug("响应json报文：[{}]",retJson);
		HttpResponseMessage response = new HttpResponseMessage();
		response.appendBody(httpMessage.getContext());
		session.write(response);
		
		long end=System.currentTimeMillis();
		logger.info("SOCKETSERVERHANDLE-TIME:{} ms",(end-start));
	}
	
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
		logger.info("会话超时，关闭当前网络连接");
		session.close(false);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		logger.error("报文接收出现异常", cause);
	}
	
}
