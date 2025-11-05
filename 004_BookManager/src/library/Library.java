package library;

import java.util.ArrayList;
import java.util.List;

public class Library
{
	//Booksリストの作成
	List<Book> books = new ArrayList<>();
	
	//本の登録
	public void registerBook(Book book)
	{
		//booksリストに追加する
		books.add(book);
	}
	
	//検索
	public List<Book> search(String keyword)
	{
		//resultリストの作成(検索結果を格納)
		List<Book> result = new ArrayList<>();
		//booksリストの要素を井戸つずつ最後まで繰り返す
		for(Book book : books)
		{
			//キーワードに一致した場合
			if(book.match(keyword))
			{
				//resultリストに追加する
				result.add(book);
			}
		}
		//resultリストの要素を返す
		return result;
	}
	
	//借りる
	public boolean borrowBook(String title)
	{
		for(Book book : books)
		{
			//getTileで取得した要素とtitle変数一致かつ借りられていない場合
			if(book.getTitle().equals(title) && !book.isBorrowed())
			{
				//借りる(trueを返す)
				return book.borrow();
			}
		}
		//falseを返す
		return false;
	}
	
	//返す
	public boolean returnBook(String title)
	{
		for(Book book: books)
		{
			//getTileで取得した要素とtitle変数一致かつ借りられている場合
			if(book.getTitle().equals(title) && book.isBorrowed())
			{
				//返す(trueを返す)
				return book.returnBook();
			}
		}
		//falseを返す
		return false;
	}
}
