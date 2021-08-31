package com.morpheus.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iduser", unique = true)
	private Long idUser;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<OrderVO> orders;

	// debe de excluirse porque si no da recursion infinita
	@JsonIgnoreProperties(value="users")
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleVO> roles = new HashSet<>();

	@Column(name = "username", length = 30)
	private String username;
	@Column(name = "surname", length = 30)
	private String surname;
	@Column(name = "password")
	private String password;
	@Column(name = "nif", length = 9, unique = true)
	private String nif;
	@Email
	@Column(name = "email", length = 30, unique = true)
	private String email;

	public UserVO(String username, String password, @Email String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public void addRole(RoleVO roleVO) {
		roles.add(roleVO);
		roleVO.getUsers().add(this);
	}

	public void removeRole(RoleVO roleVO) {
		roles.remove(roleVO);
		roleVO.getUsers().remove(this);
	}

	public void addOrder(OrderVO orderVO) {
		orders.add(orderVO);
	}

	public void removeOrder(OrderVO orderVO) {
		orders.remove(orderVO);
	}

}
