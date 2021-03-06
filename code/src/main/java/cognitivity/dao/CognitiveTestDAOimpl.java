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
 * Created by Guy on 20/1/18.
 *
 * Data Access Object
 *
 * @Note! API documentation is in the Interfaces
 */
@Repository
@Transactional
public class CognitiveTestDAOimpl extends AbstractDAO<CognitiveTest> implements CognitiveTestDAO {

    public long add(CognitiveTest cognitiveTest, Long testManagerId){
        Session session = sessionFactory.getCurrentSession();

        TestManager proxyManager = session.load(TestManager.class, testManagerId);

        cognitiveTest.setManager(proxyManager);
        session.save(cognitiveTest);
        return cognitiveTest.getId();
    }

    public CognitiveTest get(Long id) { return super.get(id, CognitiveTest.class); }

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
        List<CognitiveTest> res = query.getResultList();
        return res;
    }


    /**
     * Helper function for implementing the filter by string functions
     *
     * @param columnName - The name of the column to filter By
     * @param filter     - The required substring
     * @return all the CognitiveTest which uphold filter is substring of
     * CognitiveTest.columnName
     */
    private List<CognitiveTest> filterByString(String columnName, String filter) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from CognitiveTest T where T." + columnName + " LIKE :filter";
        Query<CognitiveTest> query = session.createQuery(queryString, CognitiveTest.class);
        query.setParameter("filter", "%" + filter + "%");
        List<CognitiveTest> res = query.getResultList();
        return res;
    }

    @Transactional(readOnly = true)
    public List<CognitiveTest> filterTestsByProject(String projectFilter) {
        return filterByString("project", projectFilter);
    }

    @Override
    public boolean testWithNameExists(String testName) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from CognitiveTest T where T.name LIKE :testName";
        Query<CognitiveTest> query = session.createQuery(queryString, CognitiveTest.class);
        query.setParameter("testName", "%" + testName + "%");
        List<CognitiveTest> res = query.getResultList();
        return !res.isEmpty();
    }

    @Transactional(readOnly = true)
    public List<CognitiveTest> filterTestsByNotes(String notesFilter) {
        return filterByString("notes", notesFilter);
    }
}
