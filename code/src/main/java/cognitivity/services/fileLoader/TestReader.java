package cognitivity.services.fileLoader;

import cognitivity.services.fileLoader.questions.Question;
import com.google.gson.GsonBuilder;

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
        return new GsonBuilder()
                .registerTypeAdapter(Question.class, questionJsonDeserializer)
                .create()
                .fromJson(jsonData, Test.class);
    }

}
