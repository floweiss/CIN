package at.technikumwien.personwebapp;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ActiveProfiles("test") // profile "test" is used
@Tag("extended")
@Transactional // rollback transactions after each test -> tests are independent
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // execute tests according to @Order annotation -> BAD because tests should not depend on each other
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    //@Order(1)
    public void testSave() {
        Person person = new Person(Sex.MALE, "Maxi", "Musterkind", LocalDate.of(2005, 3, 9), true);
        person = personRepository.save(person);

        assertNotNull(person.getId());
        assertEquals(3, personRepository.count());
    }

    @Test
    //@Order(2)
    public void testFindAllByActiveTrue() {
        var persons = personRepository.findAllByActiveTrue();

        assertEquals(1, persons.size());
    }
}
// Integration test
