package com.app;

//現在の操作がマスターかスレーブのどちらを使うべきかを一時的に保存するためのクラス
public class DataSourceContextHolder
{
	//THreadLocal:スレッドごとに独立した変数を保持
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
	
	//現在のスレッドに対して使用すべきDBのキーを設定
	public static void setBranch(String branch)
	{
		contextHolder.set(branch);
	}
	
	//現在のスレッドに設定されているDBのキーを所得する
	//MyRoutingDataSourceのdetermineCurrentLookupkey()から呼び出される
	public static String getBranch()
	{
		return contextHolder.get();
	}
	
	//現在のスレッドに設定されているキーをクリアする
	public static void clearBranch()
	{
		contextHolder.remove();
	}
}
