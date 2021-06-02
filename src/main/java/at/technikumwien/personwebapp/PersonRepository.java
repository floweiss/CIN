package at.technikumwien.personwebapp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {     // JpaRepository<Entity, PK-Type>
    // auto generates save, findAll, findById,... for Person

    // auto generates query by name of method
    List<Person> findAllByActiveTrue();
}
