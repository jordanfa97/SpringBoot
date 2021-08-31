package com.morpheus.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.morpheus.generics.CrudGenericoServiceImpl;
import com.morpheus.model.ProductVO;
import com.morpheus.repository.IProductRepos;



@Service
@Transactional
public class ProductService extends CrudGenericoServiceImpl<ProductVO, Long, IProductRepos> {

	public Optional<ProductVO> findByName(String productName) {
		return this.repos.findByName(productName);
	}

	public Optional<ProductVO> findById(Long id) {
		return this.repos.findById(id);
	}

	public ProductVO save(ProductVO product) {
		return this.repos.save(product);
	}

	public List<ProductVO> findAll() {
		return this.repos.findAll();
	}
	public Page<ProductVO> findAll(Pageable pageable) {
		return this.repos.findAll(pageable);
	}

	public void delete(ProductVO product) {
		this.repos.delete(product);
	}

	public void deleteById(Long id) {
		this.repos.deleteById(id);
	}

	public boolean existsByName(String productName) {
		return this.repos.existsByName(productName);
	}

	public boolean existsById(Long id) {
		return this.repos.existsById(id);
	}
}
