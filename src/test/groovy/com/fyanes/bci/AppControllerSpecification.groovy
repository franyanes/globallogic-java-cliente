package com.fyanes.bci


import com.fyanes.bci.dto.req.SignUpRequestDto
import com.fyanes.bci.dto.res.SignUpResponseDto
import com.fyanes.bci.service.impl.LoginServiceImpl
import com.fyanes.bci.service.impl.SignUpServiceImpl
import org.json.JSONObject
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import java.time.LocalDateTime

@WebMvcTest
@AutoConfigureMockMvc
class AppControllerSpecification extends Specification {

    @SpringBean
    private SignUpServiceImpl signUpService = Mock()
    @SpringBean
    private LoginServiceImpl loginService = Mock()
    @Autowired
    MockMvc mockMvc

    def "should create account when given valid fields for signup"() {
        given:
        String mockRequestContent = new JSONObject().put("name", "Simple Account")
                .put("email", "simple_account@gmail.com")
                .put("password", "Password11")
                .toString()
        SignUpResponseDto mockResponseDto = SignUpResponseDto.builder()
                        .id(UUID.randomUUID())
                        .created(LocalDateTime.now())
                        .lastLogin(null)
                        .token("jwt_generated_token")
                        .isActive(null)
                        .build()
        signUpService.createAccount(_ as SignUpRequestDto) >> new ResponseEntity<>(mockResponseDto, HttpStatus.OK)

        when:
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/sign_up")
                        .contentType("application/json")
                        .content(mockRequestContent)
        )
                .andReturn().getResponse()
        JSONObject responseBodyObj = new JSONObject(response.getContentAsString())

        then:
        response.getStatus().toInteger() == 200
        responseBodyObj.get("id") == mockResponseDto.getId().toString()
    }

    def "should return error message when given no email or no password for signup"() {
        given:
        String mockNoEmailRequestContent = new JSONObject().put("name", "Simple Account")
                .put("password", "Password11")
                .toString()
        String mockNoPasswordRequestContent = new JSONObject().put("name", "Simple Account")
                .put("email", "simple_account@gmail.com")
                .toString()

        when:
        MockHttpServletResponse noEmailResponse = mockMvc.perform(
                MockMvcRequestBuilders.post("/sign_up")
                        .contentType("application/json")
                        .content(mockNoEmailRequestContent)
        )
                .andReturn().getResponse()
        MockHttpServletResponse noPasswordResponse = mockMvc.perform(
                MockMvcRequestBuilders.post("/sign_up")
                        .contentType("application/json")
                        .content(mockNoPasswordRequestContent)
        )
                .andReturn().getResponse()
        String noEmailErrorMessage = getErrorMessageFromContent(new JSONObject(noEmailResponse.getContentAsString()))
        String noPasswordErrorMessage = getErrorMessageFromContent(new JSONObject(noPasswordResponse.getContentAsString()))

        then:
        noEmailResponse.getStatus().toInteger() != 200
        noEmailErrorMessage == "The email field is required"
        noPasswordResponse.getStatus().toInteger() != 200
        noPasswordErrorMessage == "The password field is required"
    }

    def "should return error message when given email has an invalid format for signup"() {
        given:
        String badEmailStr = "simple_account.gmail"
        String mockRequestContent = new JSONObject().put("name", "Simple Account")
                .put("email", badEmailStr)
                .put("password", "Password11")
                .toString()

        when:
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/sign_up")
                        .contentType("application/json")
                        .content(mockRequestContent)
        )
                .andReturn().getResponse()
        String errorMessage = getErrorMessageFromContent(new JSONObject(response.getContentAsString()))

        then:
        response.getStatus().toInteger() != 200
        errorMessage == "The email format is invalid"
    }

    def "should return error message when given password is too short or too long for signup"() {
        given:
        String badPasswordStr = "Short11"
        String mockRequestContent = new JSONObject().put("name", "Simple Account")
                .put("email", "simple_account@gmail.com")
                .put("password", badPasswordStr)
                .toString()

        when:
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/sign_up")
                        .contentType("application/json")
                        .content(mockRequestContent)
        )
                .andReturn().getResponse()
        String errorMessage = getErrorMessageFromContent(new JSONObject(response.getContentAsString()))

        then:
        response.getStatus().toInteger() != 200
        errorMessage == "The password must be between 8 and 12 characters long and can only contain alphanumeric characters"
    }

    def "should return error message when given password does not contain two numeric characters for signup"() {
        given:
        String badPasswordStr = "Password"
        String mockRequestContent = new JSONObject().put("name", "Simple Account")
                .put("email", "simple_account@gmail.com")
                .put("password", badPasswordStr)
                .toString()

        when:
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/sign_up")
                        .contentType("application/json")
                        .content(mockRequestContent)
        )
                .andReturn().getResponse()
        String errorMessage = getErrorMessageFromContent(new JSONObject(response.getContentAsString()))

        then:
        response.getStatus().toInteger() != 200
        errorMessage == "The password must contain two digits"
    }

    def "should return error message when given password does not contain an uppercase letter for signup"() {
        given:
        String badPasswordStr = "password11"
        String mockNoUppercaseRequestContent = new JSONObject().put("name", "Simple Account")
                .put("email", "simple_account@gmail.com")
                .put("password", badPasswordStr)
                .toString()
        badPasswordStr = "PAssword11"
        String mockTwoUppercaseRequestContent = new JSONObject().put("name", "Simple Account")
                .put("email", "simple_account@gmail.com")
                .put("password", badPasswordStr)
                .toString()

        when:
        MockHttpServletResponse noUppercaseResponse = mockMvc.perform(
                MockMvcRequestBuilders.post("/sign_up")
                        .contentType("application/json")
                        .content(mockNoUppercaseRequestContent)
        )
                .andReturn().getResponse()
        MockHttpServletResponse twoUppercaseResponse = mockMvc.perform(
                MockMvcRequestBuilders.post("/sign_up")
                        .contentType("application/json")
                        .content(mockTwoUppercaseRequestContent)
        )
                .andReturn().getResponse()
        String noUppercaseErrorMessage = getErrorMessageFromContent(new JSONObject(noUppercaseResponse.getContentAsString()))
        String twoUppercaseErrorMessage = getErrorMessageFromContent(new JSONObject(twoUppercaseResponse.getContentAsString()))

        then:
        noUppercaseResponse.getStatus().toInteger() != 200
        noUppercaseErrorMessage == "The password must contain only one uppercase letter"
        twoUppercaseResponse.getStatus().toInteger() != 200
        twoUppercaseErrorMessage == "The password must contain only one uppercase letter"
    }

    def "should look for account when given an Authorization header for login"() {
        given:
        String authHeaderValue = "Bearer jwt_generated_token"

        when:
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/login")
                        .contentType("application/json")
                        .header("Authorization", authHeaderValue)
        )
                .andReturn().getResponse()

        then:
        1 * loginService.loginAccount(_ as String)
    }

    def "should return error message when given no Authorization header for login"() {
        when:
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/login")
                        .contentType("application/json")
        )
                .andReturn().getResponse()
        String errorMessage = getErrorMessageFromContent(new JSONObject(response.getContentAsString()))

        then:
        response.getStatus().toInteger() != 200
        errorMessage == "Required request header 'Authorization' for method parameter type String is not present"
    }

    private String getErrorMessageFromContent(JSONObject content) {
        return content.getJSONArray("error").get(0).get("detail");
    }
}
