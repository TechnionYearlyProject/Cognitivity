package cognitivity.dao;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestQuestion;
import cognitivity.entities.TestSubject;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object for TestAnswer object
 */
@Repository
public class TestAnswerDAO extends AbstractDAO<TestAnswer> {

    public TestAnswer get(Long id){
        return super.get(id, TestAnswer.class);
    }

    public void delete(Long id){
        super.delete(id, TestAnswer.class);
    }

    /**
     * Get all the test answers a subject has given.
     *
     * @param subject - The subject whose answers we are looking for
     *
     * @return - A list of all the answers by the subject
     */
    public List<TestAnswer> getAllTestSubjectAnswers(TestSubject subject){
        // TODO: the error that intellij shows (on the query language) should be
        // TODO: fixed when the spring configuration file will be correct
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestAnswer T where T.testSubject = :testSubject";
        Query<TestAnswer> query = session.createQuery(queryString, TestAnswer.class);
        query.setParameter("testSubject", subject);

        return query.getResultList();
    }

    /**
     * Get all the test answers of a subject from a specific test.
     *
     * @param subject - The subject whose answers we are looking for.
     * @param test - The test from which we want the answers.
     *
     * @return - All the relevant answers from the test.
     */
    public List<TestAnswer> getAllTestSubjectAnswersInTest(TestSubject subject, CognitiveTest test){
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestAnswer T "
        + " where T.testSubject = :testSubject and T.cognitiveTest = :cognitiveTest";
        Query<TestAnswer> query = session.createQuery(queryString, TestAnswer.class);
        query.setParameter("testSubject", subject);
        query.setParameter("cognitiveTest", test);
        return query.getResultList();
    }

    /**
     * Get all the answers for the given test question.
     *
     * @param question - The question whose answers we are looking for.
     * @return - A list of all test answers relating to the given question.
     */
    public List<TestAnswer> getAllTestAnswerForAQuestion(TestQuestion question){
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestAnswer T where T.question = :question";
        Query<TestAnswer> query = session.createQuery(queryString, TestAnswer.class);
        query.setParameter("question", question);
        return query.getResultList();
    }
}
