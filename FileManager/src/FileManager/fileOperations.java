package FileManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class fileOperations
{
	//ファイルの一覧表示
	public static void listFiles(Scanner scanner)
	{
		System.out.println("表示するディレクトリのパス：");
		String path = scanner.nextLine();
		File dir = new File(path);
		
		//ディレクトリの存在と妥当性チェック
		if(dir.exists() && dir.isDirectory())
		{
			File[] files = dir.listFiles();
			for(File f : files)
			{
				//ファイル名とサイズを表示
				System.out.printf("%s - %d bytes%n", f.getName(), f.length());

			}
		}
		else
		{
			System.out.println("有効なディレクトリではありません。");
		}
		
		scanner.close();
	}
	
	//ファイルのコピー
	public static void copyFile(Scanner scanner)
	{
		System.out.println("コピー元のファイルパス：");
		Path source = Paths.get(scanner.nextLine());
		System.out.println("コピー先のファイルパス：");
		Path target = Paths.get(scanner.nextLine());
		
		try
		{
			//ファイルコピー（っ既存の場合は上書き）
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("コピー完了！");
		}
		catch(IOException e)
		{
			System.out.println("コピー失敗：" + e.getMessage());
		}
		
		scanner.close();
	}
	
	//ファイルの移動
	public static void moveFile(Scanner scanner)
	{
		System.out.println("移動元のファイルパス：");
		Path sourse = Paths.get(scanner.nextLine());
		System.out.println("移動先のファイルパス：");
		Path target = Paths.get(scanner.nextLine());
		
		try
		{
			Files.move(sourse, target, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("移動完了！");
		}
		catch(IOException e)
		{
			System.out.println("移動失敗：" + e.getMessage());
		}
		
		scanner.close();
	}
	
	//ファイルの削除
	public static void deleteFile(Scanner scanner)
	{
		System.out.println("削除するファイルのパス：");
		Path path = Paths.get(scanner.nextLine());
		
		try
		{
			Files.delete(path);
			System.out.println("削除完了！");
		}
		catch(IOException e)
		{
			System.out.println("削除失敗：" + e.getMessage());
		}
		
		scanner.close();
	}
	
	//テキストファイルの表示
	public static void readText(Scanner scanner)
	{
		System.out.println("表示するテキストファイルのパス：");
		Path path = Paths.get(scanner.nextLine());
		
		try
		{
			Files.lines(path).forEach(System.out::println);
		}
		catch(IOException e)
		{
			System.out.println("読み込み失敗：" + e.getMessage());
		}
		
		scanner.close();
	}
	
	//ファイルを編集
	public static void editText(Scanner scanner)
	{
		System.out.println("編集するファイルパス：");
		Path path = Paths.get(scanner.nextLine());
		System.out.println("新しい内容（終了するには空行入力）：");
		
		try(BufferedWriter writer = Files.newBufferedWriter(path))
		{
			String line;
			while(!(line = scanner.nextLine()).isEmpty())
			{
				writer.write(line);
				writer.newLine();
			}
			System.out.println("編集完了！");
		}
		catch(IOException e)
		{
			System.out.println("編集失敗：" + e.getMessage());
		}
		
		scanner.close();
	}
	
	//検索
	public static void searchFiles(Scanner scanner)
	{
		System.out.println("検索対象ディレクトリ：");
		File dir = new File(scanner.nextLine());
		System.out.println("名前/拡張子/サイズの条件を入力：");
		String keyword = scanner.nextLine().toLowerCase();
		
		if(dir.exists() && dir.isDirectory())
		{
			for(File file : dir.listFiles())
			{
				boolean match = file.getName().toLowerCase().contains(keyword) || keyword.equalsIgnoreCase(getExtension(file)) || keyword.equals(String.valueOf(file.length()));
				if(match)
				{
					System.out.println("一致：" + file.getAbsolutePath());
					
				}
			}
		}
		else
		{
			System.out.println("有効なディレクトリではありません");
		}
	}
	
	private static String getExtension(File file)
	{
		String name = file.getName();
		int lastDot = name.lastIndexOf('.');
		return lastDot == -1 ? "" : name.substring(lastDot + 1);
	}
}
