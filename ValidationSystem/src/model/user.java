package model;

import annotations.range;
import annotations.required;

public class user
{
	@required
	private String name;
	
	@range(min = 0, max = 120)
	private int age;
	
	public user(String name, int age)
	{
		this.name = name;
		this.age = age;
	}
}

