package br.com.usedrip.banktransfers.service

import br.com.usedrip.banktransfers.dto.TransferForm
import br.com.usedrip.banktransfers.dto.TransfersDTO
import br.com.usedrip.banktransfers.error.CustomException
import br.com.usedrip.banktransfers.model.Account
import br.com.usedrip.banktransfers.rules.ITransferRule
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class InterTransferService(
                           override var comission: BigDecimal = BigDecimal.valueOf(5.0),
                           private val transferService: TransferService,
                           private val limiteTransaction : BigDecimal = BigDecimal.valueOf(5000.0),
                           private val msg : String = "Interbank transfers, between accounts at different banks have a limit of R\$ 5000.00 per transfer") : ITransferRule {


    override fun convert(transferForm: TransferForm, accountTo : Account, accountFor : Account): TransfersDTO {
           if (transferForm.amount > limiteTransaction) {
                throw CustomException(msg)
           }

            return transferService.transfer(transferForm, comission, accountTo, accountFor, transferService.getProbability())
    }
}
