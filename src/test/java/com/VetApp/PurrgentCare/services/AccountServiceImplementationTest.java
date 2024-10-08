package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.FakeDataGenerator;
import com.VetApp.PurrgentCare.dtos.AccountResponse;
import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Person;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AccountServiceImplementationTest {

    private final FakeDataGenerator fakeDataGenerator = new FakeDataGenerator();
    private AccountServiceImplementation serviceUnderTest;
    @Mock
    private AccountRepository mockAccountRepository;
    @Mock
    private PersonRepository mockPersonRepository;
    @Mock
    private ModelMapper mockMapper;
    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    @BeforeEach
    public void setup() {
        this.serviceUnderTest = new AccountServiceImplementation(mockAccountRepository, mockPersonRepository, mockMapper);
    }

    @Test
    public void getAccount_whenExist_returnOneAccount() {
        // given

        final var fakeAccount = fakeDataGenerator.generateFakeAccount();
        final var fakeAccountId = fakeAccount.getId();
        final var fakeAccountResponse = fakeDataGenerator.generateFakeAccountResponse();
        given(mockAccountRepository.findById(fakeAccountId)).willReturn(Optional.of(fakeAccount));
        given(mockMapper.map(Optional.of(fakeAccount), AccountResponse.class)).willReturn(fakeAccountResponse);

        // when
        final var actual = serviceUnderTest.getAccount(fakeAccountId);

        // then
        then(actual).isEqualTo(fakeAccountResponse);
    }

    @Test
    public void getAccount_whenNotExist_returnEmptyAccountResponse() {
        // given
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakeAccountResponse = fakeDataGenerator.generateFakeAccountResponse();
        // ToDo: Make a method inside fakdDataGenerator to create a null account response.
            // Could also just do a new AccountResponse
        fakeAccountResponse.setId(null);
        fakeAccountResponse.setActive(null);
        fakeAccountResponse.setDateCreated(null);
        fakeAccountResponse.setPets(null);
        fakeAccountResponse.setAccountHolders(null);
        given(mockAccountRepository.findById(fakeAccountId)).willReturn(Optional.empty());

        // when
        final var actual = serviceUnderTest.getAccount(fakeAccountId);

        // then
        then(actual).isInstanceOf(AccountResponse.class);
        then(actual).usingRecursiveComparison().isEqualTo(fakeAccountResponse);
    }

    @Test
    public void getAllAccounts_withValidInput_returnsAllAccounts() {
        // given
        final var fakeAccounts = fakeDataGenerator.generatefakeAccountList();
        final var fakeAccountResponses = fakeDataGenerator.generateFakeAccountResponseList(fakeAccounts.size());
        given(mockAccountRepository.findAll())
                .willReturn(fakeAccounts);
        for (var i = 0; i < fakeAccounts.size(); i++) {
            given(mockMapper.map(fakeAccounts.get(i), AccountResponse.class))
                    .willReturn(fakeAccountResponses.get(i));
        }

        // when
        final var actual = serviceUnderTest.getAllAccounts();

        // then
        then(actual).isEqualTo(fakeAccountResponses);
        then(actual).hasSize(fakeAccountResponses.size());
    }

    @Test
    public void getAllAccounts_whenNoAccounts_returnsEmptyList() {
        // given
        final var emptyAccounts = new ArrayList<Account>();
        final var emptyAccountResponses = new ArrayList<AccountResponse>();
        given(mockAccountRepository.findAll()).willReturn(emptyAccounts);

        // when
        final var actual = serviceUnderTest.getAllAccounts();

        // then
        verify(mockMapper, never()).map(any(), eq(AccountResponse.class));
        then(actual).isEqualTo(emptyAccountResponses);
    }

    @Test
    public void addAccount_whenValidInput_returnsValidInput() {
        // given
        final var fakeAccountRequest = fakeDataGenerator.generateFakeAccountRequest();
        final var fakeAccount = fakeDataGenerator.generateFakeAccount();
        final var fakeAccountResponse = fakeDataGenerator.generateFakeAccountResponse();
        given(mockMapper.map(fakeAccountRequest, Account.class)).willReturn(fakeAccount);
        given(mockMapper.map(fakeAccount, AccountResponse.class)).willReturn(fakeAccountResponse);
        // when
        final var actual = serviceUnderTest.addAccount(fakeAccountRequest);

        // then
        verify(mockAccountRepository, times(1)).save(fakeAccount);
        then(actual).isEqualTo(fakeAccountResponse);
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
    public void updateAccount_whenAccountExists_returnsUpdatedAccount() {
        // given
        final var fakeAccountRequest = fakeDataGenerator.generateFakeAccountRequest();
        final var fakeOriginalAccount = fakeDataGenerator.generateFakeAccount();
        final var fakeUpdatedAccount = fakeDataGenerator.generateFakeAccount(fakeOriginalAccount);
        final var fakeAccountResponse = fakeDataGenerator.generateFakeAccountResponse();
        given(mockMapper.map(fakeAccountRequest, Account.class))
                .willReturn(fakeUpdatedAccount);
        given(mockAccountRepository.findById(fakeOriginalAccount.getId()))
                .willReturn(Optional.of(fakeOriginalAccount));
        given(mockAccountRepository.save(fakeOriginalAccount))
                .willReturn(fakeUpdatedAccount);
        given(mockMapper.map(fakeUpdatedAccount, AccountResponse.class))
                .willReturn(fakeAccountResponse);

        // when
        final var actual = serviceUnderTest.updateAccount(fakeAccountRequest, fakeOriginalAccount.getId());

        // then
        verify(mockAccountRepository).save(accountCaptor.capture());
        assertThat(accountCaptor.getValue().getActive()).isEqualTo(fakeUpdatedAccount.getActive());
        assertThat(accountCaptor.getValue().getDateCreated()).isEqualTo(fakeUpdatedAccount.getDateCreated());
        assertThat(actual).usingRecursiveComparison().isEqualTo(fakeAccountResponse);
    }

    @Test

    public void updateAccount_whenAccountNotExists_throwEntityNotFoundException() {
        // given
        // ToDo: comb through tests and see if we're pulling in classes if not Exists
        final var fakeAccountRequest = fakeDataGenerator.generateFakeAccountRequest();
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        given(mockAccountRepository.findById(fakeAccountId))
                .willThrow(new EntityNotFoundException(String.valueOf(fakeAccountId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () ->
                serviceUnderTest.updateAccount(fakeAccountRequest, fakeAccountId));
        then(exception.getMessage()).contains(String.valueOf(fakeAccountId));
    }


    @Test

    public void toggleAccount_whenAccountExists_returnsToggledAccount() {
        // given
        final var fakeAccount = fakeDataGenerator.generateFakeAccount();
        final var fakeAccountId = fakeAccount.getId();
        final var fakeAccountResponse = fakeDataGenerator.generateFakeAccountResponse();
        final var initialAccountActive = fakeAccount.getActive();
        given(mockAccountRepository.findById(fakeAccountId)).willReturn(Optional.of(fakeAccount));
        given(mockAccountRepository.save(fakeAccount)).willReturn(fakeAccount);
        given(mockMapper.map(fakeAccount, AccountResponse.class)).willReturn(fakeAccountResponse);

        // when
        final var actual = serviceUnderTest.accountToggle(fakeAccountId);

        // then
        assertThat(fakeAccount.getActive()).isNotEqualTo(initialAccountActive);
        assertThat(actual).isEqualTo(fakeAccountResponse);
    }

    @Test
    public void toggleAccount_whenAccountNotExists_throwEntityNotFoundException() {
        // given
        final var fakeAccountId = fakeDataGenerator.generateFakeAccount().getId();
        given(mockAccountRepository.findById(fakeAccountId))
                .willThrow(new EntityNotFoundException(String.valueOf(fakeAccountId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () -> serviceUnderTest.accountToggle(fakeAccountId));
        then(exception.getMessage()).contains(String.valueOf(fakeAccountId));

    }

    @Test

    public void associatePeople_whenAccountExists_returnAssociatedPeople() {

        // Build request object
        final var fakeRequest = fakeDataGenerator.generateFakeAssociatePeopleWithAccountRequest();
        final var fakePersons = fakeDataGenerator.generateDefaultPersonList();

        // Build original account object
        final var fakeOriginalAccount = fakeDataGenerator.generateFakeAccount();

        //Build updated list of accountholders
        final var fakeUpdatedAccountHolders = new ArrayList<Person>();
        fakeUpdatedAccountHolders.addAll(fakeOriginalAccount.getAccountHolders());
        fakeUpdatedAccountHolders.addAll(fakePersons);

        // Build updated account object
        final var fakeUpdatedAccount = fakeDataGenerator.generateFakeAccount(fakeOriginalAccount);

        // Build account response object
        final var fakeAccountResponse = fakeDataGenerator.generateFakeAccountResponse();
        fakeAccountResponse.setAccountHolders(fakeUpdatedAccountHolders);

        // Configure mocks for every call on dependencies within the service
        given(mockPersonRepository.findAllById(fakeRequest.personIds)).willReturn(fakePersons);
        given(mockAccountRepository.findById(fakeRequest.accountId)).willReturn(Optional.of(fakeOriginalAccount));
        given(mockAccountRepository.save(fakeOriginalAccount)).willReturn(fakeUpdatedAccount);
        given(mockMapper.map(fakeUpdatedAccount, AccountResponse.class)).willReturn(fakeAccountResponse);

        // when && then
        final var actual = serviceUnderTest.associatePeople(fakeRequest);

        // then
        verify(mockAccountRepository).save(accountCaptor.capture());
        assertThat(actual).usingRecursiveComparison().isEqualTo(fakeAccountResponse);
        assertThat(accountCaptor.getValue().getAccountHolders()).usingRecursiveComparison().isEqualTo(fakeUpdatedAccount.getAccountHolders());
    }

    @Test
    public void associatePeople_whenAccountNotExists_throwEntityNotFoundException() {
        final var fakeAccountId = fakeDataGenerator.generateFakeAccount().getId();
        final var fakeCountOfFakePerson = fakeDataGenerator.generateRandomInteger();
        final var fakePeopleIdList = fakeDataGenerator.generateFakePersonList(fakeCountOfFakePerson);

        // Build request object
        final var fakeRequest = fakeDataGenerator.generateFakeAssociatePeopleWithAccountRequest();
        fakeRequest.accountId = fakeAccountId;
        fakeRequest.personIds = fakePeopleIdList.stream()
                .map(Person::getId)
                .collect(Collectors.toList());

        // Build person object
        final var fakePerson1 = fakeDataGenerator.generateFakePerson();


        // Configure mocks for every call on dependencies within the service
        given(mockPersonRepository.findAllById(fakeRequest.personIds)).willReturn(List.of(fakePerson1));
        given(mockAccountRepository.findById(fakeAccountId)).willThrow(new EntityNotFoundException(String.valueOf(fakeAccountId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () ->
                serviceUnderTest.associatePeople(fakeRequest));
        then(exception.getMessage()).contains(String.valueOf(fakeAccountId));
    }

}