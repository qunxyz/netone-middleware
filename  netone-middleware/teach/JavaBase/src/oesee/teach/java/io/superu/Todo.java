package oesee.teach.java.io.superu;

public class Todo {

	public static void main(String[] args) {
		try {
			Human human = (Human) Class.forName(
					"oesee.teach.java.io.xml.superu.Human").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
