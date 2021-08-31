package com.morpheus.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_detail")
public class OrderDetailVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iddetail")
	private Long idDetail;

	// debe de excluirse porque si no da recursion infinita
	@JsonIgnoreProperties(value = "ordersDetails")
	@ManyToOne
	@JoinColumn(name = "idOrder")
	private OrderVO order;

	@ManyToOne
	@JoinColumn(name = "idProduct")
	private ProductVO product;

	@Column(name = "amount")
	private Integer amount;

}
