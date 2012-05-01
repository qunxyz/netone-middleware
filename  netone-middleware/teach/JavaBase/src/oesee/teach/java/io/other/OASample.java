package oesee.teach.java.io.other;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;

public class OASample {

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 1000; i++) {
			String docRs = dealwithDoc("mike", "lucy" + i, "boyfriend");
			createnewDoc(docRs, "c:/abc/doccnew" + i + ".doc");
		}

	}

	public static String dealwithDoc(String name, String who, String friendinfo)
			throws Exception {
		InputStream input = new FileInputStream("c:/Template.htm");
		byte[] byteinfo = new byte[input.available()];
		input.read(byteinfo);
		String docinfo = new String(byteinfo);

		docinfo = docinfo.replace("${who}", who);
		docinfo = docinfo.replace("${name}", name);
		docinfo = docinfo.replace("${friendinfo}", friendinfo);
		docinfo = docinfo.replace("${date}", (new Timestamp(System
				.currentTimeMillis())).toString());
		input.close();
		return docinfo;
	}

	public static void createnewDoc(String docinfo, String doc)
			throws IOException {
		OutputStream out = new FileOutputStream(doc);
		out.write(docinfo.getBytes());
		out.close();
	}

}
