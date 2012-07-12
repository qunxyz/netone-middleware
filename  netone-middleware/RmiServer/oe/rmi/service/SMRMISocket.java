package oe.rmi.service;
import java.rmi.server.*;
 import java.io.*;
 import java.net.*;
 
public class SMRMISocket
     extends RMISocketFactory {
 
  public Socket createSocket(String host, int port) throws IOException {
     return new Socket(host, port);
   }
 
  public ServerSocket createServerSocket(int port) throws IOException {

	  	 System.out.println("RMI服务器的注册与数据传输端口 ="+2897);
	     return new ServerSocket(2897);
   }
 
}
