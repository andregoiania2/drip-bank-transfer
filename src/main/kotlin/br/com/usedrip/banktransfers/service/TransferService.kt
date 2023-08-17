package br.com.usedrip.banktransfers.service

import br.com.usedrip.banktransfers.dto.TransferForm
import br.com.usedrip.banktransfers.dto.TransfersDTO
import br.com.usedrip.banktransfers.model.Account
import br.com.usedrip.banktransfers.model.Transfer
import br.com.usedrip.banktransfers.repository.TransferRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransferService(
    private val tranferRepository: TransferRepository
) {

    fun transfer(transferForm: TransferForm, comission : BigDecimal, accountTo : Account, accountFor : Account, success : Boolean): TransfersDTO {
        val transfer = tranferRepository.save( Transfer(null, comission, transferForm.amount, success, accountTo,accountFor) )
        return TransfersDTO(transfer.id, transfer.comission, transfer.success, transfer.accountFor?.code.toString()+" - "+transfer.accountFor?.name, "payment", transfer.amount)
    }

    fun getProbability() : Boolean {
        return (tranferRepository.count() %  2).toInt() == 0
    }
}