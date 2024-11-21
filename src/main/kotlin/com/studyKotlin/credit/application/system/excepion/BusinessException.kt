package com.studyKotlin.credit.application.system.excepion

data class BusinessException(override  val message : String?) : RuntimeException(message) {

}
