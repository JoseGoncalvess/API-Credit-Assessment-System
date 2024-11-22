package com.studyKotlin.credit.application.system.service

import com.studyKotlin.credit.application.system.domain.model.Address
import com.studyKotlin.credit.application.system.domain.model.Customer
import com.studyKotlin.credit.application.system.domain.repository.CustomerRepository
import com.studyKotlin.credit.application.system.excepion.BusinessException
import com.studyKotlin.credit.application.system.service.impl.CustomerService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.util.*


@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
    @MockK lateinit var customerRepository: CustomerRepository
    @InjectMockKs lateinit var customerService: CustomerService

    @Test
    fun `created customer`(){
        val  fakecustomer : Customer = buildCustomer()
        every { customerRepository.save(any()) }returns fakecustomer

        val actual : Customer = customerService.save(fakecustomer)

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakecustomer)
        verify(exactly = 1) { customerRepository.save(fakecustomer) }
    }

    @Test
    fun `find by customer for id`(){
        val  fakecustomer : Customer = buildCustomer()
        val fakeId : Long = Random().nextLong()
        every { customerRepository.findById(anyLongVararg().get(0)) }returns Optional.of(fakecustomer)
        val actual : Customer = customerService.findById(fakeId)

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakecustomer)
        verify(exactly = 1) { customerRepository.findById(fakeId) }
    }
    @Test
    fun `find by customer return throw businesexception`(){
        val fakeId : Long = Random().nextLong()
        every { customerRepository.findById(fakeId ) }returns Optional.empty()
        val actual : Customer = customerService.findById(fakeId)

        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { customerService.findById(fakeId) }
            .withMessage("Id $fakeId not foud")

        verify(exactly = 1) { customerRepository.findById(fakeId) }
    }

    @Test
    fun `delete by customer `(){
        val  fakecustomer : Customer = buildCustomer()
        val fakeId : Long = Random().nextLong()
        every { customerRepository.findById(fakeId) }returns Optional.of(fakecustomer)
        every { customerRepository.delete(fakecustomer) } just runs;
        customerService.delete(fakeId);


        verify(exactly = 1) { customerRepository.findById(fakeId) }
        verify(exactly = 1) { customerRepository.delete(fakecustomer)}
    }
}

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