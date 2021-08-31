package com.morpheus.generics;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class CrudGenericoServiceImpl<T, ID, R extends JpaRepository<T, ID>> {

	@Autowired
	protected R repos;

	// guardar
	public T save(T t) {
		return repos.save(t);
	}

	// busca por id
	public Optional<T> findById(ID id) {
		return repos.findById(id);
	}

	// listar todos
	public List<T> findAll() {
		return repos.findAll();
	}

	// paginacion
	public Page<T> findAll(Pageable pageable) {
		return repos.findAll(pageable);
	}

	// actualizar
	public T update(T entity) {
		return repos.save(entity);
	}

	// eliminar
	public void delete(T t) {
		repos.delete(t);
	}

	// eliminar por id
	public void deleteById(ID id) {
		repos.deleteById(id);
	}

	// existe por id
	public void existById(ID id) {
		repos.existsById(id);
	}

}
