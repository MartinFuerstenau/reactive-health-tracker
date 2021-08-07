package health.tracker.controller

import health.tracker.model.HealthRecord
import health.tracker.persistence.HealthRecordRepository
import kotlinx.coroutines.flow.reduce
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class HealthRecordController(private val healthRecordRepository: HealthRecordRepository) {

    @PostMapping("/health/{profileId}/record")
    suspend fun storeHealthRecord(@PathVariable profileId: Long, @RequestBody record: HealthRecord): HealthRecord {
        return healthRecordRepository.save(
            HealthRecord(
                null, profileId, record.temperature, record.bloodPressure, record.heartRate, record.date
            )
        )
    }

    @GetMapping("/health/{profileId}/avg")
    suspend fun avg(@PathVariable profileId: Long): AverageHealthStatus =
        healthRecordRepository.findByProfileId(profileId)
            .reduce(
                AverageHealthStatus(0, 0.0, 0.0, 0.0)
            ) { s, r ->
                AverageHealthStatus(
                    s.cnt + 1,
                    s.temperature + r.temperature,
                    s.bloodPressure + r.bloodPressure,
                    s.heartRate + r.heartRate
                )
            }.map { s ->
                AverageHealthStatus(
                    s.cnt,
                    if (s.cnt != 0) s.temperature / s.cnt else 0.0,
                    if (s.cnt != 0) s.bloodPressure / s.cnt else 0.0,
                    if (s.cnt != 0) s.heartRate / s.cnt else 0.0
                )
            }
}

class AverageHealthStatus(
    var cnt: Int, var temperature: Double,
    var bloodPressure: Double, var heartRate: Double
)