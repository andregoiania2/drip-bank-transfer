package br.com.usedrip.banktransfers.service

import br.com.usedrip.banktransfers.dto.AccountDTO
import br.com.usedrip.banktransfers.dto.RegisterForm
import br.com.usedrip.banktransfers.model.Account
import br.com.usedrip.banktransfers.model.Bank
import br.com.usedrip.banktransfers.model.Client
import org.springframework.stereotype.Service

@Service
class RegisterService (
    private val accountService: AccountService,
    private val bankService: BankService,
    private val clientService: ClientService
){
    fun add(dto: RegisterForm) : AccountDTO {
       val bank = getBank(dto.bankCode, dto.bankNome)
       val client = getClient(dto.clientCPF, dto.clientNome)
       return accountService.add(Account(
           dto.accountCode, dto.accountName, bank, client
       ))
    }

    fun getClient(cpf : String, name : String) : Client {
        val opt = clientService.findByCpfOptional(cpf)
        if (opt.isPresent) {
            return opt.get()
        } else {
            return clientService.add( Client(null, name, cpf, ArrayList()) )
        }
    }

    fun getBank(code : Int, name : String) : Bank {
        var opt = bankService.findByIdOptional(code);
        if (opt.isPresent) {
            return opt.get()
        } else {
            return bankService.add(Bank(code, name, ArrayList()))
        }
    }


}