package cognitivity.services;

import cognitivity.dao.TestSubjectDAO;
import cognitivity.exceptions.SendLinksException;
import cognitivity.services.mailing.MailingClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

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


    /**
     * Sends the email with the link to all subjects.
     *
     * @param subjects - the list of subjects to send the link to
     * @param link     - the link to send.
     */
    public void sendLinksToSubjects(List<String> subjects, String link) throws SendLinksException {
        if (subjects.isEmpty()) {
            logger.error("No emails were supplied");
            throw new SendLinksException(SendLinksException.ErrorType.EMPTY_EMAILS);
        }
        try {
            MailingClient.SendLink(subjects, link);
        } catch (MessagingException e) {
            logger.error("Was not able to send email correctly");
            throw new SendLinksException(SendLinksException.ErrorType.MESSAGE_FAILED_TO_SEND);
        }
    }
}
