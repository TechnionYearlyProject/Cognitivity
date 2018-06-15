package cognitivity.services;

import cognitivity.dao.TestSubjectDAO;
import cognitivity.exceptions.SendLinksException;
import cognitivity.services.mailing.MailingClient;
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
            throw new SendLinksException(SendLinksException.ErrorType.NOT_REGISTERED);
        }
    }


    /**
     * Sends the email with the link to all subjects.
     *
     * @param subjects - the list of subjects to send the link to
     * @param link     - the link to send.
     */
    public void sendLinksToSubjects(List<String> subjects, String link) throws SendLinksException {
        // checkSubjectsAreRegistered(subjects);
        try {
            MailingClient.SendLink(subjects, link);
        } catch (MessagingException e) {
            logger.error("Was not able to send email correctly");
            throw new SendLinksException(SendLinksException.ErrorType.MESSAGE_FAILED_TO_SEND);
        }
    }
}
