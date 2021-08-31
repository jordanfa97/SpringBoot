package com.morpheus.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.morpheus.generics.CrudGenericoServiceImpl;
import com.morpheus.model.OrderVO;
import com.morpheus.repository.IOrderRepos;



@Service
@Transactional
public class OrderService extends CrudGenericoServiceImpl<OrderVO, Long, IOrderRepos> {

	public Optional<OrderVO> findByDate(String date) {
		return this.repos.findByDate(date);
	}

	public Optional<OrderVO> findByUser(String username) {
		return this.repos.findByUser(username);
	}

	public Optional<OrderVO> findById(Long id) {
		return this.repos.findById(id);
	}

	public OrderVO save(OrderVO order) {
		return this.repos.save(order);
	}

	public List<OrderVO> findAll() {
		return this.repos.findAll();
	}

	public void delete(OrderVO order) {
		this.repos.delete(order);
	}

	public void deleteById(Long id) {
		this.repos.deleteById(id);
	}

	public boolean existsById(Long id) {
		return this.repos.existsById(id);
	}
}
