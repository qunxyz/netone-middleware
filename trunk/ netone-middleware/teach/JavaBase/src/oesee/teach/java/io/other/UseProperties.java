package oesee.teach.java.io.other;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class UseProperties {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ResourceBundle messages = ResourceBundle.getBundle("localuri",
				Locale.CHINESE);
		String name = messages.getString("name");
		String age = messages.getString("age");
		System.out.println("name:" + name + ",age=" + age);

	}

}
