package cognitivity.services.fileLoader;

import cognitivity.ParseUtil.QuestionDeserializer;
import cognitivity.ParseUtil.RequiredFieldAnnotatedDeserializer;
import cognitivity.services.fileLoader.questions.Question;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

/**
 * Created by ophir on 12/05/18.
 */
public class TestReader implements ITestReader {
    private String jsonData;

    public TestReader(String jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public Test read() {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Question.class, new QuestionDeserializer(jsonData, builder));
            builder.registerTypeAdapter(Test.class, new RequiredFieldAnnotatedDeserializer<Test>(jsonData, builder, true));
            builder.registerTypeAdapter(TestBlock.class, new RequiredFieldAnnotatedDeserializer<TestBlock>(jsonData, builder, true));
            builder.registerTypeAdapter(TestManager.class, new RequiredFieldAnnotatedDeserializer<TestManager>(jsonData, builder, true));
            return builder.create()
                    .fromJson(jsonData, Test.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
