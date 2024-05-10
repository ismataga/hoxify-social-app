package com.hoxify.hoxify_new.user;

import com.hoxify.hoxify_new.user.dto.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
//    @Query(value = "select activationToken from users where activationToken =?" ,nativeQuery = true)
    @Query(value = "SELECT u FROM User u WHERE u.activationToken = :token")
    User findByActivationToken(String token);

    @Query(value = "SELECT u FROM User u")
    Page<UserProjection> getAllUserRecords(Pageable pageable);

    Page<User> findByIdNot(Long id, Pageable pageable);

    User findByPasswordResetToken(String token);
}
