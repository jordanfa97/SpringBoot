package com.morpheus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.model.RoleVO;
import com.morpheus.security.enums.RoleName;



public interface IRoleRepos extends JpaRepository<RoleVO, Long> {

	Optional<RoleVO> findByRoleName(RoleName roleName);
	
	boolean existsByRoleName(String roleName);
}
