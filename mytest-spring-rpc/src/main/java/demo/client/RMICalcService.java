package demo.client;

/**
 * @author top37
 * 创建一个客户端接口，之类名称随意命名不需要跟客户端保持一致，但是 方法名必须一致，否则spring代理无法识别.
 *
 */
public interface RMICalcService {
	public int add(int a, int b);

}
