package com.everis.atmdeposits.clientapi;

import com.everis.atmdeposits.dto.PersonResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface PersonsClientApi {

    @GET("/core/persons")
    Single<PersonResponse> getPersonInfo(@Query("documentNumber")String documentNumber);

}
