package com.morpheus.security.controller;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.model.RoleVO;
import com.morpheus.model.UserVO;
import com.morpheus.security.dto.LoginUserDto;
import com.morpheus.security.dto.NewUserDto;
import com.morpheus.security.enums.RoleName;
import com.morpheus.security.jwt.JwtProvider;
import com.morpheus.security.model.JwtToken;
import com.morpheus.services.RoleService;
import com.morpheus.services.UserService;
import com.morpheus.utils.MessageUtils;



@RestController
@RequestMapping("/auth")
@CrossOrigin(value = "*")
public class AuthController {

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult) {

		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new MessageUtils("campos erroneos"), HttpStatus.BAD_REQUEST);

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		JwtToken jwtToken = new JwtToken(token);

		return new ResponseEntity<JwtToken>(jwtToken, HttpStatus.OK);
	}

	@PostMapping("/createUser")
	public ResponseEntity<?> newUser(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult) {

		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new MessageUtils("campos mal puestos"), HttpStatus.BAD_REQUEST);

		if (userService.existsByUsername(newUserDto.getUsername()))
			return new ResponseEntity<>(new MessageUtils("ese nombre ya existe"), HttpStatus.BAD_REQUEST);

		if (userService.existsByEmail(newUserDto.getEmail()))
			return new ResponseEntity<>(new MessageUtils("ese email ya existe"), HttpStatus.BAD_REQUEST);

		UserVO userVO = new UserVO(newUserDto.getUsername(), passwordEncoder.encode(newUserDto.getPassword()),
				newUserDto.getEmail());
		Set<RoleVO> roles = new HashSet<>();
		roles.add(roleService.findByRoleName(RoleName.ROLE_USER).get());

		if (newUserDto.getRoles().contains("admin"))
			roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN).get());
		userVO.setRoles(roles);
		userService.save(userVO);

		return new ResponseEntity<>(new MessageUtils("usuario guardado"), HttpStatus.CREATED);
	}
	
	  @PostMapping("/refresh-token")
	    public ResponseEntity<JwtToken> refresh(@RequestBody JwtToken jwtToken) throws ParseException {
	        String token = jwtProvider.refreshToken(jwtToken);
	        JwtToken jwt = new JwtToken(token);
	        return new ResponseEntity(jwt, HttpStatus.OK);
	    }
}
