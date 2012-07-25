package oe.rmi.message.jms;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.servlet.ServletException;

import oe.frame.orm.util.IdServer;
import oe.rmi.client.RmiEntry;
import oe.rmi.message.MessageHandle;
import oe.rmi.message.UmsBussformworklist;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageHandleJMS implements MessageHandle, MessageListener {

	public static Map jmsMap = new HashMap();
	private ManageJMS manageJMS;
	private Connection connection = null;
	private Session session;
	private MessageProducer publisher;
	private Topic topic;
	private Topic control;
	private String url;

	/** 初始化jms连接，创建topic监听器 */
	public void init() throws ServletException {
		try {
			// 从配置文件中获取JMS服务器地址及端口信息
			MessageHandleJMS messageHandleJMS = (MessageHandleJMS) RmiEntry
					.iv("msghandle");
			manageJMS = messageHandleJMS.getManageJMS();
			url = manageJMS.getUrl();
			// 连接JMS服务器
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
					url);
			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = session.createTopic("topictest.messages");
			control = session.createTopic("topictest.control");
			MessageConsumer consumer = session.createConsumer(topic);
			consumer.setMessageListener(this);
			// 启动连接
			connection.start();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteMessage(String[] messageid) throws RemoteException {
		// TODO Auto-generated method stub
		for (int i = 0; i < messageid.length; i++) {
			String workid = messageid[i];
			for (Iterator iterator = jmsMap.keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();
				List sublist = (List) jmsMap.get(key);
				List resultlist = new ArrayList();
				for (Iterator iterator2 = sublist.iterator(); iterator2
						.hasNext();) {
					UmsBussformworklist ums = (UmsBussformworklist) iterator2
							.next();
					if (workid.equals(ums.getWorkid())) {
						resultlist.add(ums);
					}
				}
				sublist.removeAll(resultlist);
			}
		}
	}

	public void deleteMessage(String messageid) throws RemoteException {
		// TODO Auto-generated method stub
		String workid = messageid;
		for (Iterator iterator = jmsMap.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			List sublist = (List) jmsMap.get(key);
			List resultlist = new ArrayList();
			for (Iterator iterator2 = sublist.iterator(); iterator2.hasNext();) {
				UmsBussformworklist ums = (UmsBussformworklist) iterator2
						.next();
				if (workid.equals(ums.getWorkid())) {
					resultlist.add(ums);
				}
			}
			sublist.removeAll(resultlist);
		}
	}

	public UmsBussformworklist load(String workid) throws RemoteException {
		// TODO Auto-generated method stub
		UmsBussformworklist worklist = new UmsBussformworklist();
		for (Iterator iterator = jmsMap.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			List sublist = (List) jmsMap.get(key);
			for (Iterator iterator2 = sublist.iterator(); iterator2.hasNext();) {
				UmsBussformworklist ums = (UmsBussformworklist) iterator2
						.next();
				if (workid.equals(ums.getWorkid())) {
					worklist = ums;
				}
			}
		}
		return worklist;
	}

	public List personMessage(String participant, int from, int to,
			String condition) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public List query(UmsBussformworklist worklist, int from, int to,
			String condition) throws RemoteException {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		if (worklist.getParticipant() == null) {
			for (Iterator iterator = jmsMap.keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();
				List sublist = (List) jmsMap.get(key);
				list.addAll(sublist);
			}
		} else {
			list = (List) jmsMap.get(worklist.getParticipant());
		}
		return list;
	}

	public Serializable send(UmsBussformworklist worklist)
			throws RemoteException {
		// TODO Auto-generated method stub
		// 发送时间
		worklist.setStatusinfo(this._MESSAGE_MODE_NEW);
		Date created = new Date(System.currentTimeMillis());
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String createdStr = f.format(created);
		// 发送人
		String sender = worklist.getSender();
		// 消息主题
		String subject = worklist.getExtattr1();
		// 消息内容
		String content = worklist.getExtattr2();
		String receiveName = worklist.getParticipant();
		url = manageJMS.getUrl();
		try {
			// 创建连接
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
					url);
			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = session.createTopic("topictest.messages");
			control = session.createTopic("topictest.control");
			publisher = session.createProducer(topic);
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			session.createConsumer(control).setMessageListener(this);
			connection.start();
			Message testMessage = session.createMessage();
			// 清除原有属性
			testMessage.clearProperties();
			// 构造唯一消息ID
			IdServer idServer = new IdServer();
			testMessage.setStringProperty("workid", idServer.xnumID());
			// 构造消息内容
			testMessage.setStringProperty("statusinfo", worklist
					.getStatusinfo());
			testMessage.setStringProperty("created", createdStr);
			testMessage.setStringProperty("sender", sender);
			testMessage.setStringProperty("subject", subject);
			testMessage.setStringProperty("content", content);
			testMessage.setStringProperty("receiveName", receiveName);
			// 发送消息
			publisher.send(testMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public Serializable[] send(List<UmsBussformworklist> worklist)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public int totalNum(String loginname, String condition)
			throws RemoteException {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		int num = 0;
		if (loginname == null) {
			for (Iterator iterator = jmsMap.keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();
				List sublist = (List) jmsMap.get(key);
				num = num + sublist.size();
			}
		} else {
			list = (List) jmsMap.get(loginname);
			if (list != null) {
				num = list.size();
			}
		}
		return num;
	}

	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		if (connection == null) {
			try {
				init();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		UmsBussformworklist worklist = fetchMessage(message);
		if (jmsMap.get(worklist.getParticipant()) != null) {
			List list = (List) jmsMap.get(worklist.getParticipant());
			list.add(worklist);
		} else {
			List list = new ArrayList();
			list.add(worklist);
			jmsMap.put(worklist.getParticipant(), list);
		}
		try {
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 获取消息,即将消息中的属性取出，存入我们自定义的UmsBussformworklist对象中
	private UmsBussformworklist fetchMessage(Message message) {
		try {
			String workid = message.getStringProperty("workid");
			String statusinfo = message.getStringProperty("statusinfo");
			String createdStr = message.getStringProperty("created");
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date created = f.parse(createdStr);
			String sender = message.getStringProperty("sender");
			String subject = message.getStringProperty("subject");
			String content = message.getStringProperty("content");
			String receiveName = message.getStringProperty("receiveName");
			if (subject != null && subject != "" && receiveName != null
					&& receiveName != "") {
				UmsBussformworklist worklist = new UmsBussformworklist();
				worklist.setWorkid(workid);
				worklist.setStatusinfo(statusinfo);
				worklist.setCreated(created);
				worklist.setSender(sender);
				worklist.setExtattr1(subject);
				worklist.setExtattr2(content);
				worklist.setParticipant(receiveName);
				return worklist;
			} else {
				return null;
			}
		} catch (JMSException e) {
			e.printStackTrace(System.out);
			return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public ManageJMS getManageJMS() {
		return manageJMS;
	}

	public void setManageJMS(ManageJMS manageJMS) {
		this.manageJMS = manageJMS;
	}

	public void setEmail(String email) throws RemoteException {
	}

	public void setPassword(String password) throws RemoteException {
	}
}
