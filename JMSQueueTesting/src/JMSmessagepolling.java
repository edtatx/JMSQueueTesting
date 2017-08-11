import javax.jms.*;
//import javax.naming.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSmessagepolling {
	
	public static void main(String[] args) throws JMSException {
		ConnectionFactory factory = 
		  new ActiveMQConnectionFactory("tcp://localhost:61616");  // ActiveMQ-specific (more)
	
		Connection con = factory.createConnection();
	
		try {
		  Session session = 
		      con.createSession(false, Session.AUTO_ACKNOWLEDGE);  // non-transacted session (more)
	
		  Queue queue = session.createQueue("test.queue");         // only specifies queue name (more)
	
		  MessageConsumer consumer = session.createConsumer(queue);
	
		  con.start();                                             // start the connection (more)
		  while (true) {                                           // run forever
		    Message msg = consumer.receive();                      // blocking! (more)
		    if (! (msg instanceof TextMessage))
		      throw new RuntimeException("Expected a TextMessage");
		    TextMessage tm = (TextMessage) msg;
		    System.out.println(tm.getText());                      // print message content
		  }
		}
		finally {
		  con.close();                                             // free all resources (more)
		}
	}
}
