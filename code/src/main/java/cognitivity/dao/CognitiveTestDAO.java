package cognitivity.dao;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Data Access Object
 */
@Repository
public class CognitiveTestDAO extends AbstractDAO<CognitiveTest> {

    public CognitiveTest get(Long id){
        return super.get(id, CognitiveTest.class);
    }

    public void delete(Long id){
        super.delete(id, CognitiveTest.class);
    }

    /**
     * Return all questions in a given test
     *
     * @param test - The cognitive test to which we want to get all questions.
     *
     * @return - A list containing all the test questions in the test.
     */
    @Transactional
    public List<TestQuestion> getTestQuestions(CognitiveTest test){
        Session session = sessionFactory.getCurrentSession();
        // HQL query string, the test parameter will be the id of the given test
        // this meant for protecting against sql injection
        // TODO: the error that intellij shows (on the query language) should be
        // TODO: fixed when the spring configuration file will be correct
        String queryString = "from TestQuestion  T where T.cognitiveTest = :test";
        Query<TestQuestion> query = session.createQuery(queryString, TestQuestion.class);
        query.setParameter("test", test);
        return query.getResultList();
    }

    /**
     * Return all blocks in a given test.
     *
     * @param test - The cognitive test to which we want to get all blocks.
     *
     * @return - A list containing all the test blocks in the test.
     */
    @Transactional
    public List<TestBlock> getTestBlocks(CognitiveTest test){
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestBlock T where T.cognitiveTest = :test";
        Query<TestBlock> query = session.createQuery(queryString, TestBlock.class);
        query.setParameter("test", test);
        return query.getResultList();
    }

    /**
     * Get all tests a given test manager has created.
     *
     * @param manager - The manager whose tests we are looking for.
     *
     * @return - A list of all the managers tests.
     */
    @Transactional
    public List<CognitiveTest> getCognitiveTestOfManager(TestManager manager){
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from CognitiveTest where testManager = :manager";
        Query<CognitiveTest> query = session.createQuery(queryString, CognitiveTest.class);
        query.setParameter("manager", manager);

        return query.getResultList();
    }
}
