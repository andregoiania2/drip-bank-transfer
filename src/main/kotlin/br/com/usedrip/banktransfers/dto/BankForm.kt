package br.com.usedrip.banktransfers.dto

import br.com.usedrip.banktransfers.model.Bank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class BankForm(
    @field:NotNull
    val code: Int,
    @field:NotEmpty(message = "Bank name cannot be blank")
    @field:Size(min = 5, max = 30, message = "Bank name must be between 5 and 30 characters")
    var name: String,
) {
    fun convert(): Bank {
        return Bank(this.code, this.name, ArrayList())
    }
}