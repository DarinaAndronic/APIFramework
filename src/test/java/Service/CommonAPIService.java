package Service;

import Rest.RestRequest;
import Rest.RestRequestType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonAPIService {

    //aceasta clasa contine metode pt tipuri de request-uri cu diferiti parametri
    public Response post(Object body,String url) {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.body(body);

        APIServiceHelper.requestLogs(requestSpecification,url,RestRequestType.REQUEST_POST);

        Response response=performRequest(RestRequestType.REQUEST_POST,requestSpecification,url);
        return response;
    }

    public Response post(Object body,String url,String token) {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", "Bearer "+token);
        requestSpecification.body(body);

        //de pus Logg-uri pt request si response

        Response response=performRequest(RestRequestType.REQUEST_POST,requestSpecification,url);
        return response;
    }

    public Response get(String url,String token) {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization", "Bearer "+token);

        //de pus Logg-uri pt request si response

        Response response=performRequest(RestRequestType.REQUEST_GET,requestSpecification,url);
        return response;
    }



    //facem o instanta de RestRequest care sa apeleze metoda de perform
    private Response performRequest(String requestType,
                                    RequestSpecification requestSpecification, String URL) {
        return new RestRequest().performRequest(requestType, requestSpecification,URL);
    }
}
