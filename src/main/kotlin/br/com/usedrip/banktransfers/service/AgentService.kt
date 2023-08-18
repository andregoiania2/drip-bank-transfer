package br.com.usedrip.banktransfers.service

import br.com.usedrip.banktransfers.dto.TransferForm
import br.com.usedrip.banktransfers.dto.TransfersDTO
import br.com.usedrip.banktransfers.model.Account
import br.com.usedrip.banktransfers.rules.IntraClientDifenteTransfer
import br.com.usedrip.banktransfers.rules.InterTransfer
import br.com.usedrip.banktransfers.rules.IntraTransfer
import org.springframework.stereotype.Service

@Service
class AgentService(
    private val accountService: AccountService,
    private val transferService: TransferService,
) {
    fun execute(transferForm: TransferForm) : TransfersDTO {
        val accountTo : Account = accountService.getAccountTo(transferForm.accountTo)
        val accountFor : Account = accountService.getAccountFor(transferForm.accountFor)
        var interTransfer = InterTransfer(
                                            IntraClientDifenteTransfer(IntraTransfer(null,transferService, accountTo, accountFor, transferForm),
                                                                        transferService, accountTo, accountFor, transferForm)
                                            ,transferService, accountTo, accountFor, transferForm)

        return interTransfer.execute()
    }
}