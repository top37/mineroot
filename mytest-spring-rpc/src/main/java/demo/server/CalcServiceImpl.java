package demo.server;

public class CalcServiceImpl implements CalcService{
	public int add(int a, int b) {
        System.out.println(a + " + " + b + " = " + ( a + b ) );
        return a + b;
    }

}
