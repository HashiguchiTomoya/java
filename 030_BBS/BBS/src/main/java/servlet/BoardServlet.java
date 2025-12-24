package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Board;
import model.FindCommentDAO;
import model.FindCommentLogic;

@WebServlet("/BoardServlet")
public class BoardServlet extends HttpServlet
{
	//シリアライズ(直列化)においてのクラスバージョンの管理
	//オブジェクトが元のクラスと互換性があるかを識別する
	private static final long serialVersionUID = 1L;
	
	//親クラス(HttpServlet)のコンストラクタを呼び出す
	public BoardServlet()
	{
		super();
	}
	
	//HTTPGETリクエストを処理するメソッドをオーバーライド
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//リクエストおあらメーターの文字エンコーディングを指定
		request.setCharacterEncoding("UTF-8");
		
		//管理者権限のテスト用、テスト時に有効にする
		//HttpSession session = request.getSession();
		//session.setAttribute("isAdmin", true);
		
		//検索、ソート、ページネーションのパラメータ取得
		String keyword = request.getParameter("keyword");
		String sort = request.getParameter("sort");
		int page = request.getParameter("page") == null ? 1: Integer.parseInt(request.getParameter("page"));
		int limit = 20;
		int offset = (page -1) * limit;
		
		//FindCommentLogicをインスタンス化してコメントリストを取得
		FindCommentLogic fcl = new FindCommentLogic();
		List<Board> list = fcl.executeFindComment(keyword, sort, limit,offset);

		//HttpSessionを取得して取得したコメントリストをkistAttributeという名前でセッションスコープに保存
		//JSPページで表示するために使用
		HttpSession session = request.getSession();
		session.setAttribute("listAttribute", list);
		
		//表示先のJSPファイルを取得してリクエストとレスポンスを転送して画面に表示
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
		rd.forward(request, response);
	}
	
	//HTTPPOSTリクエストを処理す露ためのメソッドをオーバーライド
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		
		//どのアクションを行うか取得
		String action = request.getParameter("action");
		
		//インスタンス化
		FindCommentDAO fdao = new FindCommentDAO();
		FindCommentDAO.BoardDAO bdao = fdao.new BoardDAO();
		
		try
		{
			if("add".equals(action))
			{
				String name = request.getParameter("name");
				String comment = request.getParameter("comment");
				String parentIdStr = request.getParameter("parentId");
				String levelStr = request.getParameter("threadLevel");
				
				int parentId = (parentIdStr != null) ? Integer.parseInt(parentIdStr) : 0;
				int level = (levelStr != null) ? Integer.parseInt(levelStr) : 0;
				
				Board bo = new Board();
				bo.setName(name);
				bo.setComment(comment);
				bo.setParentId(parentId);
				bo.setThreadLevel(level);
				
				bdao.addComment(bo);
			}
			else if("delete".equals(action) && isAdmin(request))
			{
				String idStr = request.getParameter("id");
				if(idStr != null)
				{
					int id = Integer.parseInt(idStr);
					bdao.deleteComment(id);
				}
			}
			else if("edit".equals(action) && isAdmin(request))
			{
				String idStr = request.getParameter("id");
				String name = request.getParameter("name");
				String comment = request.getParameter("comment");
				
				if(idStr != null)
				{
					Board bo = new Board();
					bo.setId(Integer.parseInt(idStr));
					bo.setName(name);
					bo.setComment(comment);
					bdao.updateComment(bo);
				}
			}
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath() + "/BoardServlet");
	}
	
	//管理者権限があるかどうか判定
	private boolean isAdmin(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if(session == null)
		{
			return false;
		}
		//セッションにisAdminという属性がtrueで格納されているかチェック
		Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
		return isAdmin != null && isAdmin;
	}
}
