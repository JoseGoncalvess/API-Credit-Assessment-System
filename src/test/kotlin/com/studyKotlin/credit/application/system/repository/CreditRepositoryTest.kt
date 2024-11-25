package com.studyKotlin.credit.application.system.repository

import com.studyKotlin.credit.application.system.domain.enumeration.Status
import com.studyKotlin.credit.application.system.domain.model.Address
import com.studyKotlin.credit.application.system.domain.model.Credit
import com.studyKotlin.credit.application.system.domain.model.Customer
import com.studyKotlin.credit.application.system.domain.repository.CreditRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {

    @Autowired
    lateinit var creditRepository: CreditRepository

    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit

    @BeforeEach
    fun seTup() {
        customer = testEntityManager.persist(buildCustomer())
        credit1 = testEntityManager.persist(buildCredit(customer = customer))
    }

    @Test
    fun `Should find credit by credCod`() {
        val creditCode1: UUID = UUID.fromString("eb0157ec-72fd-462f-aa3e-de60ef6c42a1")
        credit1.creditCode = creditCode1
        val fakeCredit1: Credit = creditRepository.findByCreditCode(creditCode1)!!

        Assertions.assertThat(fakeCredit1.creditCode).isNotNull()
        Assertions.assertThat(fakeCredit1.creditCode).isSameAs(creditCode1)

    }

    @Test
    fun `Should findAllcredit by CustomerId`() {
        //given
        val fackeid: Long = 1
         //wen
        val actualListCredit: List<Credit> = creditRepository.finsByAllCustomerId(customerId = fackeid)
        //then
        Assertions.assertThat(actualListCredit).isNotNull
        Assertions.assertThat(actualListCredit).isNotEmpty

    }

    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(1200.0),
        customer: Customer,
        dayFirstInstallment: LocalDate = LocalDate.now(),
        numberOfInstallment: Int = 5,
        creditCode: UUID = UUID.randomUUID(),
        status: Status = Status.IN_PROGRESS


    ) = Credit(
        creditValue = creditValue,
        customer = customer,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallment = numberOfInstallment,
        creditCode = creditCode,
        status = status

    )

    private fun buildCustomer(
        cpf: String = "132.616.740-59",
        email: String = "testDev@gmail.com",
        firstName: String = "test",
        lastName: String = "da silva",
        inCome: BigDecimal = BigDecimal.valueOf(1500.0),
        password: String = "13325",
        street: String = "Rua Dos Testes",
        zipCode: String = "12345689"
    ) = Customer(
        id = 1,
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


}