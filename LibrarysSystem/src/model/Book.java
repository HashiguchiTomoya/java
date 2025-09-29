package model;

import Interface.Borrowable;
import Interface.Searchable;

//書籍の抽象クラス
public abstract class Book implements Searchable, Borrowable
{
	public String title;
	protected Author author;
	protected Publisher publisher;
	protected boolean borrowed;
	
	public Book(String title, Author author, Publisher publisher)
	{
		//フィールドを定義
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.borrowed = false;
	}
	
	//キーワードが含まれるか判定
	public boolean matches(String keyword)
	{
		return title.contains(keyword) || author.getName().contains(keyword);
	}
	
	//貸し出し状況の管理
	public boolean borrow()
	{
		if(!borrowed)
		{
			borrowed = true;
			return true;
		}
		return false;
	}
	public void returnBook()
	{
		borrowed = false;
	}
	public boolean isBorrowed()
	{
		return borrowed;
	}
	
	//ジャンルを返す
	public abstract String getCategory();
}
