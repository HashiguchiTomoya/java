package model;

import java.util.List;

public class FindCommentLogic
{
	//list型のBoardを返すメソッド
	public List<Board> executeFindComment(String keyword, String sortOrder, int limit, int offset)
	{
		//データベースアクセスを伴うFindCommentDAOクラスの新しいインスタンス
		FindCommentDAO fcdao = new FindCommentDAO();
		//findcomment()メソッドを呼び出し、データベースからコメントのリストを取得し、listに格納
		List<Board> list = fcdao.findcomment(keyword, sortOrder, limit, offset);
		//listを返す
		return list;
	}
}
