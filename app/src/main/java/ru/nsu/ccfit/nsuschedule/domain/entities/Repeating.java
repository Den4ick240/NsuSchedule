package ru.nsu.ccfit.nsuschedule.domain.entities;

import com.google.gson.annotations.SerializedName;

public enum Repeating {

    @SerializedName("0")
    ONCE,

    @SerializedName("1")
    DAY,

    @SerializedName("2")
    MONTH,

    @SerializedName("3")
    WEEK,

    @SerializedName("4")
    TWO_WEEK,

    @SerializedName("5")
    YEAR
}
