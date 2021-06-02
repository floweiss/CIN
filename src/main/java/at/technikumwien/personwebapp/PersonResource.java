package at.technikumwien.personwebapp;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/resources/persons")
public class PersonResource {
    private final PersonRepository personRepository;

    // see: http://localhost:8080/resources/persons
    @GetMapping
    public List<Person> retrieveAll(@RequestParam(defaultValue = "false") boolean all) {
        if (all) {
            return personRepository.findAll();
        }
        return personRepository.findAllByActiveTrue();
    }

    @GetMapping("/{id}")
    public Person retrieve(@PathVariable long id) {
        //return personRepository.findById(id).get(); // without check if optional exists (not good)
        Optional<Person> person = personRepository.findById(id);
        return person.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Person with ID " + id + " not found"
        ));
    }
}
