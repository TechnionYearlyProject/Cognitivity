package cognitivity.services.fileLoader;


import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by ophir on 12/05/18.
 */
public enum QuestionType {
    @SerializedName("1")
    MultipleChoice(1),

    @SerializedName("2")
    RateQuestion(2),

    @SerializedName("3")
    OpenQuestion(3),

    @SerializedName("4")
    DrillDownQuestion(4);

    private int num;

    QuestionType(int num) {
        this.num = num;
    }


    public static QuestionType fromNumber(int number) {
        return Arrays.stream(QuestionType.values())
                .filter(t -> t.num == number)
                .findAny().orElse(QuestionType.MultipleChoice);
    }
}
