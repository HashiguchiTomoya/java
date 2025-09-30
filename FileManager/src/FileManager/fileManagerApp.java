package FileManager;

import java.util.Scanner;

public class fileManagerApp
{
	int choice = 0;
	//入力受付
	
	Scanner scanner = new Scanner(System.in);
	
	public void start()
	{
		while(true)
		{
			//メニュー表示
			System.out.println("===File Manager メニュー ===");
			System.out.println("1.ファイル一覧表示");
			System.out.println("2.ファイルのコピー");
			System.out.println("3.ファイルの移動");
			System.out.println("4.ファイルの削除");
			System.out.println("5.テキストの表示");
			System.out.println("6.テキストの編集");
			System.out.println("7.ファイルの検索");
			System.out.println("8.終了");
			System.out.println("選択してください");
			
			choice = scanner.nextInt();
			
			//選択に応じた処理分岐
			switch(choice)
			{
			case 1 -> fileOperations.listFiles(scanner);
			case 2 -> fileOperations.copyFile(scanner);
			case 3 -> fileOperations.moveFile(scanner);
			case 4 -> fileOperations.deleteFile(scanner);
			case 5 -> fileOperations.readText(scanner);
			case 6 -> fileOperations.editText(scanner);
			case 7 -> fileOperations.searchFiles(scanner);
			case 8 ->
				{
					System.out.println("終了します");
					return; //ループの終了
				}
			default -> System.out.println("無効な値です");
			}
			
			scanner.close();
		}
	}
}
