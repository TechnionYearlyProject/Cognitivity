package cognitivity.dto;


import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A wrapper class For cognitive test.
 * This class includes the wrapper blocks of the blocks in the test.
 */
public class TestWrapper {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestManager getTestManager() {
        return testManager;
    }

    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
    }

    public Integer getNumberOfSubjects() {
        return numberOfSubjects;
    }

    public void setNumberOfSubjects(Integer numberOfSubjects) {
        this.numberOfSubjects = numberOfSubjects;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getLastAnswered() {
        return lastAnswered;
    }

    public void setLastAnswered(Date lastAnswered) {
        this.lastAnswered = lastAnswered;
    }

    public Integer getNumberOfFiledCopies() {
        return numberOfFiledCopies;
    }

    public void setNumberOfFiledCopies(Integer numberOfFiledCopies) {
        this.numberOfFiledCopies = numberOfFiledCopies;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public void setBlocks(List<BlockWrapper> blocks) {
        this.blocks = blocks;
    }

    public long getId() {
        return id;
    }

    private long id;
    private String name;
    private TestManager testManager;
    private Integer numberOfSubjects;
    private Integer state;
    private Date lastModified;
    private Date lastAnswered;
    private Integer numberOfFiledCopies;
    private Integer numberOfQuestions;

    private List<BlockWrapper> blocks;


    public TestWrapper() {
    }

    public TestWrapper(String name, TestManager manager, Integer state, Integer numberOfQuestions) {
        this.id = 2L;
        this.name = name;
        this.testManager = manager;
        this.numberOfSubjects = 0;
        this.state = state;
        this.lastModified = new Date(Calendar.getInstance().getTimeInMillis()); //TODO:Check for format
        this.lastAnswered = null;
        this.numberOfFiledCopies = 0;
        this.numberOfQuestions = numberOfQuestions;
    }

    // todo : initialize id
    public TestWrapper(CognitiveTest test, List<BlockWrapper> blocks) {
        this.id = test.getId();
        this.id = 2L;
        this.lastAnswered = test.getLastAnswered();
        this.lastModified = test.getLastModified();
        this.testManager = test.getManager();
        this.name = test.getName();
        this.numberOfFiledCopies = test.getNumberOfFiledCopies();
        this.numberOfQuestions = test.getNumberOfQuestions();
        this.numberOfSubjects = test.getNumberOfSubjects();
        this.state = test.getState();

        this.blocks = blocks;
    }

    //TODO: This might cause a problem, the test wrapper ID MUSt be similar to the test ID/
    //TODO: However, For a newly created test, there is no Id to fetch.
    public TestWrapper(CognitiveTest test) {
        // this.id = test.getId();
        this.id = 1L;
        this.lastAnswered = test.getLastAnswered();
        this.lastModified = test.getLastModified();
        this.testManager = test.getManager();
        this.name = test.getName();
        this.numberOfFiledCopies = test.getNumberOfFiledCopies();
        this.numberOfQuestions = test.getNumberOfQuestions();
        this.numberOfSubjects = test.getNumberOfSubjects();
        this.state = test.getState();

        this.blocks = new ArrayList<>();
    }

    public CognitiveTest innerTest() {
        CognitiveTest cognitiveTest = new CognitiveTest(name, testManager, state, numberOfQuestions);
        cognitiveTest.setId(id);
        cognitiveTest.setLastAnswered(lastAnswered);
        cognitiveTest.setLastModified(lastModified);
        cognitiveTest.setNumberOfFiledCopies(numberOfFiledCopies);
        cognitiveTest.setNumberOfSubjects(numberOfSubjects);

        return cognitiveTest;
    }


    public List<BlockWrapper> getBlocks() {
        return blocks;
    }
}
