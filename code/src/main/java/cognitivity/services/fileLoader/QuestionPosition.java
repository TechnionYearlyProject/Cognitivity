package cognitivity.services.fileLoader;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ophir on 12/05/18.
 */
public enum QuestionPosition {
    @SerializedName("0")
    UpperRight,

    @SerializedName("1")
    UpperMiddle,

    @SerializedName("2")
    UpperLeft,

    @SerializedName("3")
    MiddleRight,

    @SerializedName("4")
    MiddleMiddle,

    @SerializedName("5")
    MiddleLeft,

    @SerializedName("6")
    BottomRight,

    @SerializedName("7")
    BottomMiddle,

    @SerializedName("8")
    BottomLeft
}
