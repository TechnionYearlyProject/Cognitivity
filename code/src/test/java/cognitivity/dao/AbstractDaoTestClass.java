package cognitivity.dao;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDaoTestClass {
    /*
     * all the DAOs are member variables because that is the only way to do them Autowired
     * we need them to be because if they won't, we won't get the dependency injections inside them
     */
    @Autowired
    protected TestAnswerDAO testAnswerDAO;
    @Autowired
    protected TestManagerDAO testManagerDAO;
    @Autowired
    protected CognitiveTestDAO cognitiveTestDAO;
    @Autowired
    protected TestBlockDAO testBlockDAO;
    @Autowired
    protected TestQuestionDAO testQuestionDAO;
    @Autowired
    protected TestSubjectDAO testSubjectDAO;
}
