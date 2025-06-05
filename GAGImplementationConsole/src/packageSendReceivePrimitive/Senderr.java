package packageSendReceivePrimitive;

import object.Msg;
import java.util.Properties;

import javax.jms.Connection;

import javax.jms.ConnectionFactory;

import javax.jms.Destination;

import javax.jms.JMSException;

import javax.jms.MessageProducer;

import javax.jms.ObjectMessage;

import javax.jms.Queue;

import javax.jms.QueueConnectionFactory;

import javax.jms.Session;

import javax.naming.Context;

import javax.naming.InitialContext;

import javax.naming.NamingException;
import static packageSendReceivePrimitive.SendReceive.openJMSHOME;
import utils.LaunchOpenJMS;

public class Senderr {

    private Context context = null;

    private ConnectionFactory factory = null;

    private Connection connection = null;

    private Destination destination = null;
    private Session session = null;
    private MessageProducer producer = null;

    public Senderr() {

    }
    public void sendObjectMessage(String host, String channel, Msg msg) {
      while(true){
          
        Properties initialProperties = new Properties();

        initialProperties.put(InitialContext.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");

        initialProperties.put(InitialContext.PROVIDER_URL,

                "tcp://"+host+":3035");

        try {
            context = new InitialContext(initialProperties);
            factory = (QueueConnectionFactory) context
                    .lookup("ConnectionFactory");
            destination = (Queue) context.lookup(channel);
            connection = factory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(destination);
            Msg eventMessage = msg;

            ObjectMessage objectMessage = session.createObjectMessage();

            objectMessage.setObject(eventMessage);
            connection.start();

            producer.send(objectMessage);
            /*--System.out.println(this.getClass().getName()
                    + " has sent a message : " + eventMessage);*/
            break;
        } catch (NamingException e) {

            //e.printStackTrace();
            System.err.println("Host not Found  Naming");
            LaunchOpenJMS.startupJMS(openJMSHOME);
        } catch (JMSException e) {
            //e.printStackTrace();
            System.err.println("Host not Found  JMSException");
            LaunchOpenJMS.startupJMS(openJMSHOME);
        }
       if (context != null) {

            try {

                context.close();

            } catch (NamingException ex) {
                //ex.printStackTrace();
                System.err.println("Host not Found  Naming");
            }

        }
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException ex) {
                //ex.printStackTrace();
                System.err.println("Host not Found  Close");
            }

        }
      }
        
    }

    public static void main(String[] args) {

        Senderr firstClient = new Senderr();

        firstClient.sendObjectMessage("192.168.35.101","mainQueue",null);

    }

}