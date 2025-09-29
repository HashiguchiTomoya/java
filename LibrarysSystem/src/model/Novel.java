package model;

//小説ジャンルの書籍クラス
public class Novel extends Book
{
	//ジャンル小説を定義
	public Novel(String title, Author author, Publisher publisher)
	{
		super(title, author, publisher);
	}
	
	public String getCategory()
	{
		return "小説";
	}

	@Override
	public boolean matchs(String keyword) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
