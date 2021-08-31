package com.morpheus.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.model.CategoryVO;
import com.morpheus.services.CategoryService;
import com.morpheus.utils.MessageUtils;



@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/list")
	public ResponseEntity<List<CategoryVO>> list() {

		List<CategoryVO> list = categoryService.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<CategoryVO> getById(@PathVariable("id") Long id) {

		if (!categoryService.existsById(id))
			return new ResponseEntity(new MessageUtils("no existe la categoria"), HttpStatus.NOT_FOUND);

		CategoryVO categoryVO = categoryService.findById(id).get();
		return new ResponseEntity<>(categoryVO, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody CategoryVO categoryVO) {

		if (StringUtils.isBlank(categoryVO.getCategoryName()))
			return new ResponseEntity<>(new MessageUtils("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

		if (categoryService.existsByCategoryName(categoryVO.getCategoryName()))
			return new ResponseEntity<>(new MessageUtils("ese nombre ya existe"), HttpStatus.BAD_REQUEST);

		CategoryVO category = new CategoryVO(categoryVO.getCategoryName());
		categoryService.save(category);

		return new ResponseEntity<>(new MessageUtils("categoria creada"), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		if (!categoryService.existsById(id))
			return new ResponseEntity<>(new MessageUtils("la categoria no existe"), HttpStatus.NOT_FOUND);

		categoryService.deleteById(id);
		return new ResponseEntity<>(new MessageUtils("categoria eliminada"), HttpStatus.OK);

	}

}
