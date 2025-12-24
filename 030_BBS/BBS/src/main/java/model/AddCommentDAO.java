package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCommentDAO
{
	//Boardオブジェクトを引数にとるコンストラクタの定義
	public AddCommentDAO(Board bo)
	{
		//もし名前フィールドが空だった場合
		if(bo.getName().isEmpty())
		{
			//デフォルト値として名無しをセット
			bo.setName("名無し");
		}
		//もしコメントフィールドが空だった場合
		if(bo.getComment().isEmpty())
		{
			//デフォルト値としてコメントなしをセット
			bo.setComment("コメントなし");
		}
		
		//データベース接続に必要な定数を定義
		final String jdbcId = "id";
		final String jdbcPass = "password";
		final String jdbcUrl ="jdbc:mysql://localhost:3306/BulletiBoard";
		
		Connection con = null;
		
		try
		{
			con = DriverManager.getConnection(jdbcId, jdbcPass, jdbcUrl);
			
			System.out.println("接続完了！");
			
			try
			{
				//パラメータ?を含むSQL挿入クリエをしよいしでPreparedStatementを生成
				PreparedStatement ps = con.prepareStatement("INSERT INTO board(name, comment) VALUES (?, ?)");
				
				//最初の?に名前(getName())2番目の?にコメント(getComment())の値を設定
				ps.setString(1, bo.getName());
				ps.setString(2, bo.getComment());
				
				//SQLのINSERT文を実行し、影響を受けた行数をrに格納
				int r = ps.executeUpdate();
				//もし0ではない場合
				if(r != 0)
				{
					System.out.println(r + "件の書き込みを追加しました。");
				}
				else
				{
					System.out.println("書き込みできませんでした。");
				}
				
				ps.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(con != null)
				{
					try
					{
						con.close();
					}
					catch(SQLException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("接続に失敗しました。");
		}
	}
}
