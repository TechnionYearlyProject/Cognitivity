package cognitivity.services.fileLoader;

import cognitivity.services.fileLoader.questions.*;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;

/**
 * Created by ophir on 25/05/18.
 */
public interface ITestReader {

    JsonDeserializer<Question> questionJsonDeserializer = (JsonDeserializer<Question>)
            (jsonElement, type, jsonDeserializationContext) -> {
                // Switch by the question questionType - deserialize according to the actual question questionType.
                final int index = jsonElement.getAsJsonObject().get("questionType").getAsInt();
                if (!(index <= QuestionType.values().length && index >= 0)) {
                    return null;
                }
                QuestionType questionType = QuestionType.values()[index];

                Class<? extends Question> questionClass = null;
                switch (questionType) {
                    case MultipleChoice:
                        questionClass = MultipleAnswersQuestion.class;
                        break;
                    case RateQuestion:
                        questionClass = RateQuestion.class;
                        break;
                    case OpenQuestion:
                        questionClass = OpenQuestion.class;
                        break;
                    case DrillDownQuestion:
                        questionClass = DrillDownQuestion.class;
                }
                return new Gson().fromJson(jsonElement, questionClass);
            };

    Test read();
}
