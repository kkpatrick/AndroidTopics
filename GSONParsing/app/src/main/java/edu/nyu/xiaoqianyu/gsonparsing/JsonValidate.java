package edu.nyu.xiaoqianyu.gsonparsing;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abc on 6/26/15.
 */
public class JsonValidate {
    public String object_or_array;
    public boolean empty;
    public long parse_time_nanoseconds;
    @SerializedName("validate")
    public boolean isValidate;
    public int size;

}
