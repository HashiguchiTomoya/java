package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindCommentDAO
{
	// 接続情報
	final String jdbcId = "id";
	final String jdbcPass = "password";
	final String jdbcUrl = "jdbc:mysql://localhost:3306/BulletiBoard";

	//1. 検索・一覧取得メソッド
	public List<Board> findcomment(String keyword, int limit, int offset)
	{
		List<Board> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			con = DriverManager.getConnection(jdbcUrl, jdbcId, jdbcPass);
			
			// 動的SQLの構築
			StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM board ");
			
			// 検索条件
			if (keyword != null && !keyword.isEmpty())
			{
                sqlBuilder.append(" WHERE name LIKE ? OR comment LIKE ? ");
			}
			
			// ページネーション
			sqlBuilder.append(" LIMIT ? OFFSET ? ");	
			
			ps = con.prepareStatement(sqlBuilder.toString());
			
			int paramIndex = 1;
			if (keyword != null && !keyword.isEmpty())
			{
				String searchPattern = "%" + keyword + "%";
				ps.setString(paramIndex++, searchPattern);
				ps.setString(paramIndex++, searchPattern);
			}
			ps.setInt(paramIndex++, limit);
			ps.setInt(paramIndex++, offset);

			rs = ps.executeQuery();

			while (rs.next())
			{
				Board bo = new Board();
				bo.setId(rs.getInt("id"));
				bo.setName(rs.getString("name"));
				bo.setComment(rs.getString("comment"));
				bo.setTime(rs.getTimestamp("time"));
				// 返信機能用のフィールドもセット
				bo.setParentId(rs.getInt("parent_id"));
				bo.setThreadLevel(rs.getInt("thread_level"));
				list.add(bo);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			closeResources(rs, ps, con);
		}
		
		return list;
	}

    //2. 総件数取得メソッド (ページネーション計算用)
	public int countComments(String keyword)
	{
		int count = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			con = DriverManager.getConnection(jdbcUrl, jdbcId, jdbcPass);
			String sql = "SELECT COUNT(*) FROM board";
			if (keyword != null && !keyword.isEmpty())
			{
				sql += " WHERE name LIKE ? OR comment LIKE ?";
			}
			ps = con.prepareStatement(sql);
			if (keyword != null && !keyword.isEmpty())
			{
				String pattern = "%" + keyword + "%";
				ps.setString(1, pattern);
				ps.setString(2, pattern);
			}
			rs = ps.executeQuery();
			if (rs.next()) count = rs.getInt(1);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeResources(rs, ps, con);
		}
		
		return count;
	}

	//3. 内部クラス BoardDAO (追加・削除・編集)
	public class BoardDAO
	{
		public void addComment(Board bo)
		{
			Connection con = null;
			PreparedStatement ps = null;
			try
			{
				con = DriverManager.getConnection(jdbcUrl, jdbcId, jdbcPass);
				// VALUESのスペルとカラム名を修正
				String sql = "INSERT INTO board(name, comment, parentId, threadLevel) VALUES (?, ?, ?, ?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, bo.getName());
				ps.setString(2, bo.getComment());
				ps.setInt(3, bo.getParentId());
				ps.setInt(4, bo.getThreadLevel());
				ps.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				closeResources(null, ps, con);
			}
		}

		public void deleteComment(int id)
		{
			Connection con = null;
			PreparedStatement ps = null;
			try
			{
				con = DriverManager.getConnection(jdbcUrl, jdbcId, jdbcPass);
				ps = con.prepareStatement("DELETE FROM board WHERE id = ?");
				ps.setInt(1, id);
				ps.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				closeResources(null, ps, con);
			}
		}

		public void updateComment(Board bo)
		{
			Connection con = null;
			PreparedStatement ps = null;
			try
			{
				con = DriverManager.getConnection(jdbcUrl, jdbcId, jdbcPass);
				// SET name = ? の '=' 抜けを修正
				String sql = "UPDATE board SET name = ?, comment = ? WHERE id = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, bo.getName());
				ps.setString(2, bo.getComment());
				ps.setInt(3, bo.getId());
				ps.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				closeResources(null, ps, con);
			}
		}
	}

    // 共通のクローズ処理
	private void closeResources(ResultSet rs, PreparedStatement ps, Connection con)
	{
		try
		{
			if (rs != null)
			{
				rs.close();
			}
			if (ps != null)
			{
				ps.close();
			}
			if (con != null)
			{
				con.close();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
