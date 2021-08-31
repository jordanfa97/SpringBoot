package com.morpheus.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.morpheus.generics.CrudGenericoServiceImpl;
import com.morpheus.model.CategoryVO;
import com.morpheus.repository.ICategoryRepos;



@Service
@Transactional
public class CategoryService extends CrudGenericoServiceImpl<CategoryVO, Long, ICategoryRepos> {

	public Optional<CategoryVO> findByCategoryName(String categoryName) {
		return this.repos.findByCategoryName(categoryName);
	}

	public Optional<CategoryVO> findById(Long id) {
		return this.repos.findById(id);
	}

	public CategoryVO save(CategoryVO category) {
		return this.repos.save(category);
	}

	public List<CategoryVO> findAll() {
		return this.repos.findAll();
	}

	public void delete(CategoryVO category) {
		this.repos.delete(category);
	}

	public void deleteById(Long id) {
		this.repos.deleteById(id);
	}

	public boolean existsById(Long id) {
		return this.repos.existsById(id);
	}

	public boolean existsByCategoryName(String categoryName) {
		return this.repos.existsByCategoryName(categoryName);
	}
}
