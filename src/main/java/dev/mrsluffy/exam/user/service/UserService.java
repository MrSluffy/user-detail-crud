package dev.mrsluffy.exam.user.service;

import dev.mrsluffy.exam.helper.common.response.PagedResponse;

import dev.mrsluffy.exam.user.data.entities.UserData;
import dev.mrsluffy.exam.user.data.model.UserModel;
import dev.mrsluffy.exam.user.data.model.UserRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

/**
 * product
 *
 * @author John Andrew Camu <werdna.jac@gmail.com>
 * @version 1.0
 * @since 1/28/2025
 **/

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserModel addUser(UserRequestModel userModel) {
        var user = UserData.builder()
                .name(userModel.getName())
                .email(userModel.getEmail())
                .role(userModel.getRole())
                .gender(userModel.getGender())
                .birthDate(userModel.getBirthDate())
                .build();

        userRepository.save(user);

        return UserModel.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .age(user.getAge())
                .build();
    }


    @Transactional
    public UserModel updateUser(Integer userId, UserRequestModel userModel) {

        if (userId == null) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"UserId cannot be empty");

        var getUserById = userRepository.findById(userId);

        UserData userData = getUserById.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));

        userData.setName(userModel.getName());
        userData.setGender(userModel.getGender());
        userData.setBirthDate(userModel.getBirthDate());
        userData.setRole(userData.getRole());

        userRepository.save(userData);

       return UserModel.builder()
                .name(userData.getName())
                .email(userData.getEmail())
                .role(userData.getRole())
                .gender(userData.getGender())
                .birthDate(userData.getBirthDate())
                .age(userData.getAge())
                .build();
    }


    @Transactional
    public void deleteUser(Integer userId) {

        if (userId == null) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "UserId cannot be empty");

        var getUserById = userRepository.findById(userId);
        UserData userData = getUserById.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));
        userData.setDeleted(true);
        userData.setDeletedAt(LocalDateTime.now());
        userRepository.save(userData);
    }


    public PagedResponse<UserData> getUsers(String email, Pageable pageable) {
        Page<UserData> userData = userRepository.findByEmail(email, pageable);
        return new PagedResponse<>(userData.getContent(), userData.getTotalElements());
    }
}
