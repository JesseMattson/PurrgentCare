package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.FakeDataGenerator;
import com.VetApp.PurrgentCare.dtos.AccountResponse;
import com.VetApp.PurrgentCare.dtos.AssociatePeopleWithAccountRequest;
import com.VetApp.PurrgentCare.models.Account;
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
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakeAccount = fakeDataGenerator.generateDefaultAccount();
        final var fakeAccountResponse = fakeDataGenerator.generateDefaultAccountResponse();
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
        final var fakeAccountResponse = new AccountResponse();
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
        final var fakeAccounts = fakeDataGenerator.generateDefaultAccountList();
        final var fakeAccountResponses = fakeDataGenerator.generateFakeAccountResponses(fakeAccounts.size());
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
        final var fakeAccounts = new ArrayList<Account>();
        final var fakeAccountResponses = new ArrayList<AccountResponse>();
        given(mockAccountRepository.findAll()).willReturn(fakeAccounts);

        // when
        final var actual = serviceUnderTest.getAllAccounts();
        verify(mockMapper, never()).map(any(), eq(AccountResponse.class));

        // then
        then(actual).isEqualTo(fakeAccountResponses);
    }

    @Test
    public void addAccount_whenValidInput_returnsValidInput() {
        // given
        final var fakeAccountRequest = fakeDataGenerator.generateFakeAccountRequest();
        final var fakeAccount = fakeDataGenerator.generateDefaultAccount();
        final var fakeAccountResponse = fakeDataGenerator.generateDefaultAccountResponse();
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
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakeOriginalAccount = fakeDataGenerator.generateDefaultAccount();
        final var fakeUpdatedAccount = fakeDataGenerator.generateDefaultAccount();
        fakeUpdatedAccount.setId(fakeOriginalAccount.getId());
        final var fakeAccountResponse = fakeDataGenerator.generateDefaultAccountResponse();
        given(mockMapper.map(fakeAccountRequest, Account.class)).willReturn(fakeUpdatedAccount);
        given(mockAccountRepository.findById(fakeAccountId)).willReturn(Optional.of(fakeOriginalAccount));
        given(mockAccountRepository.save(fakeOriginalAccount)).willReturn(fakeUpdatedAccount);
        given(mockMapper.map(fakeUpdatedAccount, AccountResponse.class)).willReturn(fakeAccountResponse);

        // when
        final var actual = serviceUnderTest.updateAccount(fakeAccountRequest, fakeAccountId);
        verify(mockAccountRepository).save(accountCaptor.capture());
        Account capturedAccount = accountCaptor.getValue();

        // then
        assertThat(capturedAccount.getActive()).isEqualTo(fakeUpdatedAccount.getActive());
        assertThat(capturedAccount.getDateCreated()).isEqualTo(fakeUpdatedAccount.getDateCreated());
        assertThat(actual).usingRecursiveComparison().isEqualTo(fakeAccountResponse);
    }

    @Test
    public void updateAccount_whenAccountNotExists_throwEntityNotFoundException() {
        // given
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
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakeAccountActive = fakeDataGenerator.generateRandomBoolean();
        final var fakeAccount = fakeDataGenerator.generateDefaultAccount();
        final var fakeAccountResponse = fakeDataGenerator.generateDefaultAccountResponse();
        fakeAccount.setActive(fakeAccountActive);
        given(mockAccountRepository.findById(fakeAccountId)).willReturn(Optional.of(fakeAccount));
        given(mockAccountRepository.save(fakeAccount)).willReturn(fakeAccount);
        given(mockMapper.map(fakeAccount, AccountResponse.class)).willReturn(fakeAccountResponse);

        // when
        final var actual = serviceUnderTest.accountToggle(fakeAccountId);

        // then
        assertThat(fakeAccount.getActive()).isNotEqualTo(fakeAccountActive);
        assertThat(actual).isEqualTo(fakeAccountResponse);
    }

    @Test
    public void toggleAccount_whenAccountNotExists_throwEntityNotFoundException() {
        // given
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        given(mockAccountRepository.findById(fakeAccountId))
                .willThrow(new EntityNotFoundException(String.valueOf(fakeAccountId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () -> serviceUnderTest.accountToggle(fakeAccountId));
        then(exception.getMessage()).contains(String.valueOf(fakeAccountId));

    }

    @Test
    public void associatePeople_whenAccountExists_returnAssociatedPeople() {

        // Variables used everywhere
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakePersonId = fakeDataGenerator.generateRandomInteger();
        final var fakePersonId2 = fakeDataGenerator.generateRandomInteger();
        final var fakeActive = fakeDataGenerator.generateRandomBoolean();
        final var fakeDateCreated = fakeDataGenerator.generateRandomDate();
        final var fakePetId = fakeDataGenerator.generateRandomInteger();

        // Build request object
        final var fakeRequest = fakeDataGenerator.generateAssociatePeopleWithAccountRequest(fakeAccountId, List.of(fakePersonId, fakePersonId2));

        // Build person objects
        final var fakePerson1 = fakeDataGenerator.generatePerson(fakePersonId);
        final var fakePerson2 = fakeDataGenerator.generatePerson(fakePersonId2);

        // Build pet object
        final var fakePet1 = fakeDataGenerator.generateFakePet();
        fakePet1.setId(fakePetId);

        // Build list objects
        final var fakePetList = fakeDataGenerator.generateDefaultPetList();

        // Build original account object
        final var fakeOriginalAccount = fakeDataGenerator.generateFakeAccount(fakeAccountId, fakeActive, fakeDateCreated, fakePetList, List.of(fakePerson1));

        // Build updated account object
        final var fakeUpdatedAccount = fakeDataGenerator.generateFakeAccount(fakeAccountId, fakeActive, fakeDateCreated, fakePetList, List.of(fakePerson1, fakePerson2));

        // Build account response object
        final var fakeAccountResponse = fakeDataGenerator.generateFakeAccountResponse(fakeAccountId, fakeActive, fakeDateCreated, fakePetList, List.of(fakePerson1, fakePerson2));
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
        final var fakePeopleIdList = new ArrayList<Integer>(fakePersonId);

        // Build request object
        final var fakeRequest = new AssociatePeopleWithAccountRequest();
        fakeRequest.accountId = fakeAccountId;
        fakeRequest.personIds = fakePeopleIdList;

        // Build person object
        final var fakePerson1 = fakeDataGenerator.generatePerson(fakePersonId);

        // Build pet object
        final var fakePet1 = fakeDataGenerator.generateFakePet();
        fakePet1.setId(fakePetId);

        // Configure mocks for every call on dependencies within the service
        given(mockPersonRepository.findAllById(fakeRequest.personIds)).willReturn(List.of(fakePerson1));
        given(mockAccountRepository.findById(fakeAccountId)).willThrow(new EntityNotFoundException(String.valueOf(fakeAccountId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () ->
                serviceUnderTest.associatePeople(fakeRequest));
        then(exception.getMessage()).contains(String.valueOf(fakeAccountId));
    }

}