package health.tracker.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table
data class Profile(@Id var id:Long? = null, var firstName: String, var lastName: String, var birthDate: LocalDate)