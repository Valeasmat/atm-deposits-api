package com.everis.atmdeposits.controller;


import com.everis.atmdeposits.clientapi.*;
import com.everis.atmdeposits.dto.*;
import com.everis.atmdeposits.exception.BlacklistException;
import com.everis.atmdeposits.util.ErrorDetail;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(value = "ATM Deposits API",description = "Rest API to retrieve client's info")
public class ATMDepositController {

    private final PersonsClientApi personsClientApi;
    private final FingerprintsClientApi fingerprintsClientApi;
    private final ReniecClientApi reniecClientApi;
    private final CardsClientApi cardsClientApi;
    private final AccountsClientApi accountsClientApi;

    @ApiOperation(value = "Post request to retrieve client's info",
            response = ATMDepositResponse.class,
            produces = "application/stream+json")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Successful operation",response = ATMDepositResponse.class),
            @ApiResponse(code=401,message = "Invalid access, client in blacklist",response = ErrorDetail.class)
    })
    @PostMapping(value = "/atm/deposits",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Single<ATMDepositResponse> getDeposits(
            @ApiParam("Client's document number. Cannot be empty.") @RequestBody ATMDepositRequest request)
            throws BlacklistException{

        try{
            Single<PersonResponse> personMaybeBlacklist= personsClientApi.getPersonInfo(request.getDocumentNumber());
            PersonResponse personResponse = personMaybeBlacklist.blockingGet();
        }catch (Exception e){
            throw new BlacklistException("Client is registered in a blacklist");
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
