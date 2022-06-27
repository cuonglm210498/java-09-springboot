package com.lecuong.java09springboot.repository;

import com.lecuong.java09springboot.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * author CuongLM
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findUserByUserNameAndPassword(String userName, String password);

    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhone(String phone);

    long countAllByUserName(String userName);

    long countAllByEmail(String email);

    long countAllByPhone(String phone);

    @Query(value = "SELECT u FROM User u WHERE u.createdBy = :createdBy")
    List<User> getByCreatedBy(@Param("createdBy") String createdBy);
}
