package br.com.usedrip.banktransfers.controller

import br.com.usedrip.banktransfers.dto.ClientForm
import br.com.usedrip.banktransfers.model.Client
import br.com.usedrip.banktransfers.service.ClientService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("client")
class ClientController(private val clientService: ClientService) {

    @GetMapping
    fun list() : List<Client> {
        return clientService.findAll()
    }

    @PostMapping
    fun add(@RequestBody @Valid form: ClientForm) : Client {
        val client : Client = form.convert()
        return clientService.add(client)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id : Long) : Client {
        return clientService.findById(id)
    }

    @GetMapping("/cpf/{documentCPF}")
    fun findByCpf(@PathVariable documentCPF: String) : Client {
       return clientService.findByCpf(documentCPF)
    }

}