package oe.midware.workflow.track.mode;

public class WfControl {

	public static String controlInfo() {

		String control = "<form action=\"\" name=\"form1\" method=\"post\">";
		String button1 = "<input name=\"but1\" type=\"button\" value=\"�� �� �� �� \" class=butt onclick=\"todosome(1)\">&nbsp;";
		String button2 = "<input name=\"but2\" type=\"button\" value=\"��ʼ������  \" class=butt onclick=\"todosome(2)\">&nbsp;";
		String button3 = "<input name=\"but3\" type=\"button\" value=\"�� �� �� �� \" class=butt onclick=\"todosome(3)\">&nbsp;";
		String button4 = "<input name=\"but4\" type=\"button\" value=\"����������  \" class=butt onclick=\"todosome(4)\">&nbsp;";
		String button5 = "<input name=\"but5\" type=\"button\" value=\"��ʷ������Ϣ\" class=butt onclick=\"todosome(5)\"></form>";

		String aheadInfo = "<br>"
				+ control
				+ button1
				+ button2
				+ button3
				+ button4
				+ button5
				+ "<em><font size=\"2px\">���[��������]��ť�������̣������̱���������صĽڵ�ᱻ���������־������ʱ����ͨ������ڵ��ʾ�ύ������</font></em>";
		return aheadInfo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
