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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.model.UserVO;
import com.morpheus.services.UserService;
import com.morpheus.utils.MessageUtils;



@CrossOrigin
@RestController
@RequestMapping({ "/user" })
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/list")
	public ResponseEntity<List<UserVO>> list() {

		List<UserVO> list = userService.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<UserVO> findById(@PathVariable("id") Long id) {

		if (!userService.existsById(id))
			return new ResponseEntity(new MessageUtils("no existe el usuario"), HttpStatus.NOT_FOUND);

		UserVO userVO = userService.findById(id).get();
		return new ResponseEntity<>(userVO, HttpStatus.OK);
	}

	@GetMapping("/findByUsername/{name}")
	public ResponseEntity<UserVO> findByUsername(@PathVariable("name") String name) {
		if (!userService.existsByUsername(name))
			return new ResponseEntity(new MessageUtils("no existe"), HttpStatus.NOT_FOUND);
		UserVO userVO = userService.findByUsername(name).get();
		return new ResponseEntity<>(userVO, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody UserVO userVO, @PathVariable("id") Long id) {

		if (!userService.existsById(id))
			return new ResponseEntity<>(new MessageUtils("no existe"), HttpStatus.NOT_FOUND);
		if (StringUtils.isBlank(userVO.getUsername()))
			return new ResponseEntity<>(new MessageUtils("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		if (userService.existsByUsername(userVO.getUsername())
				&& userService.findByUsername(userVO.getUsername()).get().getIdUser() != id)
			return new ResponseEntity<>(new MessageUtils("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
		if (userService.existsByEmail(userVO.getEmail())
				&& userService.findByUsername(userVO.getEmail()).get().getIdUser() != id)
			return new ResponseEntity<>(new MessageUtils("ese email ya existe"), HttpStatus.BAD_REQUEST);

		UserVO userVOUpdated = userService.findById(id).get();
		userVOUpdated.setUsername(userVO.getUsername());
		userVOUpdated.setEmail(userVO.getEmail());
		userVOUpdated.setNif(userVO.getNif());
		userVOUpdated.setPassword(userVO.getPassword());
		userVOUpdated.setOrders(userVO.getOrders());
		userVOUpdated.setRoles(userVO.getRoles());
		userVOUpdated.setSurname(userVO.getSurname());
		userService.save(userVOUpdated);

		return new ResponseEntity<>(new MessageUtils("user actualizado"), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		if (!userService.existsById(id))
			return new ResponseEntity<>(new MessageUtils("el user no existe"), HttpStatus.NOT_FOUND);

		userService.deleteById(id);
		return new ResponseEntity<>(new MessageUtils("user eliminado"), HttpStatus.OK);

	}
}
