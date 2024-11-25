package com.studyKotlin.credit.application.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.studyKotlin.credit.application.system.domain.model.Address
import com.studyKotlin.credit.application.system.domain.model.Credit
import com.studyKotlin.credit.application.system.domain.repository.CustomerRepository
import com.studyKotlin.credit.application.system.dto.CustomerDTO
import com.studyKotlin.credit.application.system.dto.CustomerView
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.util.*
@SpringBootTest // ESSA ANOTATION SOBE O CONTEXTO DO SPRING
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerControllerTest {
    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "http://localhost:8080/api/customers"
    }

    @BeforeEach
    fun setUp() = customerRepository.deleteAll()

    @AfterEach
    fun tearDowm() = customerRepository.deleteAll()


    @Test
    fun `Should created by Customer and return status`() {
        //given
        val customerDTO: CustomerDTO = buildCustomerDTO()
        val valueAsString: String = objectMapper.writeValueAsString(customerDTO)
        //when
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("da silva"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("132.616.740-59"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("testDev@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua Dos Testes"))
        //then
    }

    @Test
    fun `Should created by Customer  whit cpf same and return sattusCode 409`() {
        //given
        customerRepository.save(buildCustomerDTO().toEntity())
        val customerDTO: CustomerDTO = buildCustomerDTO()
        val valueAsString: String = objectMapper.writeValueAsString(customerDTO)
        //then
        //when
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isConflict)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Conflit! Consult the Documetation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("CONFLICT"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.exception").value("class org.springframework.dao.DataIntegrityViolationException"))

    }
    @Test
    fun `Should created by Customer  whit cpf isempyt and return sattusCode 400`() {
        //given

        val customerDTO: CustomerDTO = buildCustomerDTO(cpf = "")
        val valueAsString: String = objectMapper.writeValueAsString(customerDTO)
        //then
        //when
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the Documetation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.exception").value("null"))
            .andDo(MockMvcResultHandlers.print())
    }

    fun buildCustomerDTO(
        cpf: String = "132.616.740-59",
        email: String = "testDev@gmail.com",
        firstName: String = "test",
        lastName: String = "da silva",
        inCome: BigDecimal = BigDecimal.valueOf(1500.0),
        password: String = "13325",
        street: String = "Rua Dos Testes",
        zipCode: String = "12345689",
        id: Long = 1
    ) = CustomerDTO(
        cpf = cpf,
        email = email,
        firstName = firstName,
        lastName = lastName,
        inCome = inCome,
        password = password,
        street = street,
        zipCode = zipCode,
    )
    }




