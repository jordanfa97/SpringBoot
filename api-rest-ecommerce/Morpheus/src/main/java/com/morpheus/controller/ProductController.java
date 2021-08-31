package com.morpheus.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.dto.ProductDto;
import com.morpheus.model.ProductVO;
import com.morpheus.services.ProductService;
import com.morpheus.utils.MessageUtils;



@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/list")
	public ResponseEntity<List<ProductVO>> list() {

		List<ProductVO> list = productService.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/pages")
	public ResponseEntity<Page<ProductVO>> pages(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String order,
			@RequestParam(defaultValue = "true") boolean asc) {

		Page<ProductVO> pages = productService.findAll(PageRequest.of(page, size, Sort.by(order)));
		if (!asc)
			pages = productService.findAll(PageRequest.of(page, size, Sort.by(order).descending()));
		
		return new ResponseEntity<Page<ProductVO>>(pages, HttpStatus.OK);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<ProductVO> findById(@PathVariable("id") Long id) {

		if (!productService.existsById(id))
			return new ResponseEntity(new MessageUtils("no existe el producto"), HttpStatus.NOT_FOUND);

		ProductVO productVO = productService.findById(id).get();
		return new ResponseEntity<>(productVO, HttpStatus.OK);
	}

	@GetMapping("/findByName/{name}")
	public ResponseEntity<ProductVO> findByUsername(@PathVariable("name") String name) {
		if (!productService.existsByName(name))
			return new ResponseEntity(new MessageUtils("no existe"), HttpStatus.NOT_FOUND);
		ProductVO productVO = productService.findByName(name).get();
		return new ResponseEntity<>(productVO, HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody ProductDto productDto) {

		if (StringUtils.isBlank(productDto.getProductName()))
			return new ResponseEntity<>(new MessageUtils("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

		if (productDto.getPrice() == null || productDto.getPrice() < 0)
			return new ResponseEntity<>(new MessageUtils("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

		if (productService.existsByName(productDto.getProductName()))
			return new ResponseEntity<>(new MessageUtils("ese nombre ya existe"), HttpStatus.BAD_REQUEST);

		ProductVO productVO = new ProductVO(productDto.getProductName(), productDto.getPrice());
		productService.save(productVO);

		return new ResponseEntity<>(new MessageUtils("producto creado"), HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody ProductVO productVO, @PathVariable("id") Long id) {

		if (!productService.existsById(id))
			return new ResponseEntity<>(new MessageUtils("no existe"), HttpStatus.NOT_FOUND);
		if (StringUtils.isBlank(productVO.getName()))
			return new ResponseEntity<>(new MessageUtils("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		if (productService.existsByName(productVO.getName())
				&& productService.findByName(productVO.getName()).get().getIdProduct() != id)
			return new ResponseEntity<>(new MessageUtils("ese nombre ya existe"), HttpStatus.BAD_REQUEST);

		ProductVO productUpdated = productService.findById(id).get();
		productUpdated.setBrand(productVO.getBrand());
		productUpdated.setCategories(productVO.getCategories());
		productUpdated.setDescription(productVO.getDescription());
		productUpdated.setImage(productVO.getImage());
		productUpdated.setModel(productVO.getModel());
		productUpdated.setPrice(productVO.getPrice());
		productUpdated.setStock(productVO.getStock());
		productUpdated.setName(productVO.getName());
		productService.save(productUpdated);

		return new ResponseEntity<>(new MessageUtils("producto actualizado"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		if (!productService.existsById(id))
			return new ResponseEntity<>(new MessageUtils("el producto no existe"), HttpStatus.NOT_FOUND);

		productService.deleteById(id);
		return new ResponseEntity<>(new MessageUtils("producto eliminado"), HttpStatus.OK);

	}

}
