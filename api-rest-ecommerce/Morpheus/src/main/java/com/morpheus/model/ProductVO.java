/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.morpheus.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Jordy
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class ProductVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProduct;

	@Column(name = "product_name", length = 20, nullable = false)
	private String name;
	@Column(name = "image")
	private String image;
	@Column(name = "brand", length = 20)
	private String brand;
	@Column(name = "model", length = 20)
	private String model;
	@Column(name = "price")
	private Float price;
	@Column(name = "description")
	private String description;
	@Column(name = "stock")
	private Integer stock;

	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value="products")
	@JoinTable(name = "products_categories", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<CategoryVO> categories = new HashSet<>();

	public ProductVO(String productName, Float price) {
		super();
		this.name = productName;
		this.price = price;
	}

	public void addCategory(CategoryVO categoryVO) {
		categories.add(categoryVO);
		categoryVO.getProducts().add(this);
	}

	public void removeCategory(CategoryVO categoryVO) {
		categories.remove(categoryVO);
		categoryVO.getProducts().remove(this);
	}
}
