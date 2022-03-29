package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.Admin;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	@JsonIgnore
	private String userpassword;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(Long id,String username, String mob,String userpassword,String email,
			Collection<? extends GrantedAuthority> authorities) {
		this.id=id;
		this.username=username;
		this.userpassword=userpassword;
	}
	public static UserDetailsImpl build(Admin admin) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		return new UserDetailsImpl(admin.getId(),admin.getUsername(), admin.getUserpassword(),admin.getMob(),admin.getEmail(),
				authorities);
	}
	public Long getId() {
		return id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) obj;
		return Objects.equals(id, user.id);
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

}
