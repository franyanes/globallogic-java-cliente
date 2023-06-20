package com.fyanes.bci

import com.fyanes.bci.dto.PhoneDto
import com.fyanes.bci.dto.res.LoginResponseDto
import com.fyanes.bci.entity.AccountEntity
import com.fyanes.bci.entity.PhoneEntity
import com.fyanes.bci.exceptions.TokenDoesNotMatchAnyAccountException
import com.fyanes.bci.repository.AccountRepository
import com.fyanes.bci.repository.PhoneRepository
import com.fyanes.bci.service.impl.LoginServiceImpl
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import java.time.LocalDateTime

class LoginServiceImplSpecification extends Specification {

    private final TOKEN = "jwt_generated_token"
    private static AccountEntity savedAccount
    private static List<PhoneEntity> savedPhones
    private AccountRepository accountRepository = Mock()
    private PhoneRepository phoneRepository = Mock()
    private LoginServiceImpl service = new LoginServiceImpl(accountRepository, phoneRepository)

    void setupSpec() {
        savedAccount = AccountEntity.builder()
                .id(UUID.randomUUID())
                .name("Johnny Test")
                .email("johnnytest@gmail.com")
                .password("cmvMIl0JLO5PNIknJs5bnQ==")
                .token("jwt_generated_token")
                .created(LocalDateTime.now())
                .lastLogin(null)
                .isActive(true)
                .build()
        savedPhones = Collections.singletonList(PhoneEntity.builder()
                .id(UUID.randomUUID())
                .number(11112222)
                .cityCode(111)
                .countryCode("BSAS")
                .build())
    }

    // The setup() method is executed before each test, ensuring that the necessary mocks are set up properly.
    void setup() {
        accountRepository.findByToken(TOKEN) >> savedAccount
        phoneRepository.findByAccount(savedAccount) >> savedPhones
    }

    def "should return expected account when given correct token"() {
        given:
        AccountEntity expectedAccount = savedAccount
        List<PhoneEntity> expectedPhones = savedPhones

        when:
        ResponseEntity<LoginResponseDto> response = service.loginAccount(TOKEN)
        LoginResponseDto responseDto = response.getBody()
        List<PhoneDto> responseDtoPhones = response.getBody().getPhones()

        then:
        response.getStatusCodeValue() == 200
        responseDto.getName() == expectedAccount.getName()
        responseDto.getEmail() == expectedAccount.getEmail()
        responseDtoPhones.get(0).getNumber() == expectedPhones.get(0).getNumber()
    }

    def "should throw Exception when giving incorrect token"() {
        when:
        ResponseEntity<LoginResponseDto> response = service.loginAccount("incorrect_token")

        then:
        thrown(TokenDoesNotMatchAnyAccountException)
        response == null
        1 * accountRepository.findByToken(_)
        0 * phoneRepository.findByAccount(_)
    }
}
