/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.morpheus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.morpheus.model.ProductVO;


/**
 *
 * @author Jordy
 */
public interface IProductRepos extends JpaRepository<ProductVO, Long> {

	Optional<ProductVO> findByName(String productName);

	boolean existsByName(String productName);
}
