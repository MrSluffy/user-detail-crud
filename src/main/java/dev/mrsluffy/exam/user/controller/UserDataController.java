package dev.mrsluffy.exam.user.controller;

import dev.mrsluffy.exam.helper.common.response.UnauthorizedException;
import dev.mrsluffy.exam.user.data.entities.UserGender;
import dev.mrsluffy.exam.user.data.entities.UserRole;
import dev.mrsluffy.exam.user.data.model.UserModel;
import dev.mrsluffy.exam.user.data.model.UserRequestModel;
import dev.mrsluffy.exam.user.service.UserService;
import dev.mrsluffy.exam.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * product
 *
 * @author John Andrew Camu <werdna.jac@gmail.com>
 * @version 1.0
 * @since 1/28/2025
 **/

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserDataController {

    private final UserService userService;

    @PostMapping("/CreateUser")
    public ResponseEntity<?> CreateUser(
            @RequestBody UserRequestModel userModel) {
        try {
            return ResponseEntity.ok(userService.addUser(userModel));
        } catch (HttpClientErrorException.BadRequest err) {
            return ResponseEntity.badRequest().body(Utilities.error(err.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Utilities.error(e.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(Utilities.errorTraceId(exception.getMessage()));
        }
    }

    @GetMapping("/GetUsers")
    public ResponseEntity<?> GetUsers(
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "email") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {

            if (page <= 0) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Page number must be greater than 0");
            }

            int pageIndex = page > 0 ? page - 1 : 0;
            Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageIndex, size, sort);
            return ResponseEntity.ok(userService.getUsers(email, pageable));
        } catch (HttpClientErrorException.BadRequest err) {
            return ResponseEntity.badRequest().body(Utilities.error(err.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Utilities.error(e.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(Utilities.errorTraceId(exception.getMessage()));
        }
    }

    @PutMapping("/UpdateUser")
    public ResponseEntity<?> UpdateUser(
            @RequestParam Integer userId,
            @RequestBody UserRequestModel userModel) {
        try {
            return ResponseEntity.ok(userService.updateUser(userId, userModel));
        } catch (HttpClientErrorException.BadRequest err) {
            return ResponseEntity.badRequest().body(Utilities.error(err.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Utilities.error(e.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(Utilities.errorTraceId(exception.getMessage()));
        }
    }

    @DeleteMapping("/DeleteUser")
    public ResponseEntity<?> DeleteProduct(
            @RequestParam Integer userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(Utilities.message("msg", "Deleted " + userId + " Successfully"));
        } catch (HttpClientErrorException.BadRequest err) {
            return ResponseEntity.badRequest().body(Utilities.error(err.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Utilities.error(e.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(Utilities.errorTraceId(exception.getMessage()));
        }
    }


    @GetMapping("GetUserRole")
    public ResponseEntity<?> GetUserRole() {
        List<String> userRoles = Arrays.stream(UserRole.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userRoles);
    }

    @GetMapping("GetUserGender")
    public ResponseEntity<?> GetUserGender() {
        List<String> userRoles = Arrays.stream(UserGender.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userRoles);
    }
}
