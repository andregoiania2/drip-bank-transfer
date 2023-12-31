package br.com.usedrip.banktransfers.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPI3Config {
    @Bean
    fun customOpenAPI(): OpenAPI? {
        return OpenAPI()
            .info(Info().title("API Transfer"))
            .components(Components())
            .addTagsItem(Tag().name("Transfer").description("Operações de transferência"))

        //http://localhost:8080/swagger-ui/index.html
    }
}