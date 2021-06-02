package at.technikumwien.personwebapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


/*@Getter
@Setter
@ToString
@EqualsAndHashCode*/
@Data // Data instead of all above
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "t_person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Sex sex;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birtDate;

    @Column(nullable = false)
    private boolean active;

    public Person(Sex sex, String firstName, String lastName, LocalDate birtDate, boolean active) {
        this(null, sex, firstName, lastName, birtDate, active);
    }

    @JsonIgnore
    public String getName() {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("first name is null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("last name is null or blank");
        }
        return firstName + " " + lastName;
    }
}
