package cognitivity.dao;

import cognitivity.entities.TestAnswer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data Access Object for TestAnswer object
 */
@Repository
public class TestAnswerDAOimpl extends AbstractDAO<TestAnswer> implements TestAnswerDAO {

    public TestAnswer get(Long id) {
        return super.get(id, TestAnswer.class);
    }

    public void delete(Long id) {
        super.delete(id, TestAnswer.class);
    }

    @Transactional(readOnly = true)
    public List<TestAnswer> getTestSubjectAnswersInTest(long subjectId, long testId) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestAnswer T "
                + " where T.testSubject.id = :testSubjectId and T.cognitiveTest.id = :cognitiveTestId";
        Query<TestAnswer> query = session.createQuery(queryString, TestAnswer.class);
        query.setParameter("testSubjectId", subjectId);
        query.setParameter("cognitiveTestId", testId);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<TestAnswer> getTestAnswers(long questionId) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestAnswer T where T.question.id = :questionId";
        Query<TestAnswer> query = session.createQuery(queryString, TestAnswer.class);
        query.setParameter("questionId", questionId);
        return query.getResultList();
    }
}
