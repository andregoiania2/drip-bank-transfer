package br.com.usedrip.banktransfers.service

import br.com.usedrip.banktransfers.dto.AccountDTO
import br.com.usedrip.banktransfers.dto.AccountForm
import br.com.usedrip.banktransfers.dto.TransfersDTO
import br.com.usedrip.banktransfers.error.CustomException
import br.com.usedrip.banktransfers.error.CustomExceptionNotFound
import br.com.usedrip.banktransfers.model.Account
import br.com.usedrip.banktransfers.model.Bank
import br.com.usedrip.banktransfers.model.Client
import br.com.usedrip.banktransfers.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService(private val repository: AccountRepository,
    private val bankService: BankService,
    private val clientService: ClientService,
    private val notFoundMessageTo: String = "Destination account code not found!",
    private val notFoundMessageFor: String = "Source account code not found!",
    private val notFoundMessage: String = "Account code not found!"

) {

    fun add(accountDTO: AccountForm) : Account {
        var bank: Bank = bankService.findById(accountDTO.bank)
        var client : Client = clientService.findById(accountDTO.client)

        val account: Account =  Account(accountDTO.code,
                                        accountDTO.accountName,
                                        bank,
                                        client
                                    )

        return repository.save(account)
    }

    fun add(account: Account): AccountDTO {
        return convertToDTO(repository.save(account))
    }

    fun list(): List<AccountDTO> {
        var listAccountDTO = ArrayList<AccountDTO>()
        var accounts : List<Account> = repository.findAll()

        accounts.forEach{c ->
            listAccountDTO.add(convertToDTO(c))};


        return listAccountDTO
    }

    fun findById(code: Int) : AccountDTO? {
        val account =  repository.findById(code).orElseThrow{CustomExceptionNotFound(notFoundMessage) }
        return convertToDTO(account)
    }

    fun getAccountTo(code: Int): Account {
        return repository.findById(code)
            .orElseThrow {
                CustomException(notFoundMessageTo)
            }
    }

    fun getAccountFor(code : Int) : Account {
        return repository.findById(code)
            .orElseThrow {
                CustomException(notFoundMessageFor)
            }
    }


    private fun convertToDTO(account: Account): AccountDTO {

        var listaTransfDTO = ArrayList<TransfersDTO>()


        account.transfersSend.forEach { c ->
                listaTransfDTO.add(
                    TransfersDTO(
                        c.id,
                        c.comission,
                        c.success,
                        c.accountFor?.code.toString() + " - " + c.accountFor?.name,
                        "payment",
                        c.amount
                    )
                )
            }
            account.transfersReceived.forEach { c ->
                listaTransfDTO.add(
                    TransfersDTO(
                        c.id,
                        c.comission,
                        c.success,
                        c.accountFor?.code.toString() + " - " + c.accountFor?.name,
                        "receive",
                        c.amount
                    )
                )
            }
        listaTransfDTO.sortBy { it.id }

        return AccountDTO(
                account.code,
                account.name,
                account.bank?.code.toString() + " - " + account.bank?.name,
                account.client?.name + " - " + account.client?.documentCPF,
                listaTransfDTO
            )


    }

}