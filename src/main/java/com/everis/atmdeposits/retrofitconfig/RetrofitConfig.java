package com.everis.atmdeposits.retrofitconfig;


import com.everis.atmdeposits.clientapi.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Value("${url.persons}")
    private String personsUrl;
    @Value("${url.accounts}")
    private String accountsUrl;
    @Value("${url.cards}")
    private String cardsUrl;
    @Value("${url.fingerprints}")
    private String fingerprintsUrl;
    @Value("${url.reniec}")
    private String reniecUrl;

    @Bean
    PersonsClientApi personsClientApi(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(personsUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(PersonsClientApi.class);
    }

    @Bean
    CardsClientApi cardsClientApi(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(cardsUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(CardsClientApi.class);
    }


    @Bean
    AccountsClientApi accountsClientApi(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(accountsUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(AccountsClientApi.class);
    }

    @Bean
    FingerprintsClientApi fingerprintsClientApi(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(fingerprintsUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(FingerprintsClientApi.class);
    }

    @Bean
    ReniecClientApi reniecClientApi(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(reniecUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ReniecClientApi.class);
    }
}
