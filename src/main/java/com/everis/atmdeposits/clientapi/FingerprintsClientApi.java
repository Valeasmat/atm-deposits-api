package com.everis.atmdeposits.clientapi;

import com.everis.atmdeposits.dto.ATMDepositRequest;
import com.everis.atmdeposits.dto.FingerprintResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FingerprintsClientApi {

    @POST("/core/fingerprints/validate")
    Single<FingerprintResponse> validateFingerprints(@Body ATMDepositRequest atmDepositRequest);

}
