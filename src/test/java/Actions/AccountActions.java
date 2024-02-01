package Actions;

import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
import Objects.ResponseObject.ResponseAccountsSuccess;
import Objects.ResponseObject.ResponseTokenSuccess;
import Rest.RestRequestStatus;
import Service.InterfaceService.AccountServiceInterface;
import Service.ServiceImplementation.AccountServiceImpl;
import io.restassured.response.Response;
import org.testng.Assert;

public class AccountActions {

    public AccountServiceImpl accountService;

    public ResponseAccountsSuccess createNewAccount(RequestAccount requestAccount){
        accountService=new AccountServiceImpl();

        Response response=accountService.createAccount(requestAccount);
        Assert.assertEquals((int) RestRequestStatus.SC_CREATED,response.getStatusCode());

        ResponseAccountsSuccess responseAccountsSuccess=response.body().as(ResponseAccountsSuccess.class);
        Assert.assertNotNull(responseAccountsSuccess.getUserID());
        Assert.assertEquals(responseAccountsSuccess.getUsername(), requestAccount.getUserName());
        Assert.assertNotNull(responseAccountsSuccess.getBooks());

        return responseAccountsSuccess;
    }

    public ResponseTokenSuccess generateToken(RequestAccountToken requestAccountToken){
        accountService=new AccountServiceImpl();
        Response response= accountService.generateToken(requestAccountToken);
        Assert.assertEquals(RestRequestStatus.SC_OK, response.getStatusCode());

        ResponseTokenSuccess responseTokenSuccess=response.body().as(ResponseTokenSuccess.class);

        Assert.assertNotNull(responseTokenSuccess.getToken());
        Assert.assertNotNull(responseTokenSuccess.getExpires());
        Assert.assertEquals(responseTokenSuccess.getStatus(),"Success");
        Assert.assertEquals(responseTokenSuccess.getResult(),"User authorized successfully.");

        return responseTokenSuccess;
    }
}
