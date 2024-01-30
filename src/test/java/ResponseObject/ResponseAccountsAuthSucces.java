package ResponseObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseAccountsAuthSucces {
    @JsonProperty("userId") //maparea variabilei
    private String userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("books")
    private List<BookObject> books;

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public List<BookObject> getBooks() {
        return books;
    }
}