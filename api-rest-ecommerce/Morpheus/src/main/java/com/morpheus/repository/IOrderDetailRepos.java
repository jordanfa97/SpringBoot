package com.morpheus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.model.OrderDetailVO;


public interface IOrderDetailRepos extends JpaRepository<OrderDetailVO, Long> {

}
