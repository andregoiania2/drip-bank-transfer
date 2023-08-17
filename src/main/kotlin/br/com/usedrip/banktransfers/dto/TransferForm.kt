package br.com.usedrip.banktransfers.dto

import java.math.BigDecimal

data class TransferForm(
    val amount: BigDecimal,
    val accountTo: Int,
    val accountFor: Int
)
