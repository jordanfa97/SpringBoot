package com.morpheus.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.morpheus.generics.CrudGenericoServiceImpl;
import com.morpheus.model.UserVO;
import com.morpheus.repository.IUserRepos;



@Service
@Transactional
public class UserService extends CrudGenericoServiceImpl<UserVO, Long, IUserRepos> {

	public Optional<UserVO> findByUsername(String username) {
		return this.repos.findByUsername(username);
	}

	public Optional<UserVO> findById(Long id) {
		return this.repos.findById(id);
	}

	public UserVO save(UserVO user) {
		return this.repos.save(user);
	}

	public List<UserVO> findAll() {
		return this.repos.findAll();
	}

	public void delete(UserVO user) {
		this.repos.delete(user);
	}

	public void deleteById(Long id) {
		this.repos.deleteById(id);
	}

	public boolean existsByUsername(String username) {
		return this.repos.existsByUsername(username);
	}

	public boolean existsByEmail(String email) {
		return this.repos.existsByEmail(email);
	}

	public boolean existsById(Long id) {
		return this.repos.existsById(id);
	}

}
