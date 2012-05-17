package oe.teach.oescript.demo1;

import java.text.SimpleDateFormat;

import oescript.parent.OeScript;

public class SoaScript extends OeScript{
	
	/**
	 * say hello
	 */
	public Object todo1(){
		return "hello "+"$(sr_name)";

	}

}
