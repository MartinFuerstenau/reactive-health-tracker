package health.tracker.controller

import health.tracker.model.Profile
import health.tracker.persistence.ProfileRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class ProfileController(val profileRepository: ProfileRepository) {

    @PostMapping("/profile")
    suspend fun saveProfile(@RequestBody profile: Profile) : Profile = profileRepository.save(profile)
}