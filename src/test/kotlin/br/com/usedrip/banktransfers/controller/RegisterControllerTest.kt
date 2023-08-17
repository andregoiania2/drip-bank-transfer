package br.com.usedrip.banktransfers.controller

import br.com.usedrip.banktransfers.Mocks
import br.com.usedrip.banktransfers.dto.RegisterForm
import br.com.usedrip.banktransfers.repository.AccountRepository
import br.com.usedrip.banktransfers.repository.BankRepository
import br.com.usedrip.banktransfers.repository.ClientRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var repository: AccountRepository
    @MockBean
    private lateinit var bankRepository: BankRepository
    @MockBean
    private lateinit var clientRepository: ClientRepository

    private val mocks : Mocks = Mocks()


    @Test
    fun `should add a new Register`() {
        val registerForm = RegisterForm(1, "Accout 1", 1, "Bank 1", "Client 1", "333.333.333-33" )

        Mockito.`when`(clientRepository.findByDocumentCPF("333.333.333-33")).thenReturn(mocks.getOptClient())
        Mockito.`when`(bankRepository.findById(1)).thenReturn(mocks.getOptBank())
        Mockito.`when`(repository.save(Mockito.any())).thenReturn(mocks.getAccount(1))
        mockMvc.perform(
            MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper().writeValueAsString(registerForm)))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Account 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.bank").value("1 - Bank 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.client").value("Client 1 - 333.333.333-33"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.transfers[0].id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.transfers[0].comission").value(BigDecimal.valueOf(5.0)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.transfers[0].amount").value(BigDecimal.valueOf(4500.0)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.transfers[0].success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.transfers[0].operation").value("payment"))


    }

    @Test
    fun `should add a new Register Bank of Client not exist`() {
        val registerForm = RegisterForm(1, "Accout 1", 1, "Bank 1", "Client 1", "333.333.333-33" )

        Mockito.`when`(clientRepository.findByDocumentCPF("333.333.333-33")).thenReturn(Optional.empty())
        Mockito.`when`(bankRepository.findById(1)).thenReturn(Optional.empty())
        Mockito.`when`(repository.save(Mockito.any())).thenReturn(mocks.getAccount(1))
        Mockito.`when`(clientRepository.save(Mockito.any())).thenReturn(mocks.getClient(1))
        Mockito.`when`(bankRepository.save(Mockito.any())).thenReturn(mocks.getBank(1))
        mockMvc.perform(
            MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(registerForm)))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Account 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.bank").value("1 - Bank 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.client").value("Client 1 - 333.333.333-33"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.transfers[0].id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.transfers[0].comission").value(BigDecimal.valueOf(5.0)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.transfers[0].amount").value(BigDecimal.valueOf(4500.0)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.transfers[0].success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.transfers[0].operation").value("payment"))


    }
}

