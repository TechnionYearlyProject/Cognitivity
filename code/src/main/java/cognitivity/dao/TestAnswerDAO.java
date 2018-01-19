package cognitivity.dao;

import cognitivity.entities.TestAnswer;

import java.util.List;

public interface TestAnswerDAO {

    public TestAnswer get(Long id);
    public void delete(Long id);
    public long add(TestAnswer data);
    public long update(TestAnswer data);

    /**
     * Get all the test answers of a subject from a specific test.
     *
     * @param subjectId - The subject Id whose answers we are looking for.
     * @param testId    - The test Id from which we want the answers.
     * @return - All the relevant answers from the test.
     */
    public List<TestAnswer> getTestSubjectAnswersInTest(long subjectId, long testId);

    /**
     * Get all the answers for the given test question.
     *
     * @param questionId - The question Id whose answers we are looking for.
     * @return - A list of all test answers relating to the given question.
     */
    public List<TestAnswer> getTestAnswers(long questionId);

}
