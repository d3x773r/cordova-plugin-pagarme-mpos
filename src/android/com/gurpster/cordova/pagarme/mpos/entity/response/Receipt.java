
package com.gurpster.cordova.pagarme.mpos.entity.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "message",
    "name",
    "currency",
    "value"
})
public class Receipt implements Parcelable
{

    @JsonProperty("type")
    @JSONField(name = "type")
    private String type;
    @JsonProperty("message")
    @JSONField(name = "message")
    private String message;
    @JsonProperty("name")
    @JSONField(name = "name")
    private String name;
    @JsonProperty("currency")
    @JSONField(name = "currency")
    private String currency;
    @JsonProperty("value")
    @JSONField(name = "value")
    private String value;
    public final static Creator<Receipt> CREATOR = new Creator<Receipt>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Receipt createFromParcel(Parcel in) {
            return new Receipt(in);
        }

        public Receipt[] newArray(int size) {
            return (new Receipt[size]);
        }

    }
    ;

    protected Receipt(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.currency = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Receipt() {
    }

    /**
     * 
     * @param name
     * @param currency
     * @param type
     * @param message
     * @param value
     */
    public Receipt(String type, String message, String name, String currency, String value) {
        super();
        this.type = type;
        this.message = message;
        this.name = name;
        this.currency = currency;
        this.value = value;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(message);
        dest.writeValue(name);
        dest.writeValue(currency);
        dest.writeValue(value);
    }

    public int describeContents() {
        return  0;
    }

}
