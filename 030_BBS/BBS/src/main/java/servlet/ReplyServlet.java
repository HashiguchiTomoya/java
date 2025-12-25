package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("ReplyServlet")
public class ReplyServlet extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//どの党子に対する返信か(parentId)を取得
		String parentId = request.getParameter("parentId");
		//親のIDをリクエストスコープに保存して。入力画面へ
		request.setAttribute("parentId", parentId);
		//返信用入力画面を表示
		request.getRequestDispatcher("/WEB-INF/jsp/reply.jsp").forward(request, response);
	}
}
