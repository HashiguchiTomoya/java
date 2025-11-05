package fileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileManager
{
	public static void main(String[] args) throws IOException
	{
		//選択した値を代入
		int select = 0;
		Scanner sr = new Scanner(System.in);
		
		
		System.out.println("----ファイル管理システム----");
		
		//8が入力されるまで無限ループ
		while(true)
		{
			System.out.println("操作を選択してください。");
			System.out.println("1.ファイルの一覧表示");
			System.out.println("2.ファイルのコピー");
			System.out.println("3.ファイルの移動");
			System.out.println("4.ファイルの削除");
			System.out.println("5.テキストファイルの内容表示");
			System.out.println("6.テキストファイルの編集");
			System.out.println("7.検索");
			System.out.println("8.終了");
			
			//入力された値がint型かを判定
			if(sr.hasNextInt())
			{
				//int型の場合は代入
				select = sr.nextInt();
			}
			else
			{
				//int型以外の場合に表示
				System.out.println("値が正しくありません。");
			}
			
			//8が入力された場合の処理
			if(select == 8)
			{
				System.out.println("終了します。");
				break;
			}
			
			//一致するケースのコードを実行
			switch(select)
			{
			//一覧表示
			case 1:
				System.out.println("--一覧表示--");
				//カレントディレクトリへのパスを表すFileのインスタンスを作成
				File dirList = new File(".");
				//一覧を表示
				show(dirList, "");
				break;
				
			//コピー
			case 2:
				//既存のディレクトリ内のパスを指定
				Path test = Paths.get("test", "test.txt");
				//指定したファイルがあるかを判定
				if(Files.exists(test) == false)
				{
					//ない場合は新しく作る
					Files.createFile(test);
				}
				//Pathオブジェクトの生成
				Path outFile = Paths.get("test", "test.txt");
				//コピーする
				Files.copy(test, outFile);
				System.out.println("test.txtのコピーを作成しました。");
				break;
			
			//移動
			case 3:
				//既存のディレクトリ内のパスを指定
				Path moveTest = Paths.get("test", "test.txt");
				//指定したファイルがあるか判定
				if(Files.exists(moveTest) == false)
				{
					//ない場合は新しく作る
					Files.createFile(moveTest);
				}
				//Pathオブジェクトの生成
				Path moveFile = Paths.get("test", "test.txt");
				//コピーする
				Files.copy(moveTest, moveFile);
				
				//既存のパスを指定
				Path mvTest = Paths.get("test", "test1");
				//mvTestのパスにtest.txtのパスを連結させる
				Path dest = mvTest.resolve(Paths.get("test.text"));
				//moveTestで指定したファイルをdestで指定した場所に移動させる
				Files.move(moveTest, dest);
				System.out.println("test.txtをtest1に移動させました。");
				break;
				
			//削除
			case 4:
				//既存のパスを指定
				Path delText = Paths.get("test", "test.txt");
				//指定したファイルの削除
				Files.delete(delText);
				System.out.println("test.txtを削除しました");
				break;
				
			//テキストファイルの内容を表示
			case 5:
				System.out.println("test.txtの内容を表示します。");
				//既存のパスを指定
				Path display = Paths.get("test.txt");
				//BufferedReaderオブジェクトを作成し、brに代入する
				BufferedReader br = Files.newBufferedReader(display);
				try(br)
				{
					//一行ずつ読み込み表示
					br.lines().forEach(System.out::println);
				}
				break;
				
				
			//テキストファイルの編集
			case 6:
				String addword = "";
				//既存のパスを指定
				Path writeText = Paths.get("test.txt");
				//BufferedWriterオブジェクトを作成し、bwに代入する
				BufferedWriter bw = Files.newBufferedWriter(writeText);
				//追加する文字列を取得
				System.out.println("追加する文章を入力してください。");
				addword = sr.nextLine();
				//受け取った文字列を書き込む
				bw.write(addword);
				//ファイルの書き込み位置に適した改行コードを挿入
				bw.newLine();
				System.out.println("文章を追加しました。");
				break;
				
				//検索
			case 7:
				//既存のパスを指定
				Path search = Paths.get("test");
				//検索する拡張子
				String extension = ".txt";
				//指定したディレクトリ(search)内に特定の拡張子を持つファイルを検索して、searchFileに格納
				List<Path> searchFile = findFiles(search, extension);
				
				//見つかったファイルを表示
				for(Path file : searchFile)
				{
					System.out.println(file.toString());
				}
				break;
				
			default:
				System.out.println("無効な値です。");
				break;
			}
		}
		sr.close();
	}
	
	//showメソッドの宣言
	public static void show(File dir, String indent)
	{
		//ファイルやディレクトリの一覧を取得してfiles変数に格納
		File[] files = dir.listFiles();
		//filesの中を１つずつ順番に見ていきfileという名前で同じ処理を繰り返す
		for(File file : files)
		{
			//getNameメソッドを使用して、ファイル名やディレクトリ名を表示
			System.out.println(indent + file.getName());
			//ディレクトリまたはファイルかを判定
			if(file.isDirectory())
			{
				//再帰的に階層構造で表示する(indent破戒僧の深さを表現する)
				show(file, indent + " ");
			}
		}
	}
	
	public static List<Path> findFiles(Path dir, String extension) throws IOException
	{
		//walkメソッドでディレクトリツリーを走査
		return Files.walk(dir).filter(Files::isRegularFile)		//ファイルのみを対象
				.filter(path -> path.toString().endsWith(extension))//拡張子が一致するかチェック
				.collect(Collectors.toList());						//結果をリストに収集
	}
}

