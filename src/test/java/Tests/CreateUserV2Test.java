package Tests;

import Actions.AccountActions;
import Hooks.Hooks;
import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
import Objects.ResponseObject.ResponseAccountFailed;
import Objects.ResponseObject.ResponseAccountsAuthSucces;
import Objects.ResponseObject.ResponseAccountsSuccess;
import Objects.ResponseObject.ResponseTokenSuccess;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateUserV2Test extends Hooks {

    public String userID;
    public String username;
    public String password;
    public String token;

    public AccountActions accountActions;

    @Test
    public void TestMethod() {

        System.out.println("Step 1: create user: ");
         createUser();

        System.out.println("Step 2: generate token: ");
         generateToken();
//
//        System.out.println("Step 3: Optain new user: ");
//        interractNewUser();

//        createUserFail();
    }

    public void createUser() {

        accountActions = new AccountActions();
        username = "Darina" + System.currentTimeMillis();
        password = "Darina12345!";
        RequestAccount requestAccount = new RequestAccount(username, password);
        ResponseAccountsSuccess responseAccountsSuccess=accountActions.createNewAccount(requestAccount);

        userID=responseAccountsSuccess.getUserID();
    }
    public void generateToken(){
        accountActions = new AccountActions();

        RequestAccountToken requestAccountToken=new RequestAccountToken(username,password);
        ResponseTokenSuccess responseTokenSuccess=accountActions.generateToken(requestAccountToken);
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
