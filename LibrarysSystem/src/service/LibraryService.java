package service;

import java.util.ArrayList;
import java.util.List;

import model.Book;

//サービス層を表すクラス
public class LibraryService
{
	//図書館にある書籍の一覧を保持するリスト
	private List<Book> collection = new ArrayList<>();
	
	//書籍をリストに追加
	public void addBook(Book book)
	{
		collection.add(book);
	}
	
	//キーワードと一致するか検索
	public List<Book> search(String keyword)
	{
		List<Book> result = new ArrayList<>();
		for(Book b : collection)
		{
			if(b.matches(keyword))
			{
				result.add(b);
			}
		}
		return result;
	}
	
	//指定された書籍を借りる
	public boolean borrowBook(String title)
	{
		for(Book b : collection)
		{
			if(b.title.equals(title) && !b.isBorrowed())
			{
				b.borrow();
				return true;
			}
		}
		return false;
	}
	
	//指定された書籍を返却する
	public boolean returnBook(String title)
	{
		for(Book b : collection)
		{
			if(b.title.equals(title) && b.isBorrowed())
			{
				b.returnBook();
				return true;
			}
		}
		return false;
	}
}
