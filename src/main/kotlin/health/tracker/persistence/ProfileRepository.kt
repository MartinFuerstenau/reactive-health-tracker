package health.tracker.persistence

import health.tracker.model.Profile
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository: CoroutineCrudRepository<Profile, Long> {
}