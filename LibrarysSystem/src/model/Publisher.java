package model;

//出版社を保持するクラス
public class Publisher
{
	private String name;
	
	public Publisher(String name)
	{
		this.name = name;
	}
	
	//外部から取得可能
	public String getName() {return name;}
}
