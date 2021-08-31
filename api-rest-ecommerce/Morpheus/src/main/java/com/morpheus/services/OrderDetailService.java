package com.morpheus.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.morpheus.generics.CrudGenericoServiceImpl;
import com.morpheus.model.OrderDetailVO;
import com.morpheus.repository.IOrderDetailRepos;



@Service
@Transactional
public class OrderDetailService extends CrudGenericoServiceImpl<OrderDetailVO, Long, IOrderDetailRepos> {

	public List<OrderDetailVO> findAll() {
		return this.repos.findAll();
	}

	public Optional<OrderDetailVO> findById(Long id) {
		return this.repos.findById(id);
	}

	public void deleteById(Long id) {
		this.repos.deleteById(id);
	}

	public boolean existsById(Long id) {
		return this.repos.existsById(id);
	}
}
