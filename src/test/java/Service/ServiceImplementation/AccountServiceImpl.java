package Service.ServiceImplementation;

import EndPoints.AccountEndPoints;
import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
import Service.APIService.AccountAPIService;
import Service.InterfaceService.AccountServiceInterface;
import io.restassured.response.Response;

public class AccountServiceImpl implements AccountServiceInterface {

    public AccountAPIService accountAPIService;

    @Override
    public Response createAccount(RequestAccount requestAccount) {
        accountAPIService=new AccountAPIService();
        return accountAPIService.post(requestAccount, AccountEndPoints.ACCOUNT_CREATE);
    }

    @Override
    public Response generateToken(RequestAccountToken requestAccountToken) {
        accountAPIService=new AccountAPIService();
        return accountAPIService.post(requestAccountToken, AccountEndPoints.ACCOUNT_TOKEN);
    }

    @Override
    public Response getSpecificAccount(String userID, String token) {
        accountAPIService=new AccountAPIService();
        String finalEndpoint=AccountEndPoints.ACCOUNT_USERSPECIFIC.replace("{userID}", userID);
        return accountAPIService.get(finalEndpoint,token);
    }
}
