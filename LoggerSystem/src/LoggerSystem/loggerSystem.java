package LoggerSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

//ログ記録
public class loggerSystem
{
	//ログの重要度を定義
	public enum LogLevel {DEBUG, INFO, WARN, ERROR}
	
	private LogLevel level;
	private String logFile;
	private int maxSize;
	private int backupCount;
	
	//外部設定ファイル
	public loggerSystem(String configFile) throws IOException
	{
		loadConfig(configFile);
	}
	
	//設定ファイルを読み込み
	private void loadConfig(String configFile) throws IOException
	{
		Properties props = new Properties();
		
		try(FileInputStream fis = new FileInputStream(configFile))
		{
			props.load(fis);
		}
		level = LogLevel.valueOf(props.getProperty("log.level", "INFO"));
		logFile = props.getProperty("log.file", "app.log");
		maxSize = Integer.parseInt(props.getProperty("log.max.size", "10240"));
		backupCount = Integer.parseInt(props.getProperty("log.backup.count", "3"));
	}
	
	//指定したログファイルとメッセージを出力
	public void log(LogLevel msgLevel, String message)
	{
		if(msgLevel.ordinal() < level.ordinal()) return;
		
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String formatted = String.format("[%s] [%s] %s%n", timestamp, msgLevel, message);
		
		try
		{
			rotateIfNeeded();
			try(FileWriter fw = new FileWriter(logFile, true))
			{
				fw.write(formatted);
			}
		}
		catch(IOException e)
		{
			System.err.println("ログ書き込み失敗：" + e.getMessage());
		}
	}
	
	//新しいファイルに切り替え
	private void rotateIfNeeded()
	{
		File file = new File(logFile);
		if(!file.exists() || file.length() < maxSize) return;
		
		for(int i = backupCount -1; i >= 1; i--)
		{
			File src = new File(logFile + "." + i);
			File dest = new File(logFile + "." + (i + 1));
			if(src.exists()) src.renameTo(dest);
		}
		
		File current = new File(logFile);
		File firstBackup = new File(logFile + ".1");
		current.renameTo(firstBackup);
	}
	
	//簡易テスト
	public static void main(String[] args) throws IOException
	{
		loggerSystem logger = new loggerSystem("logger.properties");
		logger.log(LogLevel.DEBUG, "これはデバックメッセージです");
		logger.log(LogLevel.INFO, "情報メッセージ");
		logger.log(LogLevel.WARN, "警告メッセージ");
		logger.log(LogLevel.ERROR, "エラーメッセージ");
	}
}
