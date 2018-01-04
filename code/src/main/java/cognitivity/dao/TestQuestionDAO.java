package cognitivity.dao;

import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data Access Object for TestQuestion object
 */
@Repository
public class TestQuestionDAO extends AbstractDAO<TestQuestion> {

    public TestQuestion get(Long id) {
        return super.get(id, TestQuestion.class);
    }

    public void delete(Long id) {
        super.delete(id, TestQuestion.class);
    }

    /**
     * Get all test questions from a given manager.
     *
     * @param manager - The manager from which we want to get the questions
     * @return - A list of all test questions in the test
     */
    @Transactional
    public List<TestQuestion> getTestQuestionsFromAManager(TestManager manager) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestQuestion where testManager = :manager";
        Query<TestQuestion> query = session.createQuery(queryString, TestQuestion.class);
        query.setParameter("manager", manager);
        return query.getResultList();
    }
}
