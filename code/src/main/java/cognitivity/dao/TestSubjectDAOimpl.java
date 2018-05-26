package cognitivity.dao;

import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestSubject;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Guy on 20/1/18.
 *
 * Data Access Object for TestQuestion object
 * @Note! API documentation is in the Interfaces
 *
 */
@Repository
public class TestSubjectDAOimpl extends AbstractDAO<TestSubject> implements TestSubjectDAO{

    public TestSubject get(Long id) {
        return super.get(id, TestSubject.class);
    }

    public void delete(Long id) {
        super.delete(id, TestSubject.class);
    }


    @Transactional
    public List<TestAnswer> getSubjectAnswers(long subjectId) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestAnswer T where T.testSubject.id = :testSubjectId";
        Query<TestAnswer> query = session.createQuery(queryString, TestAnswer.class);
        query.setParameter("testSubjectId", subjectId);
        return query.getResultList();
    }

    @Transactional
    public List<TestSubject> getTestSubjectsWhoParticipatedInTest(long testId) {
        Session session = sessionFactory.getCurrentSession();
        String queryString =
                "select testSubject " +
                "from TestSubject testSubject, TestAnswer testAnswer " +
                "where testSubject.id = testAnswer.testSubject.id AND testAnswer.cognitiveTest.id = :cognitiveTestId " +
                "group by testSubject";

        Query<TestSubject> query = session.createQuery(queryString, TestSubject.class);
        query.setParameter("cognitiveTestId", testId);
        return query.getResultList();
    }

    public List<TestSubject> findAllTestSubjectsInTheSystem(){
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestSubject";
        Query<TestSubject> query = session.createQuery(queryString, TestSubject.class);
        return query.getResultList();
    }

    @Override
    public boolean doesSubjectWithEmailExist(String email) {
        return false;
    }
}
