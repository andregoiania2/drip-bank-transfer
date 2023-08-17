package br.com.usedrip.banktransfers.service

import br.com.usedrip.banktransfers.error.CustomExceptionNotFound
import br.com.usedrip.banktransfers.model.Client
import br.com.usedrip.banktransfers.repository.ClientRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientService( private val clientRepository: ClientRepository,
                     private val notFoundMessage: String = "Id Client not found!",
                     private val notFoundMessageCpf: String = "CPF Client not found!") {

    fun add(client: Client) : Client {
        return clientRepository.save(client)
    }

    fun findAll() : List<Client> {
        return clientRepository.findAll()
    }

    fun findById(id: Long) : Client {
        return clientRepository.findById(id)
            .orElseThrow{ CustomExceptionNotFound(notFoundMessage) }
    }

    fun findByCpf(documentCPF: String): Client {
        return clientRepository.findByDocumentCPF(documentCPF)
            .orElseThrow{ CustomExceptionNotFound(notFoundMessageCpf) }
    }

    fun findByCpfOptional(documentCPF: String): Optional<Client> {
        return clientRepository.findByDocumentCPF(documentCPF)
    }
}