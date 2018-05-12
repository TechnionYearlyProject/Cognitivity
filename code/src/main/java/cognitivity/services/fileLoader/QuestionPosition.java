package cognitivity.services.fileLoader;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ophir on 12/05/18.
 */
public enum QuestionPosition {
    @SerializedName("1")
    UpperRight,

    @SerializedName("2")
    UpperMiddle,

    @SerializedName("3")
    UpperLeft,

    @SerializedName("4")
    MiddleRight,

    @SerializedName("5")
    MiddleMiddle,

    @SerializedName("6")
    MiddleLeft,

    @SerializedName("7")
    BottomRight,

    @SerializedName("8")
    BottomMiddle,

    @SerializedName("9")
    BottomLeft
}
