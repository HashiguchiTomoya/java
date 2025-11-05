package loggerSystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerSystem
{
	public static void main(String[] args)
	{
		//loggerの生成
		Logger lr = Logger.getLogger(LoggerSystem.class.getName());
		
		try
		{
			//ハンドラーの生成(true:書き込み false:上書き)
			FileHandler fhr = new FileHandler("test.log", false);
			//simplefotmatによるlogフォーマットの指定
			fhr.setFormatter(new SimpleFormatter());
			//ハンドラーの設定
			lr.addHandler(fhr);
			//ログレベルの指定(ALL:全て)
			lr.setLevel(Level.CONFIG);
			
			//loggerクラスのメソッドを使ってメッセージを出力
			lr.finest("FT");	//最も詳細
			lr.finer("FR");		//詳細
			lr.fine("FE");		//普通
			lr.config("CG");	//構成
			lr.info("IO");		//情報
			lr.warning("WG");	//警告
			lr.severe("SR");	//重大
			
			//例外をスロー
			
			throw new IOException();
		}
		//指定したファイルが見つからない、又はアクセスできない時の例外
		catch(FileNotFoundException e)
		{
			//例外が発生するまでの経路を出力
			e.printStackTrace();
		}
		//プログラムがセキュリティ状の制限に違反する操作を試みた時の例外
		catch(SecurityException e)
		{
			e.printStackTrace();
		}
		//入出力の失敗や割り込み発生による例外
		catch(IOException e)
		{
			e.printStackTrace();
			lr.log(Level.CONFIG, "エラー発生", e);
			lr.warning("WG:エラー発生");
		}
	}
}