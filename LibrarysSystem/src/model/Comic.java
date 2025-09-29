package model;

//漫画ジャンルの書籍クラス
public class Comic extends Book
{
	//Bookを継承しジャンル漫画を定義
	public Comic(String title, Author author, Publisher publisher)
	{
		super(title, author, publisher);
	}
	
	public String GetCategory()
	{
		return "漫画";
	}

	@Override
	public boolean matchs(String keyword) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public String getCategory() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
