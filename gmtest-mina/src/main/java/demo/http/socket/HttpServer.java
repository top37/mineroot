package demo.http.socket;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServer {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private NioSocketAcceptor acceptor;
	
	// 监听端口
	private int port;
	
	private boolean isRunning;

	private String encoding;

	public String getEncoding() {
		return encoding;
	}
	
	public void setAcceptor(NioSocketAcceptor acceptor) {
		this.acceptor = acceptor;
	}

	public NioSocketAcceptor getAcceptor() {
		return acceptor;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * 启动HTTP服务端箭筒HTTP请求
	 * 
	 * @param port要监听的端口号
	 * @throws IOException
	 */
	public void bind(){
		try {
			synchronized (this) {
				if (isRunning) {
					return;
				}
				acceptor.bind(new InetSocketAddress(port));
				isRunning = true;
				logger.info("服务已启动，监听端口：{}", port);
			}
		} catch (Exception e) {
			logger.error("端口被占用或其他I/O异常，服务启动失败，请重试", e);
		}
		
	}
	/**
	 * 停止监听HTTP服务
	 */
	public void unbind() {
		synchronized (this) {
			if (!isRunning) {
				logger.info("服务端口：{}已关闭", port);
				return;
			}
			isRunning = false;
			try {
				acceptor.unbind();
				acceptor.dispose();
				logger.info("服务端口：{}已关闭", port);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
