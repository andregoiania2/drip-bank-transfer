package br.com.usedrip.banktransfers.dto

import java.math.BigDecimal

data class TransfersDTO(
    val id: Long?,
    val comission: BigDecimal,
    val success: Boolean,
    val account: String,
    val operation: String,
    val amount: BigDecimal
)
