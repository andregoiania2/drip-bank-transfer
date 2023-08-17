package br.com.usedrip.banktransfers.controller

import br.com.usedrip.banktransfers.Mocks
import br.com.usedrip.banktransfers.dto.AccountForm
import br.com.usedrip.banktransfers.model.Account
import br.com.usedrip.banktransfers.repository.AccountRepository
import br.com.usedrip.banktransfers.service.BankService
import br.com.usedrip.banktransfers.service.ClientService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var repository: AccountRepository
    @MockBean
    private lateinit var bankService: BankService
    @MockBean
    private lateinit var clientService: ClientService

    private val mocks : Mocks = Mocks()

    @Test
    fun `should return list of accounts`() {
        val lista = mocks.getListAccount()
        `when`(repository.findAll()).thenReturn(lista)

        mockMvc.perform(get("/account"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].code").value(1))
            .andExpect(jsonPath("$[0].name").value("Account 1"))
            .andExpect(jsonPath("$[0].bank").value("1 - Bank 1"))
            .andExpect(jsonPath("$[0].client").value("Client 1 - 333.333.333-33"))
            .andExpect(jsonPath("$[0].transfers[0].id").value(1))
            .andExpect(jsonPath("$[0].transfers[0].comission").value(BigDecimal.valueOf(5.0)))
            .andExpect(jsonPath("$[0].transfers[0].amount").value(BigDecimal.valueOf(4500.0)))
            .andExpect(jsonPath("$[0].transfers[0].success").value(true))
            .andExpect(jsonPath("$[0].transfers[0].operation").value("payment"))
    }

    @Test
    fun `should add a new Account`() {
        val accountForm = AccountForm(101, "Account 1", 1, 1)
        `when`(bankService.findById(1)).thenReturn(mocks.getBank(1))
        `when`(clientService.findById(1)).thenReturn(mocks.getClient(1))


        `when`(repository.save(Mockito.any())).thenReturn(mocks.getAccount(1))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/account")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper().writeValueAsString(accountForm)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(1))
            .andExpect(jsonPath("$.name").value("Account 1"))
            .andExpect(jsonPath("$.transfersSend[0].id").value(1))
            .andExpect(jsonPath("$.transfersSend[0].comission").value(BigDecimal.valueOf(5.0)))
            .andExpect(jsonPath("$.transfersSend[0].amount").value(BigDecimal.valueOf(4500.0)))
            .andExpect(jsonPath("$.transfersSend[0].success").value(true))
    }

    @Test
    fun `should find Account by code`() {
        val opt = mocks.getOptAccount()

        `when`(repository.findById(1)).thenReturn(opt)

        mockMvc.perform(get("/account/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(1))
            .andExpect(jsonPath("$.name").value("Account 1"))
    }

    @Test
    fun `should find Account by code on exception`() {
        val opt : Optional<Account> = Optional.empty()

        `when`(repository.findById(1)).thenReturn(opt)

        mockMvc.perform(get("/account/1"))
            .andExpect(status().is4xxClientError)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.error").value("NOT_FOUND"))
            .andExpect(jsonPath("$.message").value("Account code not found!"))
            .andExpect(jsonPath("$.path").value(""))
    }
}