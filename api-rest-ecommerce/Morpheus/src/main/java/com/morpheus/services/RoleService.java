package com.morpheus.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.morpheus.generics.CrudGenericoServiceImpl;
import com.morpheus.model.RoleVO;
import com.morpheus.repository.IRoleRepos;
import com.morpheus.security.enums.RoleName;


@Service
@Transactional
public class RoleService extends CrudGenericoServiceImpl<RoleVO, Long, IRoleRepos> {

	public Optional<RoleVO> findByRoleName(RoleName roleName) {
		return this.repos.findByRoleName(roleName);
	}

	public Optional<RoleVO> findById(Long id) {
		return this.repos.findById(id);
	}

	public List<RoleVO> findAll() {
		return this.repos.findAll();
	}

	public void delete(RoleVO rol) {
		this.repos.delete(rol);
	}

	public void deleteById(Long id) {
		this.repos.deleteById(id);
	}

	public boolean existsById(Long id) {
		return this.repos.existsById(id);
	}

	public boolean existsByRoleName(String roleName) {
		return this.repos.existsByRoleName(roleName);
	}
}
