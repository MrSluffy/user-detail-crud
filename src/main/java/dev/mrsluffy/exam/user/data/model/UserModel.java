package dev.mrsluffy.exam.user.data.model;

import dev.mrsluffy.exam.user.data.entities.UserGender;
import dev.mrsluffy.exam.user.data.entities.UserRole;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * product
 *
 * @author John Andrew Camu <werdna.jac@gmail.com>
 * @version 1.0
 * @since 1/28/2025
 **/

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String email;
    private String name;
    private LocalDate birthDate;
    private int age;
    private UserGender gender;
    private UserRole role;
}