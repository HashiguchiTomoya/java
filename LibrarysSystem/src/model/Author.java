package model;

//名前と略歴を保持するクラス
public class Author
{
	//外部から取得可能
	private String name;
	private String bio;
	
	public Author(String name, String bio)
	{
		this.name = name;
		this.bio = bio;
	}
	
	public String getName() {return name;}
	public String getBio() {return bio;}
}
