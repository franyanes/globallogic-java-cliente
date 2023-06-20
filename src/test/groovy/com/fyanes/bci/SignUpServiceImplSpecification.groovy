package com.fyanes.bci

import com.fyanes.bci.dto.PhoneDto
import com.fyanes.bci.dto.req.SignUpRequestDto
import com.fyanes.bci.dto.res.SignUpResponseDto
import com.fyanes.bci.entity.AccountEntity
import com.fyanes.bci.exceptions.AccountAlreadyExistsException
import com.fyanes.bci.repository.AccountRepository
import com.fyanes.bci.service.impl.SignUpServiceImpl
import com.fyanes.bci.utils.CustomDateFormatter
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import java.time.LocalDateTime

class SignUpServiceImplSpecification extends Specification {

    private AccountRepository repository = Mock()
    private SignUpServiceImpl service = new SignUpServiceImpl(repository)
    private static AccountEntity savedAccount

    void setupSpec() {
        savedAccount = AccountEntity.builder()
                .id(UUID.randomUUID())
                .name("Johnny Test")
                .email("johnnytest@gmail.com")
                .password("a2asfGfdf1df")
                .token("jwt_generated_token")
                .created(LocalDateTime.now())
                .lastLogin(null)
                .isActive(true)
                .build()
    }

    // The setup() method is executed before each test, ensuring that the necessary mocks are set up properly.
    void setup() {
        repository.save(_ as AccountEntity) >> savedAccount
    }

    def "should create new account"() {
        given:
        repository.findByEmail(_ as String) >> null
        AccountEntity expectedAccount = savedAccount
        SignUpRequestDto requestDto = SignUpRequestDto.builder()
                .name("Johnny Test")
                .email("johnnytest@gmail.com")
                .password("a2asfGfdf1df")
                .phones(Collections.singletonList(PhoneDto.builder()
                        .number(1111111111)
                        .cityCode(111)
                        .countryCode("BSAS")
                        .build()))
                .build()

        when:
        ResponseEntity<SignUpResponseDto> response = service.createAccount(requestDto)
        SignUpResponseDto responseDto = response.getBody()

        then:
        response.getStatusCodeValue() == 200
        responseDto.getId() == expectedAccount.getId()
        responseDto.getCreated() == CustomDateFormatter.convertDateToString(expectedAccount.getCreated())
        responseDto.getToken() == expectedAccount.getToken()
    }

    def "should not create new account with already taken email"() {
        given:
        repository.findByEmail(_ as String) >> savedAccount
        SignUpRequestDto requestDto = SignUpRequestDto.builder()
                .name("Johnny Test")
                .email("johnnytest@gmail.com")
                .password("a2asfGfdf1df")
                .phones(Collections.singletonList(PhoneDto.builder()
                        .number(1111111111)
                        .cityCode(111)
                        .countryCode("BSAS")
                        .build()))
                .build()

        when:
        ResponseEntity<SignUpResponseDto> response = service.createAccount(requestDto)

        then:
        thrown(AccountAlreadyExistsException)
        response == null
        0 * repository.save(_ as AccountEntity)
    }
}
