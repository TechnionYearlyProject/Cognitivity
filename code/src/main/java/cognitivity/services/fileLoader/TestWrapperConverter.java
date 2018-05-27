package cognitivity.services.fileLoader;

import cognitivity.dto.BlockWrapper;
import cognitivity.dto.TestWrapper;
import cognitivity.entities.CognitiveTest;
import cognitivity.entities.TestQuestion;
import cognitivity.services.fileLoader.questions.*;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ophir on 18/05/18.
 */
public class TestWrapperConverter {

    private final Test test;
    private long managerId;

    public TestWrapperConverter(Test test, long managerId) {
        this.test = test;
        this.managerId = managerId;
    }

    private cognitivity.entities.TestManager convertTestManager(final TestManager manager) {
        cognitivity.entities.TestManager testManager = new cognitivity.entities.TestManager(manager.getEmail());
        testManager.setId(managerId);
        return testManager;
    }

    private static class SecondaryQuestionConverter {
        private final DrillDownQuestion question;

        private SecondaryQuestionConverter(DrillDownQuestion question) {
            this.question = question;
        }

        public List<String> getSecondaryQuestionsText() {
            List<String> secondaryQuestionsText = new ArrayList<>();
            for (int i = 0, indexOfSecondary = 0; i < question.getAnswersForMain().length; i++) {
                if (indexOfSecondary >= question.getSecondaryQuestions().length) {
                    secondaryQuestionsText.add(i, null);
                    continue;
                }
                DrillDownSecondaryQuestion drillDownSecondaryQuestion = question.getSecondaryQuestions()[indexOfSecondary];
                if (drillDownSecondaryQuestion.getIndexOfMainQuestion() == i) {
                    secondaryQuestionsText.add(i, drillDownSecondaryQuestion.getText());
                    if (indexOfSecondary < question.getSecondaryQuestions().length) indexOfSecondary++;
                } else {
                    secondaryQuestionsText.add(i, null);
                }
            }
            return secondaryQuestionsText;
        }

        public List<List<String>> getAnswersForSecondary() {
            List<List<String>> secondaryQuestionsAnswers = new ArrayList<>();
            for (int i = 0, indexOfSecondary = 0; i < question.getAnswersForMain().length; i++) {
                if (indexOfSecondary >= question.getSecondaryQuestions().length) {
                    secondaryQuestionsAnswers.add(i, null);
                    continue;
                }
                DrillDownSecondaryQuestion drillDownSecondaryQuestion = question.getSecondaryQuestions()[indexOfSecondary];
                if (drillDownSecondaryQuestion.getIndexOfMainQuestion() == i) {
                    secondaryQuestionsAnswers.add(i, Arrays.asList(drillDownSecondaryQuestion.getAnswers()));
                    if (indexOfSecondary < question.getSecondaryQuestions().length) indexOfSecondary++;
                } else {
                    secondaryQuestionsAnswers.add(i, null);
                }
            }
            return secondaryQuestionsAnswers;
        }

        public List<Integer> getCorrectAnswerSecondary() {
            List<Integer> secondaryQuestionsCorrectAnswer = new ArrayList<>();
            for (int i = 0, indexOfSecondary = 0; i < question.getAnswersForMain().length; i++) {
                if (indexOfSecondary >= question.getSecondaryQuestions().length) {
                    secondaryQuestionsCorrectAnswer.add(i, null);
                    continue;
                }
                DrillDownSecondaryQuestion drillDownSecondaryQuestion = question.getSecondaryQuestions()[indexOfSecondary];
                if (drillDownSecondaryQuestion.getIndexOfMainQuestion() == i) {
                    secondaryQuestionsCorrectAnswer.add(drillDownSecondaryQuestion.getCorrectAnswer());
                    if (indexOfSecondary < question.getSecondaryQuestions().length) indexOfSecondary++;
                } else {
                    secondaryQuestionsCorrectAnswer.add(i, -1);
                }
            }
            return secondaryQuestionsCorrectAnswer;
        }
    }

    private String getQuestionText(Question question) {
        Map<String, Object> data = new HashMap<>();
        data.put("questionText", question.getText());
        data.put("type", question.getQuestionType());
        data.put("questionPosition", question.getPosition());
        data.put("showConfidenceBar", question.hasConfidenceBar());
        data.put("isBeingMeasured", question.hasTimeMeasurement());
        data.put("tags", question.getTags());
        switch (question.getQuestionType()) {
            case MultipleChoice:
                MultipleAnswersQuestion multipleQuestion = (MultipleAnswersQuestion) question;
                data.put("answers", multipleQuestion.getAnswers());
                data.put("correctAnswer", multipleQuestion.getCorrectAnswer());
                data.put("typeMultipleQuestion", multipleQuestion.getStructure());
                break;
            case RateQuestion:
                data.put("heightOfRate", ((RateQuestion) question).getMaxRating());
                break;
            case OpenQuestion:
                data.put("answerText", ((OpenQuestion) question).getAnswerText());
                break;
            case DrillDownQuestion:
                DrillDownQuestion drillDown = (DrillDownQuestion) question;
                SecondaryQuestionConverter secondaryQuestionConverter = new SecondaryQuestionConverter(drillDown);
                data.put("answersForMain", drillDown.getAnswersForMain());
                data.put("correctMainQuestion", drillDown.getCorrectForMain());
                data.put("secondaryQuestionsText", secondaryQuestionConverter.getSecondaryQuestionsText());
                data.put("answersForSecondary", secondaryQuestionConverter.getAnswersForSecondary());
                data.put("correctAnswerSecondary", secondaryQuestionConverter.getCorrectAnswerSecondary());

        }
        return new Gson().toJson(data);
    }

    private TestQuestion convertQuestion(final Question question, final cognitivity.entities.TestManager manager) {
        // TODO : need to add json field for each question type!
        String fullQuestionText = getQuestionText(question);
        return new TestQuestion(
                fullQuestionText,
                question.getPictureLink(),
                null,   /*  To Be Set After Block Is Created */
                null,   /*  To Be Set After Test Is Created */
                manager
        );
    }

    private List<TestQuestion> convertQuestions(final Question[] questions, final cognitivity.entities.TestManager manager) {
        return Arrays.stream(questions)
                .map(q -> convertQuestion(q, manager))
                .collect(Collectors.toList());
    }

    private BlockWrapper convertBlock(final TestBlock block, final List<TestQuestion> questions, CognitiveTest cognitiveTest) {
        BlockWrapper blockWrapper = new BlockWrapper(
                questions,
                questions.size(),
                block.isShuffle(),
                block.getTag(),
                cognitiveTest
        );
        List<TestQuestion> ql = blockWrapper.getQuestions();
        ql.forEach(q ->
                q.setTestBlock(blockWrapper.innerBlock(1L))
        ); // Id doesnt matter since it is set in CognitivityTestService.saveTestWrapperWithDaos
        return blockWrapper;
    }

    private List<BlockWrapper> convertBlocks(final TestBlock[] blocks, cognitivity.entities.TestManager manager, CognitiveTest cognitiveTest) {
        return Arrays.stream(blocks)
                .map(b -> convertBlock(b, convertQuestions(b.getQuestions(), manager), cognitiveTest))
                .collect(Collectors.toList());
    }

    private static int numberOfQuestions(TestBlock[] blocks) {
        return Arrays.stream(blocks)
                .map(b -> b.getQuestions().length)
                .reduce((x, y) -> x + y)
                .orElse(0);
    }

    private static void setTestForQuestions(TestWrapper testForQuestions) {
        testForQuestions.getBlocks()
                .forEach(
                        b -> b.getQuestions().
                                forEach(
                                        q -> q.setCognitiveTest(testForQuestions.innerTest())
                                )
                );
    }

    public TestWrapper convert() {
        final cognitivity.entities.TestManager manager = convertTestManager(test.getTestManager());
        CognitiveTest cognitiveTest = new CognitiveTest(
                test.getName(),
                manager,
                numberOfQuestions(test.getBlocks()),
                test.getNotes(),
                test.getProject()
        );
        List<BlockWrapper> blocks = convertBlocks(test.getBlocks(), manager, cognitiveTest);
        // BlockWrapper blockWrapper = new BlockWrapper()
        TestWrapper testWrapper = new TestWrapper(
                cognitiveTest,
                blocks
        );
        setTestForQuestions(testWrapper);
        return testWrapper;
    }
}
