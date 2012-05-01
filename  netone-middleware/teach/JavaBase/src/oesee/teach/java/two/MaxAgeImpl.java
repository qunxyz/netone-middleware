package oesee.teach.java.two;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MaxAgeImpl implements MaxAge {

	public void findMaxAge() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		List list = new ArrayList();
		while (!line.equals(";")) {
			String[] info = line.split(",");
			Human human = new Human();
			human.setName(info[0]);
			human.setAge(Integer.parseInt(info[1]));

			list.add(human);

			line = br.readLine();

		}

		int maxAge = 0;
		String maxAgeName = "";
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Human element = (Human) iter.next();
			if (element.getAge() > maxAge) {
				maxAge = element.getAge();
				maxAgeName = element.getName();
			}
		}

		System.out.print("max age:" + maxAge + " is " + maxAgeName);

	}

}
