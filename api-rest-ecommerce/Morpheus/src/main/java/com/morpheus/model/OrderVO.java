package com.morpheus.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class OrderVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idorder")
	private Long idOrder;

	// debe de excluirse porque si no da recursion infinita
	@JsonIgnoreProperties(value = "orders")
	@ManyToOne
	@JoinColumn(name = "idUser")
	private UserVO user;

	// 2012-03-19T07:22Z formato por defecto
	@Column(name = "date", columnDefinition = "TIMESTAMP")
	private OffsetDateTime date;

	@Column(name = "payment_method")
	private String paymentMethod;

	@Column(name = "total_payment")
	private Double total;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<OrderDetailVO> ordersDetails;

	public OrderVO(OffsetDateTime date) {
		super();
		this.date = date;
	}

}
