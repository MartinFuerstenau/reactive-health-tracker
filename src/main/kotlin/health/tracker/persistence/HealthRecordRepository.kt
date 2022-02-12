package health.tracker.persistence

import health.tracker.model.HealthRecord
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface HealthRecordRepository : CoroutineCrudRepository<HealthRecord, Long> {
    //@Query("select p.* from health_record p where p.profile_id = :profileId ")
    suspend fun findByProfileId(profileId: Long): Flow<HealthRecord>
}