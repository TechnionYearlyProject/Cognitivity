package cognitivity.ParseUtil;

import cognitivity.services.fileLoader.QuestionType;
import cognitivity.services.fileLoader.questions.*;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class QuestionDeserializer extends RequiredFieldAnnotatedDeserializer<Question> {

    public QuestionDeserializer(String jsonData, GsonBuilder builder) {
        super(jsonData, builder, false);
    }

    @Override
    public Question deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        Question q = super.deserialize(je, type, jdc);

        QuestionType questionType = q.getQuestionType();

        switch (questionType) {
            case MultipleChoice:
                return new GsonBuilder()
                        .registerTypeAdapter(MultipleAnswersQuestion.class, new RequiredFieldAnnotatedDeserializer<MultipleAnswersQuestion>(jsonData, builder, false))
                        .create()
                        .fromJson(je, MultipleAnswersQuestion.class);
            case RateQuestion:
                return new GsonBuilder()
                        .registerTypeAdapter(RateQuestion.class, new RequiredFieldAnnotatedDeserializer<RateQuestion>(jsonData, builder, false))
                        .create()
                        .fromJson(je, RateQuestion.class);
            case OpenQuestion:
                return new GsonBuilder()
                        .registerTypeAdapter(OpenQuestion.class, new RequiredFieldAnnotatedDeserializer<OpenQuestion>(jsonData, builder, false))
                        .create()
                        .fromJson(je, OpenQuestion.class);
            case DrillDownQuestion:
                return new GsonBuilder()
                        .registerTypeAdapter(DrillDownQuestion.class, new RequiredFieldAnnotatedDeserializer<DrillDownQuestion>(jsonData, builder, false))
                        .create()
                        .fromJson(je, DrillDownQuestion.class);
        }
        return null;

    }
}
