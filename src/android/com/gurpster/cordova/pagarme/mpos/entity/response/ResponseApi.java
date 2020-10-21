
package com.gurpster.cordova.pagarme.mpos.entity.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "message",
    "data"
})
public class ResponseApi implements Parcelable
{

    @JsonProperty("message")
    @JSONField(name = "message")
    private String message;
    @JsonProperty("data")
    @JSONField(name = "data")
    private Data data;
    public final static Creator<ResponseApi> CREATOR = new Creator<ResponseApi>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseApi createFromParcel(Parcel in) {
            return new ResponseApi(in);
        }

        public ResponseApi[] newArray(int size) {
            return (new ResponseApi[size]);
        }

    }
    ;

    protected ResponseApi(Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseApi() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public ResponseApi(String message, Data data) {
        super();
        this.message = message;
        this.data = data;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("data")
    public Data getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Data data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(message);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
