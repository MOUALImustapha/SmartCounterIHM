package com.inovation.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Login implements Serializable{
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	public Login(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	
	
}
