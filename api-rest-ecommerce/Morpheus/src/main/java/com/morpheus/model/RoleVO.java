package com.morpheus.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.morpheus.security.enums.RoleName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class RoleVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idrole")
	private Long idRole;

	@Column(name = "role_name", length = 10, nullable = false)
	@Enumerated(EnumType.STRING)
	private RoleName roleName;

	// se excluye para evitar stack overflow
	@EqualsAndHashCode.Exclude
	// debe de excluirse porque si no da recursion infinita
	@JsonIgnoreProperties(value = "roles")
	@ManyToMany(mappedBy = "roles")
	private Set<UserVO> users = new HashSet<>();

	public RoleVO(RoleName roleName) {
		super();
		this.roleName = roleName;
	}

}
