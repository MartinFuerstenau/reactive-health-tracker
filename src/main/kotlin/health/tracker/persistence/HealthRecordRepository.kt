package health.tracker.persistence

import health.tracker.model.HealthRecord
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface HealthRecordRepository: ReactiveCrudRepository<HealthRecord, Long> {
    @Query("select p.* from health_record p where p.profile_id = :profileId ")
    fun findByProfileId(profileId: Long): Flux<HealthRecord>
}