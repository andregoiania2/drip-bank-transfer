package br.com.usedrip.banktransfers.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RegisterForm(
    @field:NotNull
    val accountCode: Int?,
    @field:NotEmpty(message = "Account name cannot be blank")
    @field:Size(min = 5, max = 30, message = "Account name must be between 5 and 30 characters")
    val accountName: String,
    @field:NotNull
    val bankCode: Int,
   // @field:NotEmpty(message = "Bank name cannot be blank")
   // @field:Size(min = 5, max = 30, message = "Bank name must be between 5 and 30 characters")
    val bankNome : String,
    @field:NotEmpty(message = "Name cannot be blank")
    @field:Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters")
    val clientNome : String,
    @field:NotEmpty(message = "CPF cannot be blank")
    val clientCPF : String

)