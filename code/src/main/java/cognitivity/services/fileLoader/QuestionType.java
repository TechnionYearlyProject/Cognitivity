package cognitivity.services.fileLoader;


import com.google.gson.annotations.SerializedName;

/**
 * Created by ophir on 12/05/18.
 */
public enum QuestionType {
    @SerializedName("0")
    MultipleChoice,

    @SerializedName("1")
    RateQuestion,

    @SerializedName("2")
    OpenQuestion,

    @SerializedName("3")
    DrillDownQuestion

}
