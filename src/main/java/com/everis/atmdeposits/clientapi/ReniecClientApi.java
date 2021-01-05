package com.everis.atmdeposits.clientapi;

import com.everis.atmdeposits.dto.ATMDepositRequest;
import com.everis.atmdeposits.dto.ReniecResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReniecClientApi {

    @POST("/external/reniec/validate")
    Single<ReniecResponse> validateReniec(@Body ATMDepositRequest atmDepositRequest);


}
