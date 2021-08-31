package com.morpheus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.model.OrderDetailVO;
import com.morpheus.services.OrderDetailService;
import com.morpheus.utils.MessageUtils;



@CrossOrigin
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {

	@Autowired
	private OrderDetailService orderDetailService;

	@GetMapping("/list")
	public ResponseEntity<List<OrderDetailVO>> list() {

		List<OrderDetailVO> list = orderDetailService.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<OrderDetailVO> findById(@PathVariable("id") Long id) {

		if (!orderDetailService.existsById(id))
			return new ResponseEntity(new MessageUtils("no existe lo que buscas!!"), HttpStatus.NOT_FOUND);

		OrderDetailVO detail = orderDetailService.findById(id).get();
		return new ResponseEntity<>(detail, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		if (!orderDetailService.existsById(id))
			return new ResponseEntity<>(new MessageUtils("no existe lo que buscas!!"), HttpStatus.NOT_FOUND);

		orderDetailService.deleteById(id);
		return new ResponseEntity<>(new MessageUtils("Se ha eliminado!!"), HttpStatus.OK);

	}

}
