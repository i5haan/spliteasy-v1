package services;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService 
{
    public static void prepareEmail(String to, int userid, int grpid, String uName, String gName) throws Exception
    {
    	String msg = uName + " just invited you to the group " + gName + " on Spliteasy! Sign up now to split expenses: http://localhost:8080/spliteasy/signup.jsp?g=" + grpid;
    	String subject = "Invitation from Spliteasy!";
    	sendEMail(to, subject, msg);
    }
	
	
    public static void sendEMail(String to, String subject, String msg) throws Exception
    {	
    	final String from = "noreplyrsdryclean@gmail.com";
    	final  String password ="Spliteasy12345";	

        Properties props = new Properties();  
        props.setProperty("mail.transport.protocol", "smtp");     
        props.setProperty("mail.host", "smtp.gmail.com");  
        props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.port", "465");  
        props.put("mail.debug", "true");  
        props.put("mail.smtp.socketFactory.port", "465");  
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
        props.put("mail.smtp.socketFactory.fallback", "false");  
        Session session = Session.getDefaultInstance(props,  
        new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {  
           return new PasswordAuthentication(from,password);  
       }  
       }); 
        
        //session.setDebug(true);  
        Transport transport = session.getTransport();  
        InternetAddress addressFrom = new InternetAddress(from);  

        MimeMessage message = new MimeMessage(session);  
        message.setSender(addressFrom);  
        message.setSubject(subject);  
        message.setContent(msg, "text/plain");  
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));  

        transport.connect();  
        Transport.send(message);
    }
}
