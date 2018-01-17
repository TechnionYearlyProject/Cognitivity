package cognitivity.dao;

import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
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
@Transactional
public class CognitiveTestDAOimpl extends AbstractDAO<CognitiveTest> implements CognitiveTestDAO {

    public CognitiveTest get(Long id) {
        return super.get(id, CognitiveTest.class);
    }

    public void delete(Long id) {
        super.delete(id, CognitiveTest.class);
    }

    public List<TestQuestion> getTestQuestions(long testId) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestQuestion  T where T.cognitiveTest.id = :testId";
        Query<TestQuestion> query = session.createQuery(queryString, TestQuestion.class);
        query.setParameter("testId", testId);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<TestBlock> getTestBlocks(long testId) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestBlock T where T.cognitiveTest.id = :testId";
        Query<TestBlock> query = session.createQuery(queryString, TestBlock.class);
        query.setParameter("testId", testId);
        return query.getResultList();
    }


    @Transactional(readOnly = true)
    public List<CognitiveTest> getCognitiveTestOfManager(long managerId) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from CognitiveTest T where T.testManager.id = :managerId";
        Query<CognitiveTest> query = session.createQuery(queryString, CognitiveTest.class);
        query.setParameter("managerId", managerId);

        return query.getResultList();
    }
}
