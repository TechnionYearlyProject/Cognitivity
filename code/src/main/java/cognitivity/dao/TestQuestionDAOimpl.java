package cognitivity.dao;

import cognitivity.entities.TestManager;
import cognitivity.entities.TestQuestion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data Access Object for TestQuestion object
 */
@Repository
public class TestQuestionDAOimpl extends AbstractDAO<TestQuestion> implements TestQuestionDAO{

    public TestQuestion get(Long id) {
        return super.get(id, TestQuestion.class);
    }

    public void delete(Long id) {
        super.delete(id, TestQuestion.class);
    }

    @Transactional
    public List<TestQuestion> getTestQuestionsFromAManager(TestManager manager) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestQuestion where testManager = :manager";
        Query<TestQuestion> query = session.createQuery(queryString, TestQuestion.class);
        query.setParameter("manager", manager);
        List<TestQuestion> res =  query.getResultList();
        return res;
    }
}
