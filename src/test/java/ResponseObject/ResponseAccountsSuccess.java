package ResponseObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseAccountsSuccess {
    @JsonProperty("userID") //maparea variabilei
    private String userID;

    @JsonProperty("username")
    private String username;

    @JsonProperty("books")
    private List<BookObject> books;

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public List<BookObject> getBooks() {
        return books;
    }
}