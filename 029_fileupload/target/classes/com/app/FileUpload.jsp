<!-- このコンテンツがHTMLテキストであることを宣言 -->
<%@ page contentType="text/html; charset=UTF-8" %>
<!-- JSP標準タグライブラリのコアライブラリを使用するための宣言 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- JSTLのフォーマットライブラリを使用可能にするための宣言 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- JSTLの関連ライブラリを使用可能にするための宣言 -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- JSPページ内でExplressionLanguage(EL)の評価を有効にする(falseは無視しない) -->
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>ファイルアップロード</title>
<!-- ウェブサイトのタブなどで使われる小さなアイコン(.ico)を設定 -->
<link rel="shortcut icon" href="<c:url value="/assets/favicon.ico" />">
<!-- Bpptstrapのスタイルシートを読み込む -->
<link rel="stylesheet" href="<c:url value="/assets/bootstrap-min.css" />">
<!-- BootstrapLconsアイコンフォトライブラリを読み込む(矢印名地が表示できる) -->
<link rel="stylesheet" href="<c:url value="/assets/bootstrap-icons.css" />">
<link rel="stylesheet" href="<c:url value="/assets/styles.css" />">
</head>
<body>
	<%@ include file="./_headerNavbar.jsp" %>
	
	<main>
		<!-- ファイルアップロードフォーム -->
		<!-- フォームが送信された時、サーバー側のupload_fileのURLにデータが送られる -->
		<div class="container workspace">
		
			<!-- エラーメッセージ表示エリア -->
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger" role="alert">
					${errorMessage}
				</div>
			</c:if>
			
			<form id="uploadForm" enctype="multipart/form-data">
				<label>ファイル　画像：</label>
				<input type="file" name="img" />
				<br/>
				<label>ファイル　音声：</label>
				<input type="file" name="audio" />
				<br/>
				<label>ファイル　動画：</label>
				<input type="file" name="video" />
				<br/>
				<!-- ボタンを配置 -->
				<input type="submit" value="アップロード開始" id="submitButton" />
			</form>
			
			<!-- 進捗表示 -->
			<!-- 進捗バーの外側の領域を定義(mt-3:上余白の設定、d-none:デフォルトで非表示) -->
			<div class="progress mt-3 d-none" id="uploadProgress">
				<!--進捗を表す内側のバーを定義(progress-bar-striped:ストライプ模様のデザインを定義、progress-bar-animated:アニメーション)  -->
				<div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar"
					<!-- バーの属性設定(aria-valuenow="0":現在値を送信、aria-valemin(max):最小値と最大値、style="width:0%"初期を0％に) -->
					id="progressBar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
					<!-- javaScriptで進捗率に応じて書き換えられる -->
					0%
				</div>
			</div>
			<hr/>
			<!-- c:if Test=:もしresultDtoオブジェクトのimgPathプロパティが空でなければ対応するHTML要素を表示 -->
			<div class="upload-previews">
				<div class="upload mb-3">
					<p>アップロードファイル：画像</p>
					<c:if test="${!empty resultDto.imgPath}">
						<div class="spinner-border d-none uploadFileLoading"></div>
						<!-- サーバーに渡された保存場所を示すパスを元に正しいURLを自動生成し、画面に表示 -->
						<img class="uploadFile" src="<c:url value="${resultDto.imgPath}" />"/>
					</c:if>
				</div>
				<hr/>
				<div class="upload mb-3">
					<p>アップロードファイル：音声</p>
					<c:if test="${!empty resultDto.audioPath}">
						<div class="spinner-border d-none uploadFileLoading"></div>
						<audio class="uploadFile" src="<c:url value="${resultDto.audioPath}" />" controls></audio>
					</c:if>
				</div>
				<hr/>
				<div class="upload mb-3">
					<p>アップロードファイル：動画</p>
					<c:if test="${!empty resultDto.videoPath}">
						<div class="spinner-border d-none uploadFileLoading"></div>
						<video class="uploadFile" src="<c:url value="${resultDto.videoPath}" />" controls></video>
					</c:if>
					<hr/>
				</div>
			</div>
			
			<!-- ファイル一覧表示 -->
			<div class="mt-5">
				<h3>アップロード済みファイル一覧</h3>
				
				<!-- listFilesURLをサーバーで実行し、その結果をこのページに取り込む -->
				<jsp:include page="/listFiles"/>
					<c:choose>
						<c:when test="${empty fileList}">
							<p>アップロードされたファイルはありません</p>
						</c:when>
						<!-- テーブルを作成 -->
						<c:otherwise>
							<table class="table table-striped">
								<thead>
									<tr>
										<th>ファイル名</th>
										<th>サイズ(KB)</th>
										<th>ダウンロードリンク</th>
									</tr>
								</thead>
								<tbody>
									<!-- fileListの各要素をfileという変数名でループ処理 -->
									<c:forEach var="file" items="${fileList}">
										<tr>
											<!-- 現在のファイル名を表示 -->
											<td>${file.name}</td>
											<!-- 現在のファイルサイズをKBに変換して表示(小数第２位まで) -->
											<td><fmt:formatNumber value="${file.size / 1024}" maxFractionDigits="2" /></td>
											<!-- 現在のダウンロードURLへのリンクをBootstrapボタン形式で表示 -->
											<td><a href="${file.url}" target="_blank" class="btn btn-primary btn-sm">
												<!-- ダウンロードアイコンとテキストを表示 -->
												<i class="bi bi-download"></i>ダウンロード
											</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
				</jsp:include>
			</div>
		</div>
	</main>
	
	<footer class="footer mt-auto fixed-bottom py-3 bg-secondary"></footer>
	
	<script>
		//ID:uploadFormの要素が送信された時のイベントリスナー
		document.getElementById('uploadForm').addEventListener('submit', function(e)
		{
			//デフォルトのフォーム送信をキャンセル
			e.preventDefault();

			//イベントを発生させたオーム要素を取得
			const form = e.target;
			//フォームから入力データを取得し、非同期送信用のデータ(FormData)に変換
			const formData = new FormData(form);
			//ID:progressBarの要素を取得
			const progressBar = document.getElementById('progressBar');
			//ID:uploadProgressの要素を取得
			const uploadProgressArea = document.getElementById('uploadProgress');
			//ID:submitButtonの要素を取得
			const submitButton = document.getElementById('submitButton');

			//プログレスバーのコンテナからBoorstrapのd-noneクラス(非表示)を削除し表示状態に
			uploadProgressArea.classList.remove('d-none');
			//ボタンを無効化(二十送信を防ぐため)
			submitButton.disabled = true;

			//非同期通信を行うためのXMLHttpRequestオブジェクトを作成
			const xhr = new XMLHttpRequest();

			//xhr.uploadオブジェクトprogress(データ送信中)を監視
			xhr.upload.addEventListener('progress', function(event)
			{
				//送信全体のサイズ(event.total)が計算可能な場合
				if(event.lengthComputable)
				{
					//進捗率(%)を計算
					const percentComplete = Math.round((event.loaded/event.total) * 100);
					//プログレスバーの幅を計算したパーセンテージに設定
					progressBar.style.width = percentComplete + '%';
					//アクセシビリティ(aria-valuenow)に進捗率を設定
					progressBar.setAttribute('aria-valuenow', percentComplete);
					//プログレスバー内にてテキストで(50％など)を表示
					progressBar.innerText = percentComplete + '%';
				}
			});

			//loadイベントを監視
			xhr.addEventListener('load', function()
			{
				//サーバーからの応答を受け取ったらページ全体をリロードする
				window.location.reload();
			});

			//エラーイベントの監視
			xhr.addEventListener('error', function()
			{
				//エラーメッセージをアラートで表示
				alert('アップロード中にエラーが発生しました');
				//無効化していた送信ボタンを再度有効にする
				submitButton.disabled = false;
				//表示していた追うログレスバーを非表示に戻す
				uploadProgressArea.classList.add('d-none');
			});

			//非同期通信の開始
			//uploadServletURLに対し、非同期(true)で接続設定を行う
			xhr.open('POST', 'uploadServlet', true);
			//FormDataに格納したデータを使ってサーバーへ送信を開始
			xhr.send(formData);
		});
	</script>
</body>
</html>