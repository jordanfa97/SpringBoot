package com.morpheus.controller;

import java.util.List;

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

import com.morpheus.model.OrderVO;
import com.morpheus.services.OrderService;
import com.morpheus.utils.MessageUtils;



@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/list")
	public ResponseEntity<List<OrderVO>> list() {

		List<OrderVO> list = orderService.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<OrderVO> findById(@PathVariable("id") Long id) {

		if (!orderService.existsById(id))
			return new ResponseEntity(new MessageUtils("no existe el pedido"), HttpStatus.NOT_FOUND);

		OrderVO orderVO = orderService.findById(id).get();
		return new ResponseEntity<>(orderVO, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody OrderVO orderVO) {

		OrderVO order = new OrderVO(orderVO.getDate());
		
		orderService.save(order);

		return new ResponseEntity<>(new MessageUtils("order created"), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		if (!orderService.existsById(id))
			return new ResponseEntity<>(new MessageUtils("el order no existe"), HttpStatus.NOT_FOUND);

		orderService.deleteById(id);
		return new ResponseEntity<>(new MessageUtils("order eliminado"), HttpStatus.OK);

	}
}
