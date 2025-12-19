package com.gialong.blog.repository;

import com.gialong.blog.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Tìm kiếm Role theo tên (ví dụ: "ROLE_ADMIN", "ROLE_USER").
     * Đảm bảo tên Role là duy nhất.
     */
    Optional<Role> findByRoleName(String roleName);
}