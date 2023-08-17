package br.com.usedrip.banktransfers.rules

import br.com.usedrip.banktransfers.dto.TransferForm
import br.com.usedrip.banktransfers.dto.TransfersDTO
import br.com.usedrip.banktransfers.model.Account
import java.math.BigDecimal

interface ITransferRule {
    var comission : BigDecimal

    fun convert(transferForm: TransferForm, accountTo : Account, accountFor : Account): TransfersDTO
}