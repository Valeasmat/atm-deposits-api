package com.everis.atmdeposits.controller;

import com.everis.atmdeposits.clientapi.*;
import com.everis.atmdeposits.dto.*;
import com.everis.atmdeposits.exception.BlacklistException;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;


@Slf4j
@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    @InjectMocks
    private ATMDepositController controller;

    @Mock
    private PersonsClientApi personsClientApi;
    @Mock
    private AccountsClientApi accountsClientApi;
    @Mock
    private CardsClientApi cardsClientApi;
    @Mock
    private ReniecClientApi reniecClientApi;
    @Mock
    private FingerprintsClientApi fingerprintsClientApi;


    public void mocking(){
        CardsResponse cards=new CardsResponse();
        List<Card> list=Arrays.asList(new Card("1111222233334441",true),
                new Card("1111222233334442",true),
                new Card("1111222233334443",true),
                new Card("1111222233334444",false),
                new Card("1111222233334445",false),
                new Card("1111222233334446",false));
        cards.setCards(list);
        Single<CardsResponse> cardsResponse=Single.just(cards);
        Mockito.when(cardsClientApi.getCards(Mockito.anyString())).thenReturn(cardsResponse);

        AccountResponse accounts=new AccountResponse();
        accounts.setAccountNumber("111122223333444?");
        accounts.setAmount(1000L);
        Single<AccountResponse> accountResponse=Single.just(accounts);
        Mockito.when(accountsClientApi.getAccounts(Mockito.anyString())).thenReturn(accountResponse);
    }

    public void initFingerprints(){
        PersonResponse person1=new PersonResponse();
        person1.setDocument("10000000");
        person1.setId(1);
        person1.setFingerprint(true);
        person1.setBlacklist(false);
        Single<PersonResponse> personInfo1=Single.just(person1);
        Mockito.when(personsClientApi.getPersonInfo(Mockito.anyString())).thenReturn(personInfo1);

        FingerprintResponse fingerprint1=new FingerprintResponse();
        fingerprint1.setEntityName("Core");
        fingerprint1.setSuccess(true);
        Single<FingerprintResponse> fingerprintResponse1=Single.just(fingerprint1);
        Mockito.when(fingerprintsClientApi.validateFingerprints(Mockito.any(ATMDepositRequest.class))).thenReturn(fingerprintResponse1);
    }

    public void initReniec(){
        PersonResponse person1=new PersonResponse();
        person1.setDocument("10000000");
        person1.setId(1);
        person1.setFingerprint(false);
        person1.setBlacklist(false);
        Single<PersonResponse> personInfo1=Single.just(person1);
        Mockito.when(personsClientApi.getPersonInfo(Mockito.anyString())).thenReturn(personInfo1);

        ReniecResponse reniec1=new ReniecResponse();
        reniec1.setEntityName("Reniec");
        reniec1.setSuccess(true);
        Single<ReniecResponse> reniecResponse1=Single.just(reniec1);
        Mockito.when(reniecClientApi.validateReniec(Mockito.any(ATMDepositRequest.class))).thenReturn(reniecResponse1);
    }

    public void initException(){
        Mockito.when(personsClientApi.getPersonInfo(Mockito.anyString())).thenThrow(new IllegalArgumentException());
    }

    @Test
    public void controllerTestFingerprint() throws BlacklistException {
        mocking();
        initFingerprints();
        ATMDepositRequest request=new ATMDepositRequest("10000000");

        TestObserver<ATMDepositResponse> responseTest=controller.getDeposits(request).test();

        responseTest.assertNoErrors()
                .assertValueCount(1)
                .assertNever(r->r.getCustomerAmount()!=3000L)
                .assertNever(r->r.getValidAccounts().size()!=3)
                .assertNever(r->!r.getFingerprintEntityName().equals("Core"));


    }

    @Test
    public void controllerTestReniec() throws Exception {
        mocking();
        initReniec();
        ATMDepositRequest request=new ATMDepositRequest("10000000");

        TestObserver<ATMDepositResponse> responseTest=controller.getDeposits(request).test();

        responseTest.assertNoErrors()
                .assertValueCount(1)
                .assertNever(r->r.getCustomerAmount()!=3000L)
                .assertNever(r->r.getValidAccounts().size()!=3)
                .assertNever(r->!r.getFingerprintEntityName().equals("Reniec"));


    }

    @Test
    public void controllerTestException() throws BlacklistException {
        initException();
        ATMDepositRequest request=new ATMDepositRequest("10000000");
        Exception exception = Assertions.assertThrows(BlacklistException.class, () -> {
            controller.getDeposits(request);
        });

        String expectedMessage = "Client is registered in a blacklist";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage,actualMessage);

    }

}
