package cognitivity;

import org.springframework.http.MediaType;

import java.nio.charset.Charset;

/**
 * Created by ophir on 07/01/18.
 */
public class TestUtil {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );


    public static final String jsonData = "{\n" +
            "  \"testManager\": {\n" +
            "    \"email\": \"ophirk8396@gmail.com\"\n" +
            "  },\n" +
            "  \"name\": \"test1\",\n" +
            "  \"notes\": \"This is a first test for checking the load feature\",\n" +
            "  \"project\": \"project1\",\n" +
            "  \"blocks\": [\n" +
            "    {\n" +
            "      \"numberOfQuestions\": 4,\n" +
            "      \"tag\": \"blocker\",\n" +
            "      \"shuffle\": true,\n" +
            "      \"questions\": [\n" +
            "        {\n" +
            "          \"text\": \"How smart are you? (from 1 to 5)\",\n" +
            "          \"position\": 8,\n" +
            "          \"questionType\": 1,\n" +
            "          \"hasConfidenceBar\": true,\n" +
            "          \"pictureLink\": \"picture/link/path\",\n" +
            "          \"hasTimeMeasurement\": false,\n" +
            "          \"tags\": [\n" +
            "            \"rating\"\n" +
            "          ],\n" +
            "          \"maxRating\": 5\n" +
            "        },\n" +
            "        {\n" +
            "          \"text\": \"What do you study?\",\n" +
            "          \"position\": 1,\n" +
            "          \"questionType\": 2,\n" +
            "          \"hasConfidenceBar\": false,\n" +
            "          \"pictureLink\": \"picture/link/path2\",\n" +
            "          \"hasTimeMeasurement\": true,\n" +
            "          \"tags\": [\n" +
            "            \"open\",\n" +
            "            \"thinker\"\n" +
            "          ],\n" +
            "          \"answerText\": \"Computer Science\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"text\": \"How smart are you? (from 1 to 5)\",\n" +
            "          \"position\": 0,\n" +
            "          \"questionType\": 0,\n" +
            "          \"hasConfidenceBar\": true,\n" +
            "          \"pictureLink\": \"picture/link/path\",\n" +
            "          \"hasTimeMeasurement\": false,\n" +
            "          \"tags\": [\n" +
            "            \"rating\"\n" +
            "          ],\n" +
            "          \"answers\": [\n" +
            "            \"Yes\",\n" +
            "            \"No\",\n" +
            "            \"Maybe\"\n" +
            "          ],\n" +
            "          \"correctAnswer\": 1,\n" +
            "          \"structure\": 1\n" +
            "        },\n" +
            "        {\n" +
            "          \"text\": \"How smart are you? (from 1 to 5)\",\n" +
            "          \"position\": 0,\n" +
            "          \"questionType\": 3,\n" +
            "          \"hasConfidenceBar\": true,\n" +
            "          \"pictureLink\": \"picture/link/path\",\n" +
            "          \"hasTimeMeasurement\": false,\n" +
            "          \"tags\": [\n" +
            "            \"rating\"\n" +
            "          ],\n" +
            "          \"answersForMain\": [\n" +
            "            \"Yes\",\n" +
            "            \"No\",\n" +
            "            \"Maybe\"\n" +
            "          ],\n" +
            "          \"correctAnswerForMain\": 1,\n" +
            "          \"secondaryQuestions\": [\n" +
            "            {\n" +
            "              \"text\": \"Really?\",\n" +
            "              \"answers\": [\n" +
            "                \"yes\",\n" +
            "                \"No\"\n" +
            "              ],\n" +
            "              \"correctAnswer\": 1,\n" +
            "              \"indexOfMainQuestion\": 2\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";
}
