package com.morpheus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.model.RoleVO;
import com.morpheus.services.RoleService;



@CrossOrigin
@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping("/list")
	public ResponseEntity<List<RoleVO>> list() {
		List<RoleVO> list = roleService.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
