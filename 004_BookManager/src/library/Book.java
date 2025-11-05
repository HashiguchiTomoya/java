package library;

import Inface.Borrowable;
import Inface.Searchable;

public abstract class Book implements Searchable, Borrowable
{
	//継承したクラスからアクセスを許可
	protected String title;			//タイトル
	protected Author author;		//作者
	protected Publisher publisher;	//出版社
	protected boolean borrowed;	//借りられるか判定
	
	//コンストラクタの定義
	public Book(String title, Author author, Publisher publisher)
	{
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.borrowed = false;
	}
	
	
	@Override
	//march再定義し処理を上書き
	public boolean match(String keyword)
	{
		//タイトルか著者名のいずれかが検索ワードに含まれればtureを返す
		return title.contains(keyword) || author.getName().contains(keyword);
	}
	
	@Override
	//borrowを再定義し処理を上書き
	public boolean borrow()
	{
		//現在借りられていない場合
		if(!borrowed)
		{
			//代入してtrueを返す(借りられていない)
			borrowed = true;
			return true;
		}
		//falseを返す(借りられていた)
		return false;
	}
	
	@Override
	//returnBookを再定義し処理を上書き
	public boolean returnBook()
	{
		//借りられていた場合
		if(borrowed)
		{
			//代入してtrueを返す(返却された)
			borrowed = false;
			return true;
		}
		//falseを返す(借りられていなかった)
		return false;
	}
	
	@Override
	//isBorrowedを再定義し処理を上書き
	public boolean isBorrowed()
	{
		return borrowed;
	}
	
	//ゲッター
	public String getTitle()
	{
		return title;
	}
}


