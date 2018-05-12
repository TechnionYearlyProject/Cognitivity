package cognitivity.services.fileLoader;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ophir on 12/05/18.
 */
public enum MultipleAnswersStructure {
    @SerializedName("1")
    Vertical,

    @SerializedName("2")
    Horizontal,

    @SerializedName("3")
    Matrix
}
