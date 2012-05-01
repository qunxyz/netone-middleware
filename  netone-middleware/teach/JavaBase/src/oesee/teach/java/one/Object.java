package oesee.teach.java.one;

/**
 * Sample3:理解Java的原始类型和对象类型
 * @author chen.jia.xun(Robanco) www.oesee.com
 *
 */
public class Object {
	
	public static void main(String[] args) {
		int a=0;
		
		Integer a1=0;
		
		//a.
		
		//a1 取有很多方法
		a1.intValue(); // 两种类型还可以互换
		
		int a2=a1.intValue();
		// ? 如何将 普通型 转换成 对象整型
		Integer a3=a2;
	}

}
