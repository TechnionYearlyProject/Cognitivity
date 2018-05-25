package cognitivity.services.fileLoader;

import cognitivity.dao.CognitiveTestDAO;
import cognitivity.dao.TestBlockDAO;
import cognitivity.dao.TestQuestionDAO;
import cognitivity.dto.TestWrapper;
import cognitivity.exceptions.DBException;
import cognitivity.services.CognitiveTestService;

/**
 * Created by ophir on 18/05/18.
 */
public class TestDBSaver implements ITestDBSaver {
    private final Test test;
    private final CognitiveTestDAO cognitiveTestDAO;
    private final TestBlockDAO testBlockDAO;
    private final TestQuestionDAO testQuestionDAO;
    private long managerId;
    private TestWrapper testWrapper;

    public TestDBSaver(Test test,
                       CognitiveTestDAO cognitiveTestDAO,
                       TestBlockDAO testBlockDAO,
                       TestQuestionDAO testQuestionDAO, long managerId) {
        this.test = test;
        this.cognitiveTestDAO = cognitiveTestDAO;
        this.testBlockDAO = testBlockDAO;
        this.testQuestionDAO = testQuestionDAO;
        this.managerId = managerId;
    }

    @Override
    public TestDBSaver convert() {
        this.testWrapper = new TestWrapperConverter(test, managerId).convert();
        return this;
    }

    @Override
    public void writeToMySql() throws DBException {
        CognitiveTestService.saveTestWrapperWithDaos(
                testWrapper,
                cognitiveTestDAO,
                testBlockDAO,
                testQuestionDAO
        );
    }

    @Override
    public TestWrapper getWrapper() {
        return testWrapper;
    }
}
