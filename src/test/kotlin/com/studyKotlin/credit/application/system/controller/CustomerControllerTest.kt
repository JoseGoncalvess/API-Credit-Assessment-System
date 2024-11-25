package com.studyKotlin.credit.application.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.studyKotlin.credit.application.system.domain.model.Address
import com.studyKotlin.credit.application.system.domain.model.Credit
import com.studyKotlin.credit.application.system.domain.model.Customer
import com.studyKotlin.credit.application.system.domain.repository.CustomerRepository
import com.studyKotlin.credit.application.system.dto.CustomerDTO
import com.studyKotlin.credit.application.system.dto.CustomerUpdateDto
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
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class org.springframework.dao.DataIntegrityViolationException")
            )

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


    @Test
    fun `Should n by Customer for ID  and return sattusCode 200`() {
        //given
        val customer: Customer = customerRepository.save(buildCustomerDTO().toEntity())
        //then
        //when
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/${customer.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("da silva"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("132.616.740-59"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("testDev@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua Dos Testes"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Should not by Customer for ID  and return sattusCode 400`() {
        //given
        val fakeCustomerId: Long = 1L
        //then
        //when
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/$fakeCustomerId")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the Documetation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class com.studyKotlin.credit.application.system.excepion.BusinessException")
            )
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Delete by Customer for ID  and return sattusCode 204`() {
        //given
        val customer: Customer = customerRepository.save(buildCustomerDTO().toEntity())
        //then
        //when
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$URL/${customer.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not delete by id and return 400 statusCode`() {
        //given
        val nvalidId: Long = Random().nextLong()
        //then
        //when
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$URL/${nvalidId}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun ` should Update  Customer and return sattusCode 200`() {
        //given
        val customer: Customer = customerRepository.save(buildCustomerDTO().toEntity())
        val customerUpdateDto : CustomerUpdateDto = buildCustomerUpdateDTO()
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDto)
        //then
        //when
        mockMvc.perform(
            MockMvcRequestBuilders.patch("$URL?customerid=${customer.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Testando"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Code"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("92959855"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua Dos Testes Melhores"))
            .andDo(MockMvcResultHandlers.print())
    }
    @Test
    fun ` should Update  Customer not exist and return sattusCode 400`() {
        //given
        val invalidId: Long = Random().nextLong()
        val customerUpdateDto : CustomerUpdateDto = buildCustomerUpdateDTO()
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDto)
        //then
        //when
        mockMvc.perform(
            MockMvcRequestBuilders.patch("$URL?customerid=$invalidId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the Documetation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class com.studyKotlin.credit.application.system.excepion.BusinessException")
            )
            .andDo(MockMvcResultHandlers.print())

    }




    fun buildCustomerUpdateDTO(
        firstName: String = "Testando",
        lastName: String = "Code",
        inCome: BigDecimal = BigDecimal.valueOf(1520.0),
        street: String = "Rua Dos Testes Melhores",
        zipCode: String = "92959855",

    ) = CustomerUpdateDto(
        zipCode = zipCode,
        street = street,
        inCome = inCome,
        lastName = lastName,
        firstName = firstName,

    )



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




