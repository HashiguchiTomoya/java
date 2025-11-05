package library;

public  class Novel extends Book
{
	//ジャンル
	String genre;
	
	//コンストラクタの定義
	public Novel(String title, Author author, Publisher publisher,String genre)
	{
		super(title, author, publisher);
		this.genre = genre;
	}
}
