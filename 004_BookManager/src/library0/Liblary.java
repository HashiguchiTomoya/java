package library0;

public class Liblary
{
	
	public static void main(String[] args)
	{
		Book b1 = new Novel("羅生門", new Author("芥川龍之介"),new Publisher("青空文庫"));
		
		System.out.printf("%s %s %s \n", b1.title, b1.author, b1.publisher );
	}
}
