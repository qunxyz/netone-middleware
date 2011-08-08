package oe.midware.workflow.track.mode;

public class WfControl {

	public static String controlInfo() {

		String control = "<form action=\"\" name=\"form1\" method=\"post\">";
		String button1 = "<input name=\"but1\" type=\"button\" value=\"启 动 流 程 \" class=butt onclick=\"todosome(1)\">&nbsp;";
		String button2 = "<input name=\"but2\" type=\"button\" value=\"初始化流程  \" class=butt onclick=\"todosome(2)\">&nbsp;";
		String button3 = "<input name=\"but3\" type=\"button\" value=\"流 程 表 单 \" class=butt onclick=\"todosome(3)\">&nbsp;";
		String button4 = "<input name=\"but4\" type=\"button\" value=\"调试子流程  \" class=butt onclick=\"todosome(4)\">&nbsp;";
		String button5 = "<input name=\"but5\" type=\"button\" value=\"历史调试信息\" class=butt onclick=\"todosome(5)\"></form>";

		String aheadInfo = "<br>"
				+ control
				+ button1
				+ button2
				+ button3
				+ button4
				+ button5
				+ "<em><font size=\"2px\">点击[启动流程]按钮启动流程，当流程被启动后，相关的节点会被激活（带红点标志），这时可以通过点击节点表示提交动作！</font></em>";
		return aheadInfo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
