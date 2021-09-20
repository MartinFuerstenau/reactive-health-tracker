package health.tracker

import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.core.DatabaseClient

@Configuration
class DBConfiguration(db: DatabaseClient) {
    init {
        val initDb = db.sql {
            """ CREATE TABLE IF NOT EXISTS profile (
                    id SERIAL PRIMARY KEY,
                    first_name VARCHAR(20),
                    last_name VARCHAR(20),
                    birth_date Date
                );
                CREATE TABLE IF NOT EXISTS health_record(
                    id SERIAL PRIMARY KEY,
                    profile_id LONG NOT NULL,
                    temperature DECIMAL NOT NULL,
                    blood_pressure_systolic INTEGER NOT NULL,
                    blood_pressure_diastolic INTEGER NOT NULL,
                    heart_rate DECIMAL,
                    date DATE NOT NULL
                );
            """
        }
        initDb.then().subscribe()
    }
}