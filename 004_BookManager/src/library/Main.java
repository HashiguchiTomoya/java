package library;

public class Main
{
	public static void main(String[] args)
	{
		//新しいAuthorオブジェクトを作成
		Author author = new Author("芥川龍之介");
		//新しいpublisherオブジェクトを作成
		Publisher publisher = new Publisher("青空書店");
		
		//新しいnovelオブジェクトの作成
		Book novel = new Novel("羅生門",author, publisher, "文学");
		
		//新しいLibraryオブジェクトを作成
		Library ly = new Library();
		//Library内のrefisterBookメソッドを呼び出してnovelを引数として渡す
		ly.registerBook(novel);
		
		System.out.println("検索結果：");
		//Library内のsearchメソッドを呼び出して門で検索
		for(Book b : ly.search("門"))
		{
			//検索結果の表示
			System.out.println(b.getTitle());
		}
		//borrowBookを呼び出して貸出処理をし成功したかを表示
		System.out.println("貸出成功：" + ly.borrowBook("羅生門"));
		//returnBookを呼び出して返却処理をし成功したかを表示
		System.out.println("返却成功：" + ly.returnBook("羅生門"));
	}
}
