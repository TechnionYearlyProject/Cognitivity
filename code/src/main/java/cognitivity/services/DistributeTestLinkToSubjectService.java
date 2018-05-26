package cognitivity.services;

import cognitivity.dao.TestSubjectDAO;
import cognitivity.exceptions.SendLinksException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
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

    private void checkSubjectsAreRegistered(List<String> subjects) throws SendLinksException {
        if (subjects.stream()
                .anyMatch(e -> !testSubjectDAO.doesSubjectWithEmailExist(e))) {
            logger.error("Some subjects were not registered");
            throw new SendLinksException();
        }
    }

    private static void sendEmailToSubjects(List<String> subjects, String link) {
        final String username = "cognivitity.tests";
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
            InternetAddress address = new InternetAddress(username);
            message.setFrom(address);
            Address[] addresses = new Address[subjects.size()];
            for (int i = 0; i < subjects.size(); i++) {
                addresses[i] = InternetAddress.parse(subjects.get(i))[0];
            }
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
     * @param subjects - the list of subjects to send the link to
     * @param link     - the link to send.
     */
    public void sendLinksToSubjects(List<String> subjects, String link) throws SendLinksException {
        checkSubjectsAreRegistered(subjects);
        sendEmailToSubjects(subjects, link);
    }
}
