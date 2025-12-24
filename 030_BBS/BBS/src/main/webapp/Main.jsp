<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script type="text/javascript">
	//関数の定義
	function check()
	{
		//変数の定義
		var flag = 0;

		//もしnameが空であればflagを1に設定
		if(document.form1.name.value == "")
		{
			flag = 1;
		}
		//nameが空ではなく、commentが空であればflagを1に設定
		else if(document.form1.comment.value == "")
		{
			flag = 1;
		}

		//flagが1の場合
		if(flag)
		{
			//アラートボックスを表示し関数をfalseで終了
			window.alert('名前とコメントを入力してください');
			return false;
		}
		else
		{
			//関数をtrueで終了
			return true;
		}
	}
</script>
<title>掲示板</title>
</head>
<body>
	<!--検索フォームの追加-->
	<form action="BoardServlet" method="get">
		検索キーワード：<input type="text" name="keyword">
		<input type="submit" value="検索">
	</form>
	<form action="BoardServlet" method="post" name="form1" onSubmit="return check()">
		<!--何の処理をするか判定するフィールド-->
		<input type="hidden" name="action" value="add">
		<p>名前：<input type="text" name="name"></p>
		<p>コメント：<br>
			<textarea name="comment" rows="5" cols="40"></textarea>
		</p>
		<p><input type="submit" value="送信"><input type="reset" value="リセット">
		</p>
	</form>
	
	<c:forEach var="list" items="${listAttribute}">
		<!--返信ネストの表示-->
		<p style="margin-left:{list.threadLevel * 20}px;">
			ID：<c:out value="${list.id}"/>
			名前：<c:out value="${list.name}"/>
			日付：<c:out value="${list.time}"><br>
		   <c:out value="${list.comment}" /><br>
		   
		   <!--返信リンクの追加-->
		   <a href="ReplyServlet?parentId=${list.id}">返信</a>
		   
		   <!--管理者機能の追加-->
		   <!--管理者セッションが存在する場合のみ表示する-->
		   <c:if test="${sessionScope.isAdmin}">
		   	<a href="EditServlet?id=${list.id}">編集</a>
		   	<a href="BoardServlet?action=delete&id=${list.id}">削除</a>
		   </c:if>
		</p>
	</c:forEach>
	
	<!--ページネーションUIの追加-->
	<!--totalPages属性とcurrentPage属性を使ってページリンクを生成-->
	<div class="pageination">
		<c:forEach var="i" begin="1" end="${totalPages}">
			<c:choose>
				<c:when test="${i == currentPage}">
					<strong>${i}</strong>
				</c:when>
				<c:otherwise>
					<a href="BoardServlet?page=${i}&keyword=${param.keywprd}">${i}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
</body>
</html>