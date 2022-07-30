package com.spring.mongo.demo.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;




/**
 * 
 * @author Vish
 *
 */
public class LocalUser extends User  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2845160792248762779L;
	private com.spring.mongo.demo.model.User user;
	private Map<String, Object> attributes;

	public LocalUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}


	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public com.spring.mongo.demo.model.User getUser() {
		return user;
	}
}
