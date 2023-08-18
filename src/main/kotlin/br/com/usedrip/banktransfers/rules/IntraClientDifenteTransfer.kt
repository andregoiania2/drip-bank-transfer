package br.com.usedrip.banktransfers.rules

import br.com.usedrip.banktransfers.dto.TransferForm
import br.com.usedrip.banktransfers.model.Account
import br.com.usedrip.banktransfers.service.TransferService
import java.math.BigDecimal

class IntraClientDifenteTransfer(proximo: TransferRule,
                                 transferServer: TransferService,
                                 accountTo: Account,
                                 accountFor: Account,
                                 transferForm: TransferForm
) : TransferRule(proximo,
                 transferServer,
                 accountTo,
                 accountFor,
                 transferForm) {
    override fun getComission(transferForm: TransferForm): BigDecimal {
        return BigDecimal.valueOf(1.5)
    }

    override fun validateLimit(transferForm: TransferForm) {
    }
    override fun getProbabilityFall(transferForm: TransferForm): Boolean {
        return true
    }

    override fun nextTransfer(accountTo: Account, accountFor: Account): Boolean {
       return (accountTo.client?.id == accountFor.client?.id)
    }
}