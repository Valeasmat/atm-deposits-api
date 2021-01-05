package com.everis.atmdeposits.clientapi;


import com.everis.atmdeposits.dto.CardsResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CardsClientApi {


    @GET("/core/cards")
    Single<CardsResponse> getCards(@Query("documentNumber")String documentNumber);

}
