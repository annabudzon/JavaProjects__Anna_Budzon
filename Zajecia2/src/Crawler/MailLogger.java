package Crawler;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import java.util.*;
import java.util.logging.Level;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailLogger implements Logger {
    public MailLogger() throws MessagingException {}
    @Override
    public void log( String status, Student student){
     String to = "aniabudzon@gmail.com";
      // String from = "java-example@gmail.com";
            final String username = "bogusjava@gmail.com";
            final String password= "passwordjava";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.quitwait","false");
            props.put("mail.encoding", "utf8");
            props.put("mail.debug","false");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
            props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
            
        Session session;
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
                }
        });
    session.setDebug(true);
    try {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject("CLAWLER NOTIFICATION "+status+" person");
        message.setText(status+student);
        Transport transport = session.getTransport("smtp");
        transport.send(message);
        transport.close();
        // Transport.send(message);

        System.out.println("Done");
    }   catch (javax.mail.MessagingException ex) {
            java.util.logging.Logger.getLogger(MailLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
   @Override
   public void log(String status, int iteracja){}
   @Override
   public void log(String status){}
}
