package com.everis.atmdeposits.controller;


import com.everis.atmdeposits.clientapi.*;
import com.everis.atmdeposits.dto.*;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ATMDepositController {

    private final PersonsClientApi personsClientApi;
    private final FingerprintsClientApi fingerprintsClientApi;
    private final ReniecClientApi reniecClientApi;
    private final CardsClientApi cardsClientApi;
    private final AccountsClientApi accountsClientApi;

    /*
    @GetMapping("/prueba")
    public String prueba(){
        try {
            Single<PersonResponse> personInfo = personsClientApi.getPersonInfo("10000005");
            return personInfo.blockingGet().toString();
        } catch (Exception e){
            return "error";
        }
    }*/

    @PostMapping("/atm/deposits")
    public Single<ATMDepositResponse> getDeposits(@RequestBody ATMDepositRequest request){

        try{
            Single<PersonResponse> personMaybeBlacklist= personsClientApi.getPersonInfo(request.getDocumentNumber());
            PersonResponse personResponse = personMaybeBlacklist.blockingGet();
        }catch (Exception e){
            return Single.just(new ATMDepositResponse());
        }


        Observable<ATMDepositResponse> atmResponse = personsClientApi
                .getPersonInfo(request.getDocumentNumber())
                .toObservable()
                .map(p -> {
                    List<AccountNumber> accountNumbers = new ArrayList<>();
                    //Response initialization
                    ATMDepositResponse response = new ATMDepositResponse();

                    //Flag to validate fingerprints or reniec
                    if (p.getFingerprint()) {
                        response.setFingerprintEntityName(fingerprintsClientApi
                                .validateFingerprints(request)
                                .blockingGet()
                                .getEntityName());
                    } else {
                        response.setFingerprintEntityName(reniecClientApi
                                .validateReniec(request)
                                .blockingGet()
                                .getEntityName());

                    }

                    //reducing total amount of accounts
                    Long totalAmount = cardsClientApi
                            .getCards(p.getDocument())
                            .toObservable()
                            .flatMap(list -> Observable.fromIterable(list.getCards()))
                            .filter(Card::getActive)
                            .map(Card::getCardNumber)
                            .map(card -> accountsClientApi
                                    .getAccounts(card)
                                    .toObservable())
                            .flatMap(Observable::wrap)
                            .doOnNext(account -> {
                                //log.info(account.getAccountNumber());
                                //side effect to collect list of accounts
                                accountNumbers.add(new AccountNumber(account.getAccountNumber()));
                            })
                            .map(AccountResponse::getAmount)
                            .reduce(Long::sum).blockingGet();

                    //set fields
                    response.setValidAccounts(accountNumbers);
                    response.setCustomerAmount(totalAmount);

                    return response;
                });

        return atmResponse.elementAt(0,new ATMDepositResponse());
    }
}
