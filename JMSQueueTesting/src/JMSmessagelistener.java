import javax.jms.*;
//import javax.naming.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSmessagelistener {
	
	public static void main(String[] args) throws JMSException, InterruptedException {
		ConnectionFactory factory = 
				  new ActiveMQConnectionFactory("tcp://localhost:61616");  // ActiveMQ-specific (more)

				Connection con = factory.createConnection();

				try {
				  Session session = 
				      con.createSession(false, Session.AUTO_ACKNOWLEDGE);  // non-transacted session (more)

				  Queue queue = session.createQueue("test.queue");         // only specifies queue name (more)

				  MessageConsumer consumer = session.createConsumer(queue);

				  consumer.setMessageListener(new MessageListener() {
				    public void onMessage(Message msg) {
				      try {
				        if (! (msg instanceof TextMessage))
				          throw new RuntimeException("no text message");
				        TextMessage tm = (TextMessage) msg;
				        System.out.println(tm.getText());                  // print message
				      }
				      catch (JMSException e) {
				        System.err.println("Error reading message");
				      }
				    }
				  });
				  
				  con.start();                                             // start the connection (more)
				  Thread.sleep(30 * 1000); // receive messages for 30s
				} finally {
				  con.close();  // free all resources (more)
				  System.out.println("Done!");
				}
			}
	}	
