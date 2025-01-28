package dev.mrsluffy.exam.user.data.model;

import dev.mrsluffy.exam.user.data.entities.UserGender;
import dev.mrsluffy.exam.user.data.entities.UserRole;
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
public class UserRequestModel {
    private String email;
    private String name;
    private LocalDate birthDate;
    private UserGender gender;
    private UserRole role;
}
