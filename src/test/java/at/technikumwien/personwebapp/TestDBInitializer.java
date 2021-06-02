package at.technikumwien.personwebapp;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.util.List;

@Configuration
@Profile("test") // used for profile "test"
@RequiredArgsConstructor
public class TestDBInitializer {
    private final PersonRepository personRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void handleApplicationReady() {
        //System.out.println("-------------------- Application ready --------------------");

        personRepository.saveAll(
            List.of(
                new Person(Sex.MALE, "Max", "Mustermann", LocalDate.of(1990, 1, 10), true),
                new Person(Sex.FEMALE, "Marie", "Musterfrau", LocalDate.of(1992, 10, 20), false)
            )
        );
    }
}
