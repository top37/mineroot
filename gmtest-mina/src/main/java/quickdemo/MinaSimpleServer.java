package quickdemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaSimpleServer {
private static final int PORT = 9123;
    
    private static final String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        // 接收者
        IoAcceptor acceptor = new NioSocketAcceptor();

        // 设置编码器
        acceptor.getFilterChain().addLast(
                "codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset
                        .forName("UTF-8"))));

        //设置Handler
        acceptor.setHandler(new TimeServerHandler());

        // 绑定端口,启动服务，并开始处理远程客户端请求
        acceptor.bind(new InetSocketAddress(HOST,PORT));
        System.out.println("服务端启动成功");
    }

}
