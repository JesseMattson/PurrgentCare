package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Pet;
import com.VetApp.PurrgentCare.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AccountServiceImplementationTest {

    private AccountServiceImplementation serviceUnderTest;

    @Mock
    private AccountRepository mockAccountRepository;


    @BeforeEach
    public void setup() {
        this.serviceUnderTest = new AccountServiceImplementation(mockAccountRepository);}

    @Test
    public void getAccount_whenExist_returnOneAccount() {
        // given
        final var fakeAccount = mock(Account.class);
        final var fakeAccountId = fakeAccount.getId();
        given(mockAccountRepository.findById(fakeAccountId)).willReturn(Optional.of(fakeAccount));

        // when
        final var actual = serviceUnderTest.getAccount(fakeAccountId);

        // then
        then(actual).isEqualTo(fakeAccount);
    }

    @Test
    public void addAccount_whenValidInput_returnsValidInput() {
        // given
        final var fakeAccount = mock(Account.class);

        // when
        serviceUnderTest.addAccount(fakeAccount);

        // then
        verify(mockAccountRepository, times(1)).save(fakeAccount);
    }
    @Test
    public void deleteAccount_whenValidInput () {
        // given
        final var fakeAccountId = new Random().nextInt(1000);

        // when
        serviceUnderTest.deleteAccount(fakeAccountId);

        // then
        verify(mockAccountRepository,times(1)).deleteById(fakeAccountId);

    }

    @Test
    public void getAllAccounts_withValidInput_returnsAllAccounts() {
        // given
        final var countOfAccounts = new Random().nextInt(1000);
        final var expected = buildAccountList(countOfAccounts);
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
        final List<Account> expected = buildAccountList(0);
        given(mockAccountRepository.findAll())
                .willReturn(expected);

        // when
        final var actual = serviceUnderTest.getAllAccounts();

        // then
        then(actual).isEqualTo(expected);
    }


    private List <Account> buildAccountList(Integer countOfAccounts) {
        List<Account> accountList = new ArrayList<>(List.of());
        var i = 1;
        while (i <= countOfAccounts) {
            var account = mock(Account.class);
            accountList.add(account);
            i++;
        }
        return accountList;
    }

    // attempting to create tests for updating accounts but getting
    // stuck because we do not have the ability to set accountHolders & pets
    // --Unsure of how to continue as of now.


//    @Test
//    public void updateAccount_whenAccountExists_returnsUpdatedAccount () {
//        // given
//        final var fakeAccountId = new Random().nextInt(1000);
//        final var fakeAccount = new Account();
//        final var fakeAccountHolders = new ArrayList<accountHolders>()
//        final var originalAccount = new Account(fakeAccountId, Boolean.TRUE, "05-21-2024", fakeAccountHolders, "Pharaoh");
//        final var updatedAccount = new Account(fakeAccountId,"True", "05-21-2024");
//        given(mockAccountRepository.findById(fakeAccountId))
//                .willReturn(Optional.of(originalAccount));
//        when(mockAccountRepository.save(any(Account.class)))
//                .thenReturn(updatedAccount);
//
//        // when
//        final var actual = serviceUnderTest.updatePet(updatedAccount, fakeAccountId);
//
//        // then
//        assertThat(actual)
//                .usingRecursiveComparison()
//                .isEqualTo(originalAccount);
//    }
//
//    @Test
//    public void updatePet_whenPetNotExists_throwEntityNotFoundException () {
//        // given
//        final var fakePetId = new Random().nextInt(1000);
//        final var fakeAccount = new Account();
//        final var updatedPet = new Pet(fakePetId, "Tiger", "Cat", 2, "Male",fakeAccount);
//        given(mockPetRepository.findById(fakePetId))
//                .willThrow(new EntityNotFoundException(String.valueOf(fakePetId)));
//
//        // when && then
//        final var exception = assertThrows(EntityNotFoundException.class,() -> {
//            serviceUnderTest.updatePet(updatedPet, fakePetId);
//        });
//        then(exception.getMessage()).contains(String.valueOf(fakePetId));
//    }

}
