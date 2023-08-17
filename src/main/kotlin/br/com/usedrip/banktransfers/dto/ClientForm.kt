package br.com.usedrip.banktransfers.dto

import br.com.usedrip.banktransfers.model.Client
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class ClientForm(
    @field:NotEmpty(message = "Name cannot be blank")
    @field:Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters")
    val name: String,
    @field:NotEmpty(message = "CPF cannot be blank")
    val documentCPF: String,
) {
    fun convert() : Client {
        return Client(
            null,
            this.name,
            this.documentCPF,
            ArrayList()
        )
    }
}