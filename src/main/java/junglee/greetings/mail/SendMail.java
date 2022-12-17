package junglee.greetings.mail;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMail {

@Autowired
private EmailConfig emailconf;

public boolean SendWithoutAttachement(String from, String to, String subject, String body) throws MessagingException {

Properties properties = new Properties();

properties.put("mail.smtp.host", emailconf.getHost());
        properties.put("mail.smtp.port", emailconf.getPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", emailconf.getUsername());
        properties.put("mail.password", emailconf.getPassword());
       
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(emailconf.getUsername(), emailconf.getPassword());
            }
            });
        try {
               MimeMessage message = new MimeMessage(session);
               message.setFrom(new InternetAddress(from));
               message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
               message.setSubject(subject);
               message.setContent(body, "text/html");
               Transport.send(message);
               return true;
            } catch (MessagingException mex) {
               mex.printStackTrace();
               return false;
            }
}


public boolean sendWithAttachement(String from, String to, List<String> cc, String subject, File fileContent) {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", emailconf.getHost());
        properties.put("mail.smtp.port", emailconf.getPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", emailconf.getUsername());
        properties.put("mail.password", emailconf.getPassword());
        
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(emailconf.getUsername(), emailconf.getPassword());
            }
            });
        try {
        	MimeMessage message = new MimeMessage(session);
        	message.setSubject(subject);
        	message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            /*
             * This HTML mail have to 2 part, the BODY and the embedded image
             */
            MimeMultipart multipart = new MimeMultipart("related");

            // first part  (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<img src=\"cid:image\">";
            messageBodyPart.setContent(htmlText, "text/html");

            // add it
            multipart.addBodyPart(messageBodyPart);
            
            // second part (the image)
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(fileContent);
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID","<image>");
            
            // add it
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            message.setContent(multipart);
            Transport.send(message);
            return true;
        }
        catch(MessagingException mex) {
            mex.printStackTrace();
            return false;
         }
}
}