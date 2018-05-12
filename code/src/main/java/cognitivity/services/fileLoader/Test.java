package cognitivity.services.fileLoader;

/**
 * Created by ophir on 12/05/18.
 */
public class Test {
    private TestManager testManager;
    private String name;
    private String notes;
    private String project;
    private TestBlock[] blocks;

    public TestManager getTestManager() {
        return testManager;
    }

    public void setTestManager(TestManager testManager) {
        this.testManager = testManager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public TestBlock[] getBlocks() {
        return blocks;
    }

    public void setBlocks(TestBlock[] blocks) {
        this.blocks = blocks;
    }

    public Test(TestManager testManager, String name, String notes, String project, TestBlock[] blocks) {

        this.testManager = testManager;
        this.name = name;
        this.notes = notes;
        this.project = project;
        this.blocks = blocks;
    }
}
