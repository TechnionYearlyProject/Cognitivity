package cognitivity.services.mailing;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;

/**
 * Created by ophir on 15/06/18.
 * <p>
 * [<b>Cognitivity</b> : ]
 */
public class MailingClient {
    private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
    private static final String SMTP_AUTH_USER = "azure_0daff2c2a979962506b73af4b28ef4b3@azure.com";
    private static final String SMTP_AUTH_PWD = "dev@cognitivity18";

    private static final String COGNITIVITY_EMAIL = "cognitivity.tests@gmail.com";

    public static void SendLink(List<String> to, String link) throws MessagingException {
        SendMail(to, COGNITIVITY_EMAIL, link);
    }

    private static void SendMail(List<String> to, String from, String link) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", SMTP_HOST_NAME);
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", "true");

        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(properties, auth);
        mailSession.setDebug(true);

        MimeMessage message = createEmailMessage(mailSession, to, from, link);

        SendMessage(mailSession, message);
    }

    private static MimeMessage createEmailMessage(Session mailSession, List<String> to, String from, String link) throws MessagingException {
        MimeMessage message = new MimeMessage(mailSession);

        // Create content of message (link to test)
        Multipart multipart = new MimeMultipart("alternative");

        BodyPart part1 = new MimeBodyPart();
        part1.setText("You are invited to take part in an experiment:");

        BodyPart part2 = new MimeBodyPart();
        part2.setContent(
                "<p>Hello,</p>" +
                        "<p>You are hereby invited to fill in the following <i>cognitive-test</i>:" +
                        "<a href=" + link + ">Cognitive Test</a>" +
                        "<p>Thank you,<br>The Cognitivity Team</br></p>",
                "text/html");

        multipart.addBodyPart(part1);
        multipart.addBodyPart(part2);

        // Set from address - will be the cognitivity team tests emails
        message.setFrom(new InternetAddress(from));

        // Create array of addresses
        InternetAddress[] toAddress = new InternetAddress[to.size()];
        for (int i = 0; i < to.size(); i++) {
            toAddress[i] = new InternetAddress(to.get(i));
            toAddress[i].validate();
        }
        // Add recipients
        message.addRecipients(Message.RecipientType.TO,
                toAddress);

        // Set subject line
        message.setSubject("Cognitivity Invitation");

        // Set content
        message.setContent(multipart);

        return message;
    }

    private static void SendMessage(Session mailSession, MimeMessage message) throws MessagingException {
        Transport transport = mailSession.getTransport();
        // Connect the transport object.
        transport.connect();
        // Send the message.
        transport.sendMessage(message, message.getAllRecipients());
        // Close the connection.
        transport.close();
    }


    /**
     * Authenticator
     */
    private static class SMTPAuthenticator extends javax.mail.Authenticator {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
        }
    }
}
