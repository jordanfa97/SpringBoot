package com.morpheus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.model.UserVO;


public interface IUserRepos extends JpaRepository<UserVO, Long> {

	Optional<UserVO> findByUsername(String nombreUsuario);

	boolean existsByUsername(String nombreUsuario);

	boolean existsByEmail(String email);

}
