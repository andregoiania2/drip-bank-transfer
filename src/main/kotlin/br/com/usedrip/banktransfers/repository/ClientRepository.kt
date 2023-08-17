package br.com.usedrip.banktransfers.repository

import br.com.usedrip.banktransfers.model.Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ClientRepository: JpaRepository<Client, Long> {
    fun findByDocumentCPF(documentCpf : String) : Optional<Client>
}