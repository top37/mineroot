package demo.http.socket;

import demo.http.filter.HttpRequestDecoder;
import demo.http.filter.HttpResponseEncoder;
import demo.http.filter.HttpResponseMessage;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;


public class HttpServerProtocolCodecFactory extends DemuxingProtocolCodecFactory {
	public HttpServerProtocolCodecFactory() {
		super.addMessageDecoder(HttpRequestDecoder.class);
		super.addMessageEncoder(HttpResponseMessage.class,
				HttpResponseEncoder.class);
	}

}
