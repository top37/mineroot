package demo.http.common;

import java.util.List;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * 
 * @author User
 *
 */
public class NioAcceptorHelper implements InitializingBean {
	@Value("#{env.processorCount}")
	private int processorCount;	//并发数量
	
	private NioSocketAcceptor acceptor;

	private List<IoFilter> ioFilters;

	public NioSocketAcceptor getAcceptor() {
		return acceptor;
	}

	public List<IoFilter> getIoFilters() {
		return ioFilters;
	}

	public void setIoFilters(List<IoFilter> ioFilters) {
		this.ioFilters = ioFilters;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(processorCount > 0) {
			acceptor = new NioSocketAcceptor(processorCount);
		} else {
			acceptor = new NioSocketAcceptor();
		}
		
		if (ioFilters != null) {
			for (IoFilter filter : ioFilters) {
				acceptor.getFilterChain().addLast(filter.getClass().getName(),
						filter);
			}
		}
	}

}
