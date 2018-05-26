package cognitivity.services;

import cognitivity.dao.TestSubjectDAO;
import cognitivity.entities.TestSubject;
import cognitivity.exceptions.SendLinksException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * Business service for sending test links to subjects
 * <p>
 * Created by ophir on 26/5/18.
 */

@Service
public class DistributeTestLinkToSubjectService {

    private TestSubjectDAO testSubjectDAO;

    private final static Logger logger = Logger.getLogger(DistributeTestLinkToSubjectService.class);

    @Autowired
    public DistributeTestLinkToSubjectService(TestSubjectDAO testSubjectDAO) {
        this.testSubjectDAO = testSubjectDAO;
    }

    private void checkSubjectsAreRegistered(List<TestSubject> subjects) throws SendLinksException {
        if (subjects.stream()
                .anyMatch(s -> !testSubjectDAO.doesSubjectWithEmailExist(s.getEmail()))) {
            logger.error("Some subjects were not registered");
            throw new SendLinksException();
        }
    }

    private static void sendEmailToSubjects(List<TestSubject> subjects, String link) {
        final String username = "cognivitity.tests@gmail.com";
        final String password = "cognitivity1234";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            Address[] addresses = (Address[]) subjects.stream()
                    .map(TestSubject::getEmail)
                    .map(e -> {
                        try {
                            return InternetAddress.parse(e);
                        } catch (AddressException ignored) {
                        }
                        return null;
                    }).toArray();
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject("Cognitivity: Test invitation");
            message.setText(
                    "Dear friend:\n" +
                            "\n" +
                            "You are invited to answer a cognitive test, at the link:\n" +
                            link + "\n" +
                            "\n" +
                            "Thank you in advance,\n" +
                            "The Cognitivity Team."
            );

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Sends the email with the link to all subjects.
     *
     * @param link     - the link to send.
     * @param subjects - the list of subjects to send the link to
     */
    public void sendLinksToSubjects(List<TestSubject> subjects, String link) throws SendLinksException {
        checkSubjectsAreRegistered(subjects);
        sendEmailToSubjects(subjects, link);
    }
}
