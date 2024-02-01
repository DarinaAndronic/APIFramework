package Rest;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestRequest {

    //trebuie sa fac o metoda care sa execute un request la un endpoint
    public Response performRequest(String requestType,
                                   RequestSpecification requestSpecification,String URL){
        switch (requestType){
            case RestRequestType.REQUEST_DELETE:
                return prepare(requestSpecification).delete(URL);
            case RestRequestType.REQUEST_GET:
                return prepare(requestSpecification).get(URL);
            case RestRequestType.REQUEST_POST:
                return prepare(requestSpecification).post(URL);
            case RestRequestType.REQUEST_PUT:
                return prepare(requestSpecification).put(URL);
        }
        return null;
    }

    //trebuie sa configurez setarile pt client
    public RequestSpecification prepare(RequestSpecification requestSpecification){
        requestSpecification.baseUri("https://demoqa.com");
        requestSpecification.contentType("application/json");

        return requestSpecification;
    }
}
