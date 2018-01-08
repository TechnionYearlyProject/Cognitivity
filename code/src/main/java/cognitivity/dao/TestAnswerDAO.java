package cognitivity.dao;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestAnswer;
import cognitivity.entities.TestQuestion;
import cognitivity.entities.TestSubject;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data Access Object for TestAnswer object
 */
@Repository
public class TestAnswerDAO extends AbstractDAO<TestAnswer> {

    public TestAnswer get(Long id) {
        return super.get(id, TestAnswer.class);
    }

    public void delete(Long id) {
        super.delete(id, TestAnswer.class);
    }

    /**
     * Get all the test answers of a subject from a specific test.
     *
     * @param subjectId - The subject Id whose answers we are looking for.
     * @param testId    - The test Id from which we want the answers.
     * @return - All the relevant answers from the test.
     */
    @Transactional
    public List<TestAnswer> getTestSubjectAnswersInTest(long subjectId, long testId) {
        Session session = sessionFactory.getCurrentSession();
        TestSubjectDAO subjectDAO = new TestSubjectDAO();
        CognitiveTestDAO testDao = new CognitiveTestDAO();
        String queryString = "from TestAnswer T "
                + " where T.testSubject = :testSubject and T.cognitiveTest = :cognitiveTest";
        Query<TestAnswer> query = session.createQuery(queryString, TestAnswer.class);
        query.setParameter("testSubject", subjectDAO.get(subjectId));
        query.setParameter("cognitiveTest", testDao.get(testId));
        return query.getResultList();
    }

    /**
     * Get all the answers for the given test question.
     *
     * @param questionId - The question Id whose answers we are looking for.
     * @return - A list of all test answers relating to the given question.
     */
    @Transactional
    public List<TestAnswer> getTestAnswers(long questionId) {
        TestQuestionDAO dao = new TestQuestionDAO();
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestAnswer T where T.question = :question";
        Query<TestAnswer> query = session.createQuery(queryString, TestAnswer.class);
        query.setParameter("question", dao.get(questionId));
        return query.getResultList();
    }
}
