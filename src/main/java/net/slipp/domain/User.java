package net.slipp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity //해당 class를 Entity(table)로 사용하겠다는 의미 
public class User extends AbstractEntity{

	//(1) nullable = false : not null 설정 (2) length = 20 : id 문자열 길이
	@Column(nullable = false, length = 20, unique=true) 
	@JsonProperty
	private String userId;
	
	@JsonIgnore
	private String password;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String email;
	
	public boolean matchId(Long newId) {
		if(newId == null) {
			return false;
		}
		return newId.equals(getId());
	}
	
	public boolean matchPassword(String newPassword) {
		if(newPassword == null) {
			return false;
		}
		return newPassword.equals(password);
	}

	public void update(User updateUser) {
		this.userId = updateUser.userId;
		this.password = updateUser.password;
		this.name = updateUser.name;
		this.email = updateUser.email;
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
		return "User [" + super.toString() +", userId=" + userId + ", password=" + password + ","
				+ " name=" + name + ", email=" + email + "]";
	}
}
