package health.tracker.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Embedded
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate


@Table
data class HealthRecord(
    @Id var id: Long?,
    var profileId: Long?,
    var temperature: Double,
    var bloodPressureSystolic: Int,
    var bloodPressureDiastolic: Int,
    var heartRate: Double,
    var date: LocalDate
)
