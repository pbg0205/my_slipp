package net.slipp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity //해당 class를 Entity(table)로 사용하겠다는 의미 
public class User {
	@Id //primary_key로 설정
	@GeneratedValue //database에서 자동으로 값을 1씩 증가시키는 역할
	private Long id;
	
	//(1) nullable = false : not null 설정 (2) length = 20 : id 문자열 길이
	@Column(nullable = false, length = 20) 
	private String userId;
	private String password;
	private String name;
	private String email;
	
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}

	public void update(User updateUser) {
		this.userId = updateUser.userId;
		this.password = updateUser.password;
		this.name = updateUser.name;
		this.email = updateUser.email;
		
	}
}
