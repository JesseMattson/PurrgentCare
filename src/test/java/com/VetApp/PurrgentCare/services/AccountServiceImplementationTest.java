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
        final var fakePeopleList = fakeDataGenerator.generatePeopleList(fakeDataGenerator.generateRandomInteger());
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
        final var fakePeopleList = fakeDataGenerator.generatePeopleList(fakeDataGenerator.generateRandomInteger());
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
        final var accountId = new Random().nextInt(1000);
        final var originalAccount = Account.builder().id(accountId).active(Boolean.TRUE).dateCreated(new Date()).build();
        final var updatedAccount = Account.builder().id(accountId).active(Boolean.FALSE).dateCreated(new Date(894561)).build();
        given(mockAccountRepository.findById(accountId)).willReturn(Optional.of(originalAccount));
        when(mockAccountRepository.save(originalAccount)).thenReturn(updatedAccount);

        // when
        final var actual = serviceUnderTest.updateAccount(updatedAccount, accountId);

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(originalAccount);
    }

    @Test
    public void updateAccount_whenAccountNotExists_throwEntityNotFoundException() {
        // given
        final var accountId = new Random().nextInt(1000);
        final var originalAccount = Account.builder().id(accountId).active(Boolean.TRUE).dateCreated(new Date()).build();
        final var updatedAccount = Account.builder().id(accountId).active(Boolean.TRUE).dateCreated(new Date()).build();
        ;
        given(mockAccountRepository.findById(accountId)).willThrow(new EntityNotFoundException(String.valueOf(accountId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceUnderTest.updateAccount(updatedAccount, accountId);
        });
        then(exception.getMessage()).contains(String.valueOf(accountId));
    }


    @Test
    public void toggleAccount_whenAccountExists_returnsToggledAccount() {
        // given
        final var accountId = new Random().nextInt(1000);
        final var originalAccount = Account.builder().id(accountId).active(Boolean.TRUE).dateCreated(new Date()).build();
        given(mockAccountRepository.findById(accountId)).willReturn(Optional.of(originalAccount));
        when(mockAccountRepository.save(originalAccount)).thenReturn(originalAccount);

        // when
        final var actual = serviceUnderTest.accountToggle(accountId);

        // then
        assertThat(actual.getActive()).isNotEqualTo(Boolean.TRUE);
    }

    @Test
    public void toggleAccount_whenAccountNotExists_throwEntityNotFoundException() {
        // given
        final var accountId = new Random().nextInt(1000);
        given(mockAccountRepository.findById(accountId)).willThrow(new EntityNotFoundException(String.valueOf(accountId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceUnderTest.accountToggle(accountId);
        });
        then(exception.getMessage()).contains(String.valueOf(accountId));

    }


    // Test associatePeople when account exists
    //This method adds a list of personIds to an account
    //
    @Test
    public void associatePeople_whenAccountExists_returnAssociatedPeople() {

        // Variables used everywhere
        final var accountId = fakeDataGenerator.generateRandomInteger();
        final var personId = fakeDataGenerator.generateRandomInteger();
        final var personId2 = fakeDataGenerator.generateRandomInteger();
        final var active = fakeDataGenerator.generateRandomBoolean();
        final var dateCreated = new Date();

        // Build request object
        final var request = fakeDataGenerator.generateAssociatePeopleWithAccountRequest(accountId, List.of(personId, personId2));

        // Build person objects
        final var person1 = fakeDataGenerator.generatePerson(personId);
        final var person2 = fakeDataGenerator.generatePerson(personId2);

        // Build pet object
        final var pet1 = Pet.builder().build();

        // Build original account object
        final var originalAccount = fakeDataGenerator.generateAccount(accountId, active, dateCreated, List.of(pet1), List.of(person1));

        // Build updated account object
        final var updatedAccount = fakeDataGenerator.generateAccount(accountId, active, dateCreated, List.of(pet1), List.of(person1, person2));

        // Build account response object
        final var accountResponse = fakeDataGenerator.generateAccountResponse(active, dateCreated, List.of(pet1), List.of(person1, person2));

        // Configure mocks for every call on dependencies within the service
        given(mockPersonRepository.findAllById(request.personIds)).willReturn(List.of(person1, person2));
        given(mockAccountRepository.findById(accountId)).willReturn(Optional.of(originalAccount));
        given(mockAccountRepository.save(originalAccount)).willReturn(updatedAccount);
        given(mockMapper.map(updatedAccount, AccountResponse.class)).willReturn(accountResponse);

        // when && then
        final var actual = serviceUnderTest.associatePeople(request);
        verify(mockAccountRepository).save(accountCaptor.capture());
        Account capturedAccount = accountCaptor.getValue();

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(accountResponse);
        assertThat(capturedAccount.getAccountHolders()).usingRecursiveComparison().isEqualTo(updatedAccount.getAccountHolders());
    }

    @Test
    public void accountResponse_whenAccountNotExists_throwEntityNotFoundException() {
        final var accountId = new Random().nextInt(1000);
        final var personId = new Random().nextInt(1000);
        final var active = Boolean.TRUE;
        final var dateCreated = new Date();
        final var peopleIdList = new ArrayList<Integer>(personId);

        // Build request object
        final var request = new AssociatePeopleWithAccountRequest();
        request.accountId = accountId;
        request.personIds = peopleIdList;

        // Build person object
        final var person1 = Person.builder().id(personId).build();

        // Build pet object
        final var pet1 = Pet.builder().build();

        // Build updated account object
        final var updatedAccount = Account.builder().id(accountId).active(active).dateCreated(dateCreated).pets(List.of(pet1)).accountHolders(List.of(person1)).build();

        // Configure mocks for every call on dependencies within the service
        given(mockPersonRepository.findAllById(request.personIds)).willReturn(List.of(person1));
        given(mockAccountRepository.findById(accountId)).willThrow(new EntityNotFoundException(String.valueOf(accountId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceUnderTest.associatePeople(request);
        });
        then(exception.getMessage()).contains(String.valueOf(accountId));
    }

}