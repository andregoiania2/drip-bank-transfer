package br.com.usedrip.banktransfers.service

import br.com.usedrip.banktransfers.dto.TransferForm
import br.com.usedrip.banktransfers.dto.TransfersDTO
import br.com.usedrip.banktransfers.model.Account
import br.com.usedrip.banktransfers.rules.ITransferRule
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class IntraTransferService(
                           override var comission: BigDecimal = BigDecimal.ZERO,
                           private val transferService: TransferService) : ITransferRule {
    override fun convert(transferForm: TransferForm, accountTo: Account, accountFor: Account): TransfersDTO {
        return transferService.transfer(transferForm, comission, accountTo, accountFor, true)
    }
}