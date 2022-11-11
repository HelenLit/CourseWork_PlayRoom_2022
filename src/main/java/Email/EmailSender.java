package Email;

import java.util.Properties;

/* Зовнішня бібліотека javax.mail */

import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.*;

public class EmailSender {
    public static void main(String[] args) throws Exception {
        send("The critical error occurred","TestMessage");
    }

    public static void send(String subject, String mess){
        try {
            Properties properties = new Properties();

            properties.put("mail.smtp.auth", "true");

            properties.put("mail.smtp.starttls.enable", "true");

            properties.put("mail.smtp.socketFactory.port", "465"); //SSL Port

            properties.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class

            //       properties.put("mail.smtp.host","smtp.ukr.net");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "465");

            properties.put("mail.smtp.ssl.Enable", "true");

            properties.put("mail.smtp.user", "olena.litovska.kn.2021@lpnu.ua");

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("olena.litovska.kn.2021@lpnu.ua", "***");
                }
            };

            Session session = Session.getDefaultInstance(properties, auth);
            MimeMessage message = new MimeMessage(session);

            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");

            message.setFrom(new InternetAddress("olena.litovska.kn.2021@lpnu.ua"));

            message.setReplyTo(InternetAddress.parse("olena.litovska.kn.2021@lpnu.ua", false));

            message.setSubject(subject, "UTF-8");

            message.setText(mess, "UTF-8");

            message.setRecipient(Message.RecipientType.TO, new InternetAddress("playroomadm@ukr.net"));
            Transport.send(message);
        }catch (MessagingException e){

        }
    }
}