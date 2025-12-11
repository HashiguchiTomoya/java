package com.app;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//このクラスがJPAによって管理されるエンティティであることを示す
@Entity
//このエンティティがusersテーブルにマッピングされることを示す
@Table(name = "users")
public class Users
{
	//userIdが主キーであることを示す
	@Id
	//主キーがデータベースによって自動的に生成されてい居ることを示す。
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	//テーブルのそれぞれの値を格納する
	private Long userId;
	
	private String username;
	
	private String password;
	
	private String role;
	
	//ゲッターとセッター
	public Long getId()
	{
		return userId;
	}	
	public void setId(Long userId)
	{
		this.userId = userId;
	}
	
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getRole()
	{
		return role;
	}
	public void setRole(String role)
	{
		this.role = role;
	}
}
