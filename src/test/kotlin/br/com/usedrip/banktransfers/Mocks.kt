package br.com.usedrip.banktransfers

import br.com.usedrip.banktransfers.model.Account
import br.com.usedrip.banktransfers.model.Bank
import br.com.usedrip.banktransfers.model.Client
import br.com.usedrip.banktransfers.model.Transfer
import java.math.BigDecimal
import java.util.*

class Mocks {
    fun getListBank() : List<Bank> {
        return listOf(getBank(1), getBank(2))
    }

    fun getBank(code: Int) : Bank {
        return Bank(code, "Bank "+code.toString())
    }

    fun getOptBank() : Optional<Bank> {
        return Optional.of(Bank(1, "Bank 1"))
    }

    fun getOptAccount() : Optional<Account> {
        return Optional.of(getAccount2(1))
    }

    fun getOptAccount2() : Optional<Account> {
        return Optional.of(getAccount2Bank(2))
    }

    fun getOptClient() : Optional<Client> {
        return Optional.of(getClient(1))
    }

    fun getListClient() : List<Client> {
        return listOf(Client(id = 1, name = "Client 1", documentCPF = "111.111.111-11"),
            Client(id = 2, name = "Client 2", documentCPF = "222.222.222-22"))
    }

    fun getClient(id : Long ) : Client {
        return Client(id = 1, name = "Client "+id.toString(), documentCPF = "333.333.333-33")
    }
/*
    fun getListAccountDTO() : List<AccountDTO> {
        var listaAccount = ArrayList<AccountDTO>()
        listaAccount.add(AccountDTO(1, "Conta 1","237 - Bradesco", "Jose", getListTransfDTO() ))
        listaAccount.add(AccountDTO(1, "Conta 1","237 - Bradesco", "Jose", getListTransfDTO() ))
        return  listaAccount
    }

    fun getListTransfDTO() : List<TransfersDTO> {
        var listaTransfDTO = ArrayList<TransfersDTO>()
        listaTransfDTO.add(
            TransfersDTO(1, BigDecimal.valueOf(5.0),true,"1 - Teste","payment",
                BigDecimal.valueOf(4005.0))
        )
        listaTransfDTO.add(
            TransfersDTO(1, BigDecimal.valueOf(5.0),false,"1 - Teste","payment",
                BigDecimal.valueOf(305.0))
        )
        return listaTransfDTO;
    }
*/
    fun getListAccount() : List<Account> {
        var listaAccount = ArrayList<Account>()
            listaAccount.add(getAccount(1))
           // listaAccount.add(getAccount(2))

        return listaAccount
    }

    fun getAccount(code : Int) : Account {
        return Account(code, "Account "+code.toString(), getBank(1), getClient(1), getListTransf(), getListTransf())
    }
    fun getAccount2(code : Int) : Account {
        return Account(code, "Account "+code.toString(), getBank(1), getClient(1))
    }
    fun getAccount2Bank(code : Int) : Account {
        return Account(code, "Account "+code.toString(), getBank(2), getClient(3))
    }

    fun getListTransf() : List<Transfer> {
        var listaTransfer = ArrayList<Transfer>()
        listaTransfer.add(getTransf(1))
        listaTransfer.add(getTransf(2))
        return listaTransfer
    }

    fun getTransf(id : Long) : Transfer {
        return Transfer(id, BigDecimal.valueOf(5.0), BigDecimal.valueOf(4500.00), id == 1L, getAccount2(1), getAccount2(2))
    }

    fun getTransfIntra(id : Long) : Transfer {
        return Transfer(id, BigDecimal.ZERO, BigDecimal.valueOf(4500.00), id == 1L, getAccount2(1), getAccount2(2))
    }

}