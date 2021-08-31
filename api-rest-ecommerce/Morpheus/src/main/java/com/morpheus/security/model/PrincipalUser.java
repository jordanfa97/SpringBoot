package com.morpheus.security.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.morpheus.model.UserVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public static PrincipalUser build(UserVO userVO) {
		List<GrantedAuthority> roles = userVO.getRoles().stream()
				.map(x -> new SimpleGrantedAuthority(x.getRoleName().name())).collect(Collectors.toList());

		return new PrincipalUser(userVO.getUsername(), userVO.getPassword(), roles);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

}
