package cognitivity.dto;


import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A wrapper class For cognitive test.
 * This class includes the wrapper blocks of the blocks in the test.
 */
public class TestWrapper {

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public void setBlocks(List<BlockWrapper> blocks) {
        this.blocks = blocks;
    }


    public Long getId() {
        return id;
    }

    private Long id;
    private String name;
    private TestManager testManager;
    private Integer numberOfQuestions;
    private String notes;
    private String project;

    private List<BlockWrapper> blocks;


    public TestWrapper() {
    }


    public TestWrapper(CognitiveTest test, List<BlockWrapper> blocks) {
        this.id = test.getId();
        this.testManager = test.getManager();
        this.name = test.getName();
        this.numberOfQuestions = test.getNumberOfQuestions();
        this.notes = test.getNotes();
        this.project = test.getProject();

        this.blocks = blocks;
    }

    public TestWrapper(CognitiveTest test) {
        this.id = test.getId();
        this.testManager = test.getManager();
        this.name = test.getName();
        this.numberOfQuestions = test.getNumberOfQuestions();
        this.notes = test.getNotes();
        this.project = test.getProject();

        this.blocks = new ArrayList<>();
    }

    public CognitiveTest innerTest() {
        CognitiveTest cognitiveTest = new CognitiveTest(name, testManager, numberOfQuestions, notes, project);
        cognitiveTest.setId(id);

        return cognitiveTest;
    }


    public List<BlockWrapper> getBlocks() {
        return blocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestWrapper that = (TestWrapper) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    public String getNotes() {
        return notes;
    }

    public String getProject() {
        return project;
    }

    public void setNotes(String notes) {

        this.notes = notes;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
