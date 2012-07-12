package oe.rmi.service;
import java.rmi.server.*;
import java.util.Locale;
import java.util.ResourceBundle;
 import java.io.*;
import java.net.*;
 
public class SMRMISocket
     extends RMISocketFactory {
	
	ResourceBundle messages1 = ResourceBundle.getBundle("Service", Locale.CHINESE);
 
  public Socket createSocket(String host, int port) throws IOException {
     return new Socket(host, port);
   }
 
  public ServerSocket createServerSocket(int port) throws IOException {
		 String dataport=messages1.getString("dataport");
	  	 System.out.println("RMI服务器的注册与数据传输端口 ="+dataport);
	     return new ServerSocket(Integer.parseInt(dataport));
   }
 
}
