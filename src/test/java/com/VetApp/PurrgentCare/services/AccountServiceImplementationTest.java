
package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.FakeDataGenerator;
import com.VetApp.PurrgentCare.dtos.AccountResponse;
import com.VetApp.PurrgentCare.dtos.AssociatePeopleWithAccountRequest;
import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.models.Pet;
import com.VetApp.PurrgentCare.repositories.AccountRepository;
import com.VetApp.PurrgentCare.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AccountServiceImplementationTest {

    private AccountServiceImplementation serviceUnderTest;

    @Mock
    private AccountRepository mockAccountRepository;

    @Mock
    private PersonRepository mockPersonRepository;

    @Mock
    private ModelMapper mockMapper;
    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    private final FakeDataGenerator fakeDataGenerator = new FakeDataGenerator();

    @BeforeEach
    public void setup() {
        this.serviceUnderTest = new AccountServiceImplementation(mockAccountRepository, mockPersonRepository, mockMapper);
    }

    @Test
    public void getAccount_whenExist_returnOneAccount() {
        // given
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakeActive = fakeDataGenerator.generateRandomBoolean();
        final var fakeDateCreated = fakeDataGenerator.generateRandomDate();
        final var fakePetList = fakeDataGenerator.generatePetList(fakeDataGenerator.generateRandomInteger());
        final var fakePeopleList = fakeDataGenerator.generatePersonList(fakeDataGenerator.generateRandomInteger());
        final var fakeAccount = fakeDataGenerator.generateAccount(fakeAccountId, fakeActive, fakeDateCreated, fakePetList, fakePeopleList);
        given(mockAccountRepository.findById(fakeAccountId)).willReturn(Optional.of(fakeAccount));

        // when
        final var actual = serviceUnderTest.getAccount(fakeAccountId);

        // then
        then(actual).isEqualTo(fakeAccount);
    }

    @Test
    public void addAccount_whenValidInput_returnsValidInput() {
        // given
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakeActive = fakeDataGenerator.generateRandomBoolean();
        final var fakeDateCreated = fakeDataGenerator.generateRandomDate();
        final var fakePetList = fakeDataGenerator.generatePetList(fakeDataGenerator.generateRandomInteger());
        final var fakePeopleList = fakeDataGenerator.generatePersonList(fakeDataGenerator.generateRandomInteger());
        final var fakeAccount = fakeDataGenerator.generateAccount(fakeAccountId, fakeActive, fakeDateCreated, fakePetList, fakePeopleList);

        // when
        serviceUnderTest.addAccount(fakeAccount);

        // then
        verify(mockAccountRepository, times(1)).save(fakeAccount);
    }

    @Test
    public void deleteAccount_whenValidInput() {
        // given
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();

        // when
        serviceUnderTest.deleteAccount(fakeAccountId);

        // then
        verify(mockAccountRepository, times(1)).deleteById(fakeAccountId);

    }

    @Test
    public void getAllAccounts_withValidInput_returnsAllAccounts() {
        // given
        final var countOfAccounts = fakeDataGenerator.generateRandomInteger();
        final var expected = fakeDataGenerator.generateAccountList(countOfAccounts);
        given(mockAccountRepository.findAll())
                .willReturn(expected);

        // when
        final var actual = serviceUnderTest.getAllAccounts();

        // then
        then(actual).isEqualTo(expected);
        then(actual).hasSize(countOfAccounts);
    }


    @Test
    public void getAllAccounts_whenNoAccounts_returnsEmptyList() {
        // given
        final var expected = fakeDataGenerator.generateAccountList(0);
        given(mockAccountRepository.findAll())
                .willReturn(expected);

        // when
        final var actual = serviceUnderTest.getAllAccounts();

        // then
        then(actual).isEqualTo(expected);
    }


    // attempting to create tests for updating accounts but getting
    // stuck because we do not have the ability to set accountHolders & pets
    // --Unsure of how to continue as of now.


    @Test
    public void updateAccount_whenAccountExists_returnsUpdatedAccount() {
        // given
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakeActiveTrue = Boolean.TRUE;
        final var fakeActiveFalse = Boolean.FALSE;
        final var fakeOriginalDateCreated = fakeDataGenerator.generateRandomDate();
        final var fakeUpdatedDateCreated = fakeDataGenerator.generateRandomDate();
        final var fakePetList = fakeDataGenerator.generatePetList(fakeDataGenerator.generateRandomInteger());
        final var fakePeopleList = fakeDataGenerator.generatePersonList(fakeDataGenerator.generateRandomInteger());

        final var fakeOriginalAccount = fakeDataGenerator.generateAccount(fakeAccountId, fakeActiveTrue, fakeOriginalDateCreated, fakePetList, fakePeopleList);
        final var fakeUpdatedAccount = fakeDataGenerator.generateAccount(fakeAccountId, fakeActiveFalse, fakeUpdatedDateCreated, fakePetList, fakePeopleList);
        given(mockAccountRepository.findById(fakeAccountId)).willReturn(Optional.of(fakeOriginalAccount));
        when(mockAccountRepository.save(fakeOriginalAccount)).thenReturn(fakeUpdatedAccount);

        // when
        final var actual = serviceUnderTest.updateAccount(fakeUpdatedAccount, fakeAccountId);

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(fakeOriginalAccount);
    }

    @Test
    public void updateAccount_whenAccountNotExists_throwEntityNotFoundException() {
        // given
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakeActive = fakeDataGenerator.generateRandomBoolean();
        final var fakeDateCreated = fakeDataGenerator.generateRandomDate();
        final var fakePetList = fakeDataGenerator.generatePetList(fakeDataGenerator.generateRandomInteger());
        final var fakePeopleList = fakeDataGenerator.generatePersonList(fakeDataGenerator.generateRandomInteger());

        final var fakeUpdatedAccount = fakeDataGenerator.generateAccount(fakeAccountId, fakeActive, fakeDateCreated, fakePetList, fakePeopleList);

        given(mockAccountRepository.findById(fakeAccountId)).willThrow(new EntityNotFoundException(String.valueOf(fakeAccountId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceUnderTest.updateAccount(fakeUpdatedAccount, fakeAccountId);
        });
        then(exception.getMessage()).contains(String.valueOf(fakeAccountId));
    }


    @Test
    public void toggleAccount_whenAccountExists_returnsToggledAccount() {
        // given
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakeActiveTrue = Boolean.TRUE;
        final var fakeDateCreated = fakeDataGenerator.generateRandomDate();
        final var fakePetList = fakeDataGenerator.generatePetList(fakeDataGenerator.generateRandomInteger());
        final var fakePeopleList = fakeDataGenerator.generatePersonList(fakeDataGenerator.generateRandomInteger());

        final var fakeOriginalAccount = fakeDataGenerator.generateAccount(fakeAccountId, fakeActiveTrue, fakeDateCreated, fakePetList, fakePeopleList);

        given(mockAccountRepository.findById(fakeAccountId)).willReturn(Optional.of(fakeOriginalAccount));
        when(mockAccountRepository.save(fakeOriginalAccount)).thenReturn(fakeOriginalAccount);

        // when
        final var actual = serviceUnderTest.accountToggle(fakeAccountId);

        // then
        assertThat(actual.getActive()).isNotEqualTo(Boolean.TRUE);
    }

    @Test
    public void toggleAccount_whenAccountNotExists_throwEntityNotFoundException() {
        // given
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        given(mockAccountRepository.findById(fakeAccountId)).willThrow(new EntityNotFoundException(String.valueOf(fakeAccountId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceUnderTest.accountToggle(fakeAccountId);
        });
        then(exception.getMessage()).contains(String.valueOf(fakeAccountId));

    }


    // Test associatePeople when account exists
    //This method adds a list of personIds to an account
    //
    @Test
    public void associatePeople_whenAccountExists_returnAssociatedPeople() {

        // Variables used everywhere
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakePersonId  = fakeDataGenerator.generateRandomInteger();
        final var fakePersonId2 = fakeDataGenerator.generateRandomInteger();
        final var fakeActive = fakeDataGenerator.generateRandomBoolean();
        final var fakeDateCreated = fakeDataGenerator.generateRandomDate();
        final var fakePetId = fakeDataGenerator.generateRandomInteger();
        final var fakeCountOfFakePets = fakeDataGenerator.generateRandomInteger();
        final var fakeCountOfFakePeople = fakeDataGenerator.generateRandomInteger();

        // Build request object
        final var fakeRequest = fakeDataGenerator.generateAssociatePeopleWithAccountRequest(fakeAccountId, List.of(fakePersonId, fakePersonId2));

        // Build person objects
        final var fakePerson1 = fakeDataGenerator.generatePerson(fakePersonId);
        final var fakePerson2 = fakeDataGenerator.generatePerson(fakePersonId2);

        // Build pet object
        final var fakePet1 = fakeDataGenerator.generatePet(fakePetId);

        // Build list objects
        final var fakePetList = fakeDataGenerator.generatePetList(fakeCountOfFakePets);
        final var fakePeopleList = fakeDataGenerator.generatePersonList(fakeCountOfFakePeople);

        // Build original account object
        final var fakeOriginalAccount = fakeDataGenerator.generateAccount(fakeAccountId, fakeActive, fakeDateCreated, fakePetList, List.of(fakePerson1));

        // Build updated account object
        final var fakeUpdatedAccount = fakeDataGenerator.generateAccount(fakeAccountId, fakeActive, fakeDateCreated, fakePetList, List.of(fakePerson1, fakePerson2));

        // Build account response object
        final var fakeAccountResponse = fakeDataGenerator.generateAccountResponse(fakeActive, fakeDateCreated, fakePetList, List.of(fakePerson1, fakePerson2));
        // Configure mocks for every call on dependencies within the service
        given(mockPersonRepository.findAllById(fakeRequest.personIds)).willReturn(List.of(fakePerson1, fakePerson2));
        given(mockAccountRepository.findById(fakeAccountId)).willReturn(Optional.of(fakeOriginalAccount));
        given(mockAccountRepository.save(fakeOriginalAccount)).willReturn(fakeUpdatedAccount);
        given(mockMapper.map(fakeUpdatedAccount, AccountResponse.class)).willReturn(fakeAccountResponse);

        // when && then
        final var actual = serviceUnderTest.associatePeople(fakeRequest);
        verify(mockAccountRepository).save(accountCaptor.capture());
        Account capturedAccount = accountCaptor.getValue();

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(fakeAccountResponse);
        assertThat(capturedAccount.getAccountHolders()).usingRecursiveComparison().isEqualTo(fakeUpdatedAccount.getAccountHolders());
    }

    @Test
    public void accountResponse_whenAccountNotExists_throwEntityNotFoundException() {
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakePersonId = fakeDataGenerator.generateRandomInteger();
        final var fakePetId = fakeDataGenerator.generateRandomInteger();
        final var fakeActiveTrue = Boolean.TRUE;
        final var fakeDateCreated = fakeDataGenerator.generateRandomDate();
        final var fakePeopleIdList = new ArrayList<Integer>(fakePersonId);
        final var fakeCountOfFakePets = fakeDataGenerator.generateRandomInteger();
        final var fakeCountOfFakePeople = fakeDataGenerator.generateRandomInteger();
        final var fakePetList = fakeDataGenerator.generatePetList(fakeCountOfFakePets);
        final var fakePersonList = fakeDataGenerator.generatePersonList(fakeCountOfFakePeople);

        // Build request object
        final var fakeRequest = new AssociatePeopleWithAccountRequest();
        fakeRequest.accountId = fakeAccountId;
        fakeRequest.personIds = fakePeopleIdList;

        // Build person object
        final var fakePerson1 = fakeDataGenerator.generatePerson(fakePersonId);

        // Build pet object
        final var fakePet1 = fakeDataGenerator.generatePet(fakePetId);

        // Build updated account object
        final var updatedAccount = fakeDataGenerator.generateAccount(fakeAccountId, fakeActiveTrue, fakeDateCreated,fakePetList, fakePersonList);

        // Configure mocks for every call on dependencies within the service
        given(mockPersonRepository.findAllById(fakeRequest.personIds)).willReturn(List.of(fakePerson1));
        given(mockAccountRepository.findById(fakeAccountId)).willThrow(new EntityNotFoundException(String.valueOf(fakeAccountId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceUnderTest.associatePeople(fakeRequest);
        });
        then(exception.getMessage()).contains(String.valueOf(fakeAccountId));
    }

}