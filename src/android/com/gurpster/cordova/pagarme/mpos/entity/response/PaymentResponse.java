
package com.gurpster.cordova.pagarme.mpos.entity.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "message",
    "data"
})
public class PaymentResponse implements Parcelable
{

    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private Data data;
    public final static Creator<PaymentResponse> CREATOR = new Creator<PaymentResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PaymentResponse createFromParcel(Parcel in) {
            return new PaymentResponse(in);
        }

        public PaymentResponse[] newArray(int size) {
            return (new PaymentResponse[size]);
        }

    }
    ;

    protected PaymentResponse(Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public PaymentResponse() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public PaymentResponse(String message, Data data) {
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
