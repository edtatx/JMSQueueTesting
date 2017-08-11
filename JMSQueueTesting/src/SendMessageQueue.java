import javax.jms.*;
//import javax.naming.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class SendMessageQueue {

	public static void main(String[] args) throws JMSException {
		ConnectionFactory factory = 
				  new ActiveMQConnectionFactory("tcp://localhost:61616");  // ActiveMQ-specific (more)

				Connection con = factory.createConnection();

				try {
				  Session session = 
				      con.createSession(false, Session.AUTO_ACKNOWLEDGE);  // non-transacted session (more)

				  Queue queue = session.createQueue("test.queue");         // only specifies queue name (more)

				  MessageProducer producer = session.createProducer(queue);
				  Message msg = session.createTextMessage("Hi Ed this is a message");  // text message (more)
				  producer.send(msg);
				}
				finally {
				  con.close();                                             // free all resources (more)
				}
	}

}
