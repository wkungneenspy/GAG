/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package packageSendReceivePrimitive;

import object.Msg;
import java.util.Properties;

import javax.jms.Connection;

import javax.jms.ConnectionFactory;

import javax.jms.Destination;

import javax.jms.JMSException;

import javax.jms.Message;

import javax.jms.MessageConsumer;

import javax.jms.ObjectMessage;

import javax.jms.Queue;

import javax.jms.QueueConnectionFactory;

import javax.jms.Session;

import javax.naming.Context;

import javax.naming.InitialContext;

import javax.naming.NamingException;

public class Receiverr {

    private Context context = null;

    private ConnectionFactory factory = null;

    private Connection connection = null;

    private Destination destination = null;

    private Session session = null;

    private MessageConsumer consumer = null;

    public Receiverr() {

    }	

    public Msg receiveObjectMessage(String host, String channel) {

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

            consumer = session.createConsumer(destination);


            connection.start();

            Message message = consumer.receive();
	
            if (message instanceof ObjectMessage) {

                Object object = ((ObjectMessage) message).getObject();
                Msg ev = (Msg) object;

                //--System.out.println(this.getClass().getName()

                     //--   + " has received a message : " + ev.getChannel());
                
                return ev;
            }

        } catch (NamingException e) {

    //        e.printStackTrace();

        } catch (JMSException e) {

  //          e.printStackTrace();
        }finally{

            if (context != null) {
                try {

                    context.close();

                } catch (NamingException ex) {

//                    ex.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ex) {
      //              ex.printStackTrace();
                }
            }
        }
        return new Msg();
    }
    public static void main(String[] args) {

        Receiverr secondClient = new Receiverr();

        secondClient.receiveObjectMessage("localhost","mainQueue");
    }
}