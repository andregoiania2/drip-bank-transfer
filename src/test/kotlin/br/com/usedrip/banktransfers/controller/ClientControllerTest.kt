package br.com.usedrip.banktransfers.controller

import br.com.usedrip.banktransfers.Mocks
import br.com.usedrip.banktransfers.dto.ClientForm
import br.com.usedrip.banktransfers.model.Client
import br.com.usedrip.banktransfers.repository.ClientRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var clientRepository: ClientRepository

    private val mocks : Mocks = Mocks()

    @Test
    fun `should return list of Client`() {
        `when`(clientRepository.findAll()).thenReturn(mocks.getListClient())

        val andExpect = mockMvc.perform(get("/client"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Client 1"))
            .andExpect(jsonPath("$[0].documentCPF").value("111.111.111-11"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Client 2"))
            .andExpect(jsonPath("$[1].documentCPF").value("222.222.222-22"))
    }

    @Test
    fun `should add a new Client`() {
        val clientForm = ClientForm(name = "New Client", documentCPF = "333.333.333-33")
        val expectedClient = mocks.getClient(1)

        `when`(clientRepository.save(any())).thenReturn(expectedClient)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/client")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper().writeValueAsString(clientForm)))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Client 1"))
            .andExpect(jsonPath("$.documentCPF").value("333.333.333-33"))
    }

    @Test
    fun `should find client by id`() {
        val client = mocks.getClient(1)
        val opt : Optional<Client> = Optional.of(client)

        `when`(clientRepository.findById(1)).thenReturn(opt)

         mockMvc.perform(get("/client/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
             .andExpect(jsonPath("$.id").value(1))
             .andExpect(jsonPath("$.name").value("Client 1"))
             .andExpect(jsonPath("$.documentCPF").value("333.333.333-33"))

    }

    @Test
    fun `should find client by id on exception`() {
        val opt : Optional<Client> = Optional.empty()

        `when`(clientRepository.findById(1)).thenReturn(opt)

        mockMvc.perform(get("/client/1"))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.error").value("NOT_FOUND"))
            .andExpect(jsonPath("$.message").value("Id Client not found!"))
            .andExpect(jsonPath("$.path").value(""))

    }


    @Test
    fun `should find client by CPF`() {
        val client = mocks.getClient(1)
        val opt : Optional<Client> = Optional.of(client)

        `when`(clientRepository.findByDocumentCPF("333.333.333-33")).thenReturn(opt)

        mockMvc.perform(get("/client/cpf/333.333.333-33"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Client 1"))
            .andExpect(jsonPath("$.documentCPF").value("333.333.333-33"))

    }

    @Test
    fun `should find client by CPF exception`() {
        val opt : Optional<Client> = Optional.empty()

        `when`(clientRepository.findByDocumentCPF("333.333.333-33")).thenReturn(opt)

        mockMvc.perform(get("/client/cpf/333.333.333-33"))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.error").value("NOT_FOUND"))
            .andExpect(jsonPath("$.message").value("CPF Client not found!"))
            .andExpect(jsonPath("$.path").value(""))
    }
}