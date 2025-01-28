package dev.mrsluffy.exam.user.data.entities;

import dev.mrsluffy.exam.helper.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;


@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_details")
public class UserData extends BaseEntity {

    @NotNull
    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 50)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Transient
    private int age;

    @Enumerated(EnumType.STRING)
    private UserGender gender;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        this.age = calculateAge();
    }

    public int getAge() {
        return calculateAge();
    }

    private int calculateAge() {
        if (birthDate != null) {
            return Period.between(birthDate, LocalDate.now()).getYears();
        }
        return 0;
    }
}
