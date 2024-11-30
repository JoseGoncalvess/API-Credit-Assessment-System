package com.studyKotlin.credit.application.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.studyKotlin.credit.application.system.domain.model.Address
import com.studyKotlin.credit.application.system.domain.model.Credit
import com.studyKotlin.credit.application.system.domain.model.Customer
import com.studyKotlin.credit.application.system.domain.repository.CreditRepository
import com.studyKotlin.credit.application.system.domain.repository.CustomerRepository
import com.studyKotlin.credit.application.system.dto.CreditDTO
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
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
import java.time.LocalDate
import java.util.UUID


@SpringBootTest // ESSA ANOTATION SOBE O CONTEXTO DO SPRING
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CreditControllerTest {
    @Autowired
    private lateinit var creditRepository: CreditRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "http://localhost:8080/api/credits"
    }

    @BeforeEach
    fun setUp() = creditRepository.deleteAll()

    @AfterEach
    fun tearDowm() = creditRepository.deleteAll()

    @Test
    fun `Should created by Credit and return status ok`() {
        val fakeCustomer : Customer = buildCustomer()
        val fakeCreditDTO : CreditDTO = buildCreditDTO()
        val value = objectMapper.writeValueAsString(fakeCreditDTO)

        customerRepository.save(fakeCustomer)
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(value)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andDo(MockMvcResultHandlers.print())


    }

    @Test
    fun `Should created by Credit and return status erro`() {
        val fakeCustomer : Customer = buildCustomer()
        val fakeCreditDTO : CreditDTO = buildCreditDTO()
        val value = objectMapper.writeValueAsString(fakeCreditDTO.dayFirstInstallment.plusDays(180))

        customerRepository.save(fakeCustomer)
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(value)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the Documetation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class org.springframework.http.converter.HttpMessageNotReadableException")
            )
            .andDo(MockMvcResultHandlers.print())


    }

    @Test
    fun `Find cretid by ID and status ok`() {
        val fakeCustomer : Customer = buildCustomer()
        val fakeCreditDTO : CreditDTO = buildCreditDTO()
        val value : Credit = fakeCreditDTO.toEntity()
        value.customer = fakeCustomer

        customerRepository.save(fakeCustomer)
        creditRepository.save(value)
        mockMvc.perform(
            MockMvcRequestBuilders.get("${URL}/1").contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("[0].creditCode").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("[0].creditValue").value(1200.00))
            .andExpect(MockMvcResultMatchers.jsonPath("[0].numberOfInstallment").value("4"))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andDo(MockMvcResultHandlers.print())


    }

    @Test
    fun `Find cretid by ID and content is empyt`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("${URL}/1").contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0))
            .andDo(MockMvcResultHandlers.print())


    }

    @Test
    fun `Delet credit by CredCod and Id and status code ok`() {
        val fakeCustomer : Customer = buildCustomer()
        val fakeCreditDTO : CreditDTO = buildCreditDTO()
        val value : Credit = fakeCreditDTO.toEntity()
        value.customer = fakeCustomer

        customerRepository.save(fakeCustomer)
        creditRepository.save(value)
        val credCod : UUID = creditRepository.findById(1).get().creditCode;
        mockMvc.perform(
            MockMvcRequestBuilders.get("${URL}?customerid=1&credCod=$credCod").contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditCode").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.creditValue").value(1200.00))
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfInstallment").value("4"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emailCustomer").value("testDev@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.incodeCustomer").value(1500.00))
            .andExpect(MockMvcResultMatchers.jsonPath("$.stattus").value("IN_PROGRESS"))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andDo(MockMvcResultHandlers.print())


    }

    @Test
    fun `Delet credit by Id and status code ok`() {
        val fakeCustomer : Customer = buildCustomer()
        val fakeCreditDTO : CreditDTO = buildCreditDTO()
        val value = objectMapper.writeValueAsString(fakeCreditDTO)

        customerRepository.save(fakeCustomer)
        mockMvc.perform(
            MockMvcRequestBuilders.delete("${URL}/1").contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("Customer with id ->  1 Deleted"))
            .andDo(MockMvcResultHandlers.print())


    }

    private fun buildCreditDTO(
        creditValue: BigDecimal = BigDecimal.valueOf(1200.0),
        dayFirstInstallment: LocalDate = LocalDate.now().plusDays(12),
        customerId: Long = 1,
        numberOfInstallment: Int = 4,
    ) = CreditDTO(
        customerId = 1,
        numberOfInstallment = numberOfInstallment,
        dayFirstInstallment = dayFirstInstallment,
        creditValue = creditValue,
    )
    private  fun  buildCustomer(
        cpf : String = "132.616.740-59",
        email: String = "testDev@gmail.com",
        firstName: String = "test",
        lastName: String = "da silva",
        inCome: BigDecimal = BigDecimal.valueOf(1500.0),
        password : String = "13325",
        street : String  = "Rua Dos Testes",
        zipCode : String = "12345689"
    ) = Customer(
        cpf = cpf,
        email = email,
        firstName = firstName,
        lastName = lastName,
        inCome = inCome,
        password = password,
        address = Address(
            street = street,
            zipCode = zipCode
        )

    )

    private  fun loadingCredCod() : UUID{
      return  creditRepository.findById(1).get().creditCode;
    }
}