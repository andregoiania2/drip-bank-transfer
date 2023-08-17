package br.com.usedrip.banktransfers.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AccountForm(
    @field:NotNull
    val code: Int?,
    @field:NotEmpty(message = "Account name cannot be blank")
    @field:Size(min = 5, max = 30, message = "Account name must be between 5 and 30 characters")
    val accountName: String,
    @field:NotNull
    val bank: Int,
    @field:NotNull
    val client : Long
)