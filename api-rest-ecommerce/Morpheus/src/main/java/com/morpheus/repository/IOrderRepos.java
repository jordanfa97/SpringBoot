package com.morpheus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.model.OrderVO;


public interface IOrderRepos extends JpaRepository<OrderVO, Long> {

	Optional<OrderVO> findByDate(String date);

	Optional<OrderVO> findByUser(String username);
}
