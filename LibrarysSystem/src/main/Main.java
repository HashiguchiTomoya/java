package main;

import model.Author;
import model.Book;
import model.Comic;
import model.Novel;
import model.Publisher;
import service.LibraryService;

//テスト
public class Main
{
	public static void main(String[] args)
	{
		//追加・検索・貸出・返却を管理するインスタンスの作成
		LibraryService library = new LibraryService();
		
		//書籍情報の作成と登録
		Author a1 = new Author("夏目漱石", "日本の文豪");
		Publisher p1 = new Publisher("岩波書店");
		Book b1 = new Novel("吾輩は猫である", a1, p1);
		Book b2 = new Comic("ワンピース", new Author("尾田栄一郎", "日本の漫画家"), new Publisher("集英社"));
		
		library.addBook(b1);
		library.addBook(b2);
		
		//キーワード検索の実行
		System.out.println("検索結果：");
		for(Book b : library.search("猫"))
		{
			System.out.println(b.title + " - " + b.getCategory());
		}
		
		//貸出処理
		System.out.println("貸出：");
		if(library.borrowBook("吾輩は猫である"))
		{
			System.out.println("貸出成功");
		}
		
		//返却処理
		System.out.println("返却");
		if(library.returnBook("吾輩は猫である"))
		{
			System.out.println("返却完了");
		}
	}
}
