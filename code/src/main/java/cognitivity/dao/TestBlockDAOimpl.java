package cognitivity.dao;

import cognitivity.dto.BlockWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestBlock;
import cognitivity.entities.TestQuestion;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TestBlockDAOimpl extends AbstractDAO<TestBlock> implements TestBlockDAO {

    public TestBlock get(Long id) {
        return super.get(id, TestBlock.class);
    }

    public void delete(Long id) {
        super.delete(id, TestBlock.class);
    }

//    @Override
//    public Long add(BlockWrapper data) {
//        Session session = sessionFactory.getCurrentSession();
//        TestBlock newData = data.innerBlock();
//        return (Long) session.save(newData);
//    }
//
//    @Override
//    public void update(BlockWrapper data) {
//        Session session = sessionFactory.getCurrentSession();
//        TestBlock newData = data.innerBlock();
//        session.save(newData);
//    }

    /**
     * Get all the questions for a given block.
     *
     * @param blockID - The Id of the given block.
     * @return - A list of all the questions in the block.
     */
    @Transactional(readOnly = true)
    public List<TestQuestion> getAllBlockQuestions(long blockID) {

        Session session = sessionFactory.getCurrentSession();
        String queryString = "from TestQuestion T "
                + " where T.testBlock.id = :testBlockId";
        Query<TestQuestion> query = session.createQuery(queryString, TestQuestion.class);
        query.setParameter("testBlockId", blockID);
        return query.getResultList();
    }

}
