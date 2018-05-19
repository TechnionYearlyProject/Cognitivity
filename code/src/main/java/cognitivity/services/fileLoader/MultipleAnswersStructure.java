package cognitivity.services.fileLoader;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ophir on 12/05/18.
 */
public enum MultipleAnswersStructure {
    @SerializedName("0")
    Vertical,

    @SerializedName("1")
    Horizontal,

    @SerializedName("2")
    Matrix
}
