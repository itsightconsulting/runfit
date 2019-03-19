package com.itsight.repository;

import com.itsight.domain.SecurityRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SecurityRoleRepository extends JpaRepository<SecurityRole, Long> {

    SecurityRole findBySecurityUserUsername(String username);

    SecurityRole findBySecurityUserIdAndRole(Long securityUserId, String role);

    @Modifying
    @Query("DELETE FROM SecurityRole S WHERE S.id = ?1")
    void deleteById(Long id);

}
