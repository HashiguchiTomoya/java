package com.app;

import java.util.HashMap;
import java.util.Map;

//標準javaのデータインターフェースをインポート
import javax.sql.DataSource;

//@Beanを使うため
import org.springframework.context.annotation.Bean;
//@Configurationを使うため
import org.springframework.context.annotation.Configuration;
//DBの物理的な接続を作成するための基本的なデータソース実装クラスをインポート
import org.springframework.jdbc.datasource.DriverManagerDataSource;
//実行時の特定の条件に元図いて、どのデータソースを使うかを決定
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

//Spriongコンテナに対して、アプリケーションの設定(Configuration)を定義するためのクラス
@Configuration
public class DataSourceConfig
{
	//masterDataSource()が返すオブジェクトをSpringの管理対象であるコンポーネント(Bean)として登録
	@Bean
	//DB接続を表現するインターフェースを実装したオブジェクトを返すメソッドの宣言
	public DataSource masterDataSource()
	{
		//DB接続を作成するためのオブジェクトを新しく生成する
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		//使用するDBの種類に対応したJDBCドライバーのクラスを指定
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		//DBの場所と接続先を指定するURL
		dataSource.setUrl("jdbc:mysql://<マスターIP>:3306/testdb");
		//DBに接続に使用するユーザー名
		dataSource.setUsername("user");
		//ユーザーのパスワード
		dataSource.setPassword("password");
		//設定済みのDataSourceオブジェクトを返す。
		return dataSource;
	}
	
	//slaveDataSource()が返すオブジェクトをSpringの管理対象であるコンポーネント(Bean)として登録
	@Bean
	public DataSource slaveDataSource()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://<スレーブIP>:3306/testdb");
		dataSource.setUsername("user");
		dataSource.setPassword("password");
		return dataSource;
	}
	
	
	@Bean
	public DataSource routingDataSource()
	{
		//AbstractRoutingDataSourceを継承して作成したカスタムクラスを生成
		MyRoutingDataSource routingDataSource = new MyRoutingDataSource();
		
		//利用可能な据えてのデータソースを格納するためのマップを作成
		Map<Object, Object> targetDataSources = new HashMap<>();
		//masterDataSourceメソッドを呼び出して、その結果をmasterというキーでマップに格納
		targetDataSources.put("master", masterDataSource());
		//slaveDataSourceメソッドを呼び出して、その結果をslaveというキーでマップに格納
		targetDataSources.put("slave", slaveDataSource());
		
		//作成したマスター祖スレーブのマップをルーティングデータソースインスタンスに設定、どの接続先が利用可能か認識
		routingDataSource.setDefaultTargetDataSource(masterDataSource());
		//ルーティングのキーが決定できなかった場合や、デフォルトでどちらを使用するかを指定
		routingDataSource.setDefaultTargetDataSource(masterDataSource());
		return routingDataSource;
	}
}

//マスター/スレーブのDBをどのように切り替えるかを定義
//AbstractRoutingDataSourceを継承
class MyRoutingDataSource extends AbstractRoutingDataSource
{
	//親クラス(AbstractRoutingDataSource)の抽象メソッドをオーバーライド
    @Override
    protected Object determineCurrentLookupKey()
    {
    		//現在の実行コンテキストに応じて、使用するDB接続のキーを返す
        return DataSourceContextHolder.getBranch();
    }
}
