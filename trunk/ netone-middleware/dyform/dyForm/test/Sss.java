import org.apache.commons.lang.StringUtils;


public class Sss {
	
	public static void main(String[] args) {
		String webpath = "file:/" + System.getProperty("user.dir") + "/wf/";
		System.out.println(StringUtils.replace(webpath,"\\", "/"));
	}

}
