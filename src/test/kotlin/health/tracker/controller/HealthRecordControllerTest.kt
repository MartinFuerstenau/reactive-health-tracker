package health.tracker.controller

import com.fasterxml.jackson.databind.ObjectMapper
import health.tracker.model.HealthRecord
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate

@SpringBootTest
internal class HealthRecordControllerTest {

    private val profileId = 1L

    @Autowired
    lateinit var controller: HealthRecordController

    @Autowired
    lateinit var mapper: ObjectMapper

    lateinit var client: WebTestClient

    @BeforeEach
    fun setup() {
        client = WebTestClient.bindToController(controller).build()
    }

    @AfterEach
    fun tearDown() {
        client.delete().uri("/health/$profileId/records").exchange().expectStatus().isNoContent
    }

    @Test
    fun storeHealthRecord() {
        val record = HealthRecord(null, 1L, 36.0, 120.0, 60.0, LocalDate.now())
        val body = mapper.writeValueAsString(record)

        client
            .post()
            .uri("/health/$profileId/record")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id")
            .isNotEmpty
    }

    @Test
    fun records() {
        for (i in 1..5) {
            val record = HealthRecord(null, profileId, 36.0 + i, 120.0 + i, 60.0 + i, LocalDate.now())
            val body = mapper.writeValueAsString(record)
            client
                .post()
                .uri("/health/$profileId/record")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated
        }

        client
            .get()
            .uri("/health/$profileId/records")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(HealthRecord::class.java)
            .hasSize(5)
    }

    @Test
    fun avg() {
        for (i in 1..5) {
            val record = HealthRecord(null, profileId, 36.0 + i, 120.0 + i, 60.0 + i, LocalDate.now())
            val body = mapper.writeValueAsString(record)
            client
                .post()
                .uri("/health/$profileId/record")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated
        }

        client
            .get()
            .uri("/health/$profileId/avg")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.cnt").isEqualTo(5)
            .jsonPath("$.temperature").isNumber
            .jsonPath("$.temperature").isEqualTo(39.0)
            .jsonPath("$.heartRate").isNumber
            .jsonPath("$.heartRate").isEqualTo(63.0)
            .jsonPath("$.bloodPressure").isNumber
            .jsonPath("$.bloodPressure").isEqualTo(123.0)
    }

}