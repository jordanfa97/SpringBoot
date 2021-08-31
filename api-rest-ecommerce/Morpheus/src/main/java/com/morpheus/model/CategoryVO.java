package com.morpheus.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class CategoryVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCategory;

	@Column(name = "category_name", unique = true, length = 10, nullable = false)
	private String categoryName;

	// debe de excluirse porque si no da stackoverflow
	@EqualsAndHashCode.Exclude
	@ManyToMany(mappedBy = "categories")
	// debe de excluirse porque si no da recursion infinita
	@JsonIgnoreProperties(value = "categories")
	private Set<ProductVO> products = new HashSet<>();

	public CategoryVO(String categoryName) {
		super();
		this.categoryName = categoryName;
	}

}
