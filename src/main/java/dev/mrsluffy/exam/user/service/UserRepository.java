package dev.mrsluffy.exam.user.service;

import dev.mrsluffy.exam.user.data.entities.UserData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * product
 *
 * @author John Andrew Camu <werdna.jac@gmail.com>
 * @version 1.0
 * @since 1/28/2025
 **/

@Repository
public interface UserRepository extends JpaRepository<UserData, Integer> {
    @Query("SELECT p FROM UserData p WHERE p.isDeleted = false AND " +
            "(LOWER(p.email) LIKE LOWER(CONCAT('%', :email, '%')) OR :email IS NULL)")
    Page<UserData> findByEmail(String email, Pageable pageable);
}
