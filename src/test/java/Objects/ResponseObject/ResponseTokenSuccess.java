package Objects.ResponseObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseTokenSuccess {
    @JsonProperty("token") //maparea variabilei
    private String token;

    @JsonProperty("expires")
    private String expires;

    @JsonProperty("status")
    private String status;
    @JsonProperty("result")
    private String result;

    public String getToken() {
        return token;
    }

    public String getExpires() {
        return expires;
    }

    public String getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }
}