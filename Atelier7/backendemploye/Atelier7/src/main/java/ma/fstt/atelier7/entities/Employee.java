package ma.fstt.atelier7.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "employees")

@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le prénom est requis")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Le nom est requis")
    @Column(nullable = false)
    private String lastName;

    @Email(message = "Email non valide")
    @NotBlank(message = "L'email est requis")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Le salaire est requis")
    @PositiveOrZero(message = "Le salaire doit être positif ou zéro")
    private Double salary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
