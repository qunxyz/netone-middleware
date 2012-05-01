package oesee.teach.java.four;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestException {

	public void todo() throws Exception {

		todo1();

	}

	public void todo1() throws RuntimeException, IOException {

		todo3();
	}

	public void todo2() throws Exception {
		throw new Exception();
	}

	public void todo3() throws NullPointerException {
		throw new NullPointerException();
	}

	public void todo4() throws FileNotFoundException {
		throw new FileNotFoundException();
	}

}
