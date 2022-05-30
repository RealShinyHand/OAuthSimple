package com.realshinyhand.springtest.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column(name="name",nullable = false)
	private String name;
	
	@Column(name="email",nullable = false,unique = true)
	private String email;

	public User(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	
}
