<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>返信画面</title>
</head>
<body>
	<h2>返信を投稿する</h2>
	<form action="BoardServlet" method="post">
		<input type="hidden" name="action" value="add">
		
		<!-- 返信先とスレッドレベルを隠してフィールドで送信 -->
		<input type="hidden" name="parentId" value="${parentId}">
		<!-- 簡易的に1を固定、または親レベル+１ -->
		<input type="hidden" name="threadLevel" value="1">
		
		<p>名前：<input type="text" name="name"></p>
		<p>コメント：<br>
			<textarea name="comment" rows="5" cols="40"></textarea>
		</p>
		<p><input type="submit" value="返信"></p>
	</form>
	<p><a href="BoardServlet">掲示板に戻る</a></p>
</body>
</html>