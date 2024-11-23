package com.studyKotlin.credit.application.system.service

import com.studyKotlin.credit.application.system.domain.enumeration.Status
import com.studyKotlin.credit.application.system.domain.model.Address
import com.studyKotlin.credit.application.system.domain.model.Credit
import com.studyKotlin.credit.application.system.domain.model.Customer
import com.studyKotlin.credit.application.system.domain.repository.CreditRepository
import com.studyKotlin.credit.application.system.excepion.BusinessException
import com.studyKotlin.credit.application.system.service.impl.CreditService
import com.studyKotlin.credit.application.system.service.impl.CustomerService
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.stereotype.Service
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CretitServicetest {
    @MockK
    lateinit var creditRepository: CreditRepository

    @MockK
    lateinit var customerService: CustomerService

    @InjectMockKs
    lateinit var creditService: CreditService

    // TODO CRIAR TESTES
    @Test
    fun `Saved Credit`() {
        val fakeCredit: Credit = buildCredit()
        val fakeCustomer : Customer = buildCustomer()
        val fakeId: Long = Random().nextLong()

        every { customerService.findById(fakeCredit.customer!!.id!!) } returns fakeCustomer
        every { creditRepository.save(any()) } returns fakeCredit

        val actuCrdit : Credit = creditService.save(fakeCredit)

        Assertions.assertThat(actuCrdit).isNotNull
        verify(exactly = 1) { customerService.findById(any()) }
    }

    @Test
    fun `Saved Credit trhow bussines exception`() {
        val fakeCredit: Credit = buildCredit()
        every { creditRepository.save(fakeCredit) } returns fakeCredit
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { creditService.save(fakeCredit) }
            .withMessage("The first installment must be paid within 90 days of applying for the loan.")
    }

    @Test
    fun `Find Credits by CustomerId`() {
        val fakeCredit: Credit = buildCredit()
        val fakeId: Long = Random().nextLong()
        every { creditRepository.finsByAllCustomerId(fakeId) } returns mutableListOf(fakeCredit)
        val actualCredits: List<Credit> = creditService.findByCustomer(fakeId)
        Assertions.assertThat(actualCredits).isNotEmpty()
        Assertions.assertThat(actualCredits).isNotNull()
        Assertions.assertThat(actualCredits.get(0).customer?.cpf).isSameAs("132.616.740-59")
    }

    // TODO AJUSTAR O CODIGO UUII QUE ESTA VINDO DIVERGENTE
    @Test
    fun `Find CredCode and CustomerId`() {
        val fakeCredit: Credit = buildCredit()
        val fakeId: Long = Random().nextLong()
        var fakeCredCode: UUID = UUID.randomUUID()

        every { creditRepository.findByCreditCode(fakeCredCode) } returns fakeCredit

        val actualCredit: Credit = creditService.findByCreditCode(fakeCredCode, 1)
        Assertions.assertThat(actualCredit).isNotNull()
        Assertions.assertThat(actualCredit.customer!!.id).isSameAs(1L)

    }

    @Test
    fun `Delete Credit by Id`() {
        val fakeId: Long = Random().nextLong()

        every { creditRepository.deleteById(fakeId) } just Runs;
        creditService.delete(fakeId)

        verify(exactly = 1) { creditRepository.deleteById(fakeId) }
        verify(exactly = 1) { creditService.delete(fakeId) }

    }


    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(1200.0),
        customer: Customer = buildCustomer(),
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