package com.everis.atmdeposits.clientapi;


import com.everis.atmdeposits.dto.AccountResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AccountsClientApi {

    @GET("/core/accounts")
    Single<AccountResponse> getAccounts(@Query("cardNumber")String documentNumber);
}
