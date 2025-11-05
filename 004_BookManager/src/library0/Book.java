package library0;

public abstract class Book
{
	//タイトル
	String title = "";
	protected Author author;
	protected Publisher publisher;
	
	//どのクラスからのアクセス可能
	public Book(String title, Author author, Publisher publisher)
	{
		this.title = title;
		this.author = author;
		this.publisher = publisher;
	}
	
	public abstract String getKinds();
}

class Novel extends Book
{
	//スーパークラスの呼び出し
	public Novel(String title, Author author, Publisher publisher)
	{
		super(title, author, publisher);
	}

	//親クラスに値を渡す
	@Override
	public String getKinds()
	{
		return "小説";
	}

	
}