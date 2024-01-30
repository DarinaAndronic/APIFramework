package Tests;

import RequestObject.RequestAccount;
import RequestObject.RequestAccountToken;
import ResponseObject.ResponseAccountFailed;
import ResponseObject.ResponseAccountsAuthSucces;
import ResponseObject.ResponseAccountsSuccess;
import ResponseObject.ResponseTokenSuccess;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateUserTest {

    public String userID;
    public String username;
    public String password;
    public String token;
    @Test
    public void TestMethod() {

//        System.out.println("Step 1: create user: ");
//         createUser();

//        System.out.println("Step 2: generate token: ");
//         generateToken();
//
//        System.out.println("Step 3: Optain new user: ");
//        interractNewUser();

        createUserFail();
    }

    public void createUser(){
        //Definim caracteristicile clientului
        RequestSpecification requestSpecification= RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com");
        requestSpecification.contentType("application/json");

        //Configuram request-ul
         username="Darina"+System.currentTimeMillis();
         password="Darina12345!";

        //JSONObject requestbody=new JSONObject();
//        requestbody.put("userName", username);
//        requestbody.put("password","Darina12345.=");
        RequestAccount requestAccount=new RequestAccount(username, password);
        requestSpecification.body(requestAccount);

        //Accesam response-ul
        Response response=requestSpecification.post("/Account/v1/User");
        ResponseBody body=response.getBody();
        body.prettyPrint();

        //Validam statusul request-ului
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 201);//principala validare

        //validam Response Body
        ResponseAccountsSuccess responseAccountsSuccess=response.body().as(ResponseAccountsSuccess.class);
        Assert.assertNotNull(responseAccountsSuccess.getUserID());//verificare ca exista o valoare pt acest field
        Assert.assertEquals(responseAccountsSuccess.getUsername(), username);//verificam daca username are valoarea din request
        Assert.assertNotNull(responseAccountsSuccess.getBooks());

        userID=responseAccountsSuccess.getUserID();
    }

    //Request care ne genereaza un token (autentificare si autorizare)

    public void generateToken(){
        RequestSpecification requestSpecification= RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com");
        requestSpecification.contentType("application/json");

        RequestAccountToken requestAccountToken=new RequestAccountToken(username,password);
        requestSpecification.body(requestAccountToken);


        Response response=requestSpecification.post("/Account/v1/GenerateToken");
        ResponseBody body=response.getBody();
        body.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);
        ResponseTokenSuccess responseTokenSuccess=response.body().as(ResponseTokenSuccess.class);

        Assert.assertNotNull(responseTokenSuccess.getToken());
        Assert.assertNotNull(responseTokenSuccess.getExpires());
        Assert.assertEquals(responseTokenSuccess.getStatus(),"Success");
        Assert.assertEquals(responseTokenSuccess.getResult(),"User authorized successfully.");

        token=responseTokenSuccess.getToken();
    }

    //Facem un Get pt user-ul creat

    public void interractNewUser()
    {
        RequestSpecification requestSpecification= RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com");
        requestSpecification.contentType("application/json");


        requestSpecification.header("Authorization", "Bearer "+token);//autorizarea care foloseste token

        Response response=requestSpecification.get("Account/v1/User/"+userID); //compunere de endpoint
        Assert.assertEquals(response.getStatusCode(), 200);

        ResponseAccountsAuthSucces responseAccountsAuthSucces=response.body().as(ResponseAccountsAuthSucces.class);

        Assert.assertNotNull(responseAccountsAuthSucces.getUserId());
        Assert.assertEquals(responseAccountsAuthSucces.getUsername(), username);
        Assert.assertNotNull(responseAccountsAuthSucces.getBooks());
    }

    public void createUserFail(){
        RequestSpecification requestSpecification= RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com");
        requestSpecification.contentType("application/json");

        username="Darina123";
        password="Darina12345!";

        RequestAccount requestAccount=new RequestAccount(username, password);
        requestSpecification.body(requestAccount);
        Response response=requestSpecification.post("/Account/v1/User");
        ResponseBody body=response.getBody();
        body.prettyPrint();

//        System.out.println(response.getStatusCode());
      Assert.assertEquals(response.getStatusCode(), 406);
//
        ResponseAccountFailed responseAccountFailed=response.body().as(ResponseAccountFailed.class);
        Assert.assertEquals(responseAccountFailed.getCode(),1204);
        Assert.assertEquals(responseAccountFailed.getMessage(),"User exists!");
    }
}
