package com.studyKotlin.credit.application.system.excepion

import org.aspectj.apache.bcel.classfile.Field
import org.springframework.beans.MethodInvocationException
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handlerValidexecption(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionsDetils> {
        val erros: MutableMap<String, String?> = hashMapOf()
        ex.bindingResult.allErrors.stream().map { erro: ObjectError ->
            val fieldName: String = (erro as FieldError).field
            val erroMenssage: String? = erro.defaultMessage
            erros[fieldName] = erroMenssage
        }

        return ResponseEntity(
            ExceptionsDetils(
                title = "Bad Request! Consult the Documetation",
                details = erros,
                exception = ex.cause.toString(),
                timestamp = LocalDateTime.now(),
                status = HttpStatus.BAD_REQUEST
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(DataAccessException::class)
    fun handlerDataAccessException(ex: DataAccessException): ResponseEntity<ExceptionsDetils> {
        return ResponseEntity(
            ExceptionsDetils(
                title = "Bad Conflit! Consult the Documetation",
                details = mutableMapOf(ex.cause.toString() to ex.message),
                exception = ex.javaClass.toString(),
                timestamp = LocalDateTime.now(),
                status = HttpStatus.CONFLICT

            ), HttpStatus.CONFLICT
        )
    }

    @ExceptionHandler(RuntimeException::class)
    fun handlerRuntimeException(ex: RuntimeException): ResponseEntity<ExceptionsDetils> {
        return ResponseEntity(
            ExceptionsDetils(
                title = "Bad Request! Consult the Documetation",
                details = mutableMapOf(ex.cause.toString() to ex.message),
                exception = ex.javaClass.toString(),
                timestamp = LocalDateTime.now(),
                status = HttpStatus.BAD_REQUEST
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(BusinessException::class)
    fun handlerBusinessException(ex: BusinessException): ResponseEntity<ExceptionsDetils> {
        return ResponseEntity(
            ExceptionsDetils(
                title = "Bad Request! Consult the Documetation",
                details = mutableMapOf(ex.cause.toString() to ex.message),
                exception = ex.javaClass.toString(),
                timestamp = LocalDateTime.now(),
                status = HttpStatus.BAD_REQUEST
            ), HttpStatus.BAD_REQUEST
        )
    }
}