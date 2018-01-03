package cognitivity.dao;

import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestSubject;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data Access Object for TestQuestion object
 */
@Repository
public class TestSubjectDAO extends AbstractDAO<TestSubject> {
    public TestSubject get(Long id){
        return super.get(id, TestSubject.class);
    }

    public void delete(Long id){
        super.delete(id, TestSubject.class);
    }

    /**
     * Get all the answers for the given test subject
     *
     * @param subject - the test subject.
     * @return - A list of all the answers the test subject has given.
     */
    @Transactional
    public List<TestAnswer> getSubjectAnswers(TestSubject subject){
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestAnswer T where T.testSubject = :testSubject";
        Query<TestAnswer> query = session.createQuery(queryString, TestAnswer.class);
        query.setParameter("testSubject", subject);
        return query.getResultList();
    }

    /**
     * Get all the test subject who participated in a given test.
     *
     * @param testId - the test Id of the given test
     * @return - A list of the subjects who participated the test.
     */
    @Transactional
    public List<TestSubject> getTestSubjectsWhoParticipatedInTest(long testId){
        return null;
    }
}
