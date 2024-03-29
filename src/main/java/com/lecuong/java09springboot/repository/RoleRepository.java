package com.lecuong.java09springboot.repository;

import com.lecuong.java09springboot.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {

    List<Role> findByIdIn(List<Long> ids);
}
