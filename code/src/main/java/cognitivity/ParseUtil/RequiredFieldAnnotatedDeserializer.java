package cognitivity.ParseUtil;

import cognitivity.exceptions.JsonTestParseError;
import cognitivity.services.fileLoader.questions.Question;
import com.google.gson.*;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by ophir on 26/05/18.
 */
public class RequiredFieldAnnotatedDeserializer<T> implements JsonDeserializer<T> {

    private final static Logger logger = Logger.getLogger(RequiredFieldAnnotatedDeserializer.class);
    protected final String jsonData;
    protected final GsonBuilder builder;
    private final boolean recursive;

    public RequiredFieldAnnotatedDeserializer(String jsonData, GsonBuilder builder, boolean recursive) {
        this.jsonData = jsonData;
        this.builder = builder;
        this.recursive = recursive;
    }

    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        GsonBuilder builder = new GsonBuilder();
        if (recursive) {
            builder.registerTypeAdapter(Question.class, new QuestionDeserializer(jsonData, this.builder));
        }
        T pojo = builder
                .create()
                .fromJson(je, type);

        Field[] fields = pojo.getClass().getDeclaredFields();
        for (Field f : fields) {
            // if (f.getAnnotation(JsonRequired.class) != null) {
            try {
                f.setAccessible(true);
                if (f.get(pojo) == null) {
                    throw new JsonParseException(new JsonTestParseError(jsonData));
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                logger.error("Failed to read test file content.");
            }
            // }
        }

        return pojo;
    }
}
