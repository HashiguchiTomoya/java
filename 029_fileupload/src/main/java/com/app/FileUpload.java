package com.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dto.UploadFile;

//uploadServletというURLパターンでアクセスできるように設定
@WebServlet("/uploadServlet")
//ファイルのサイズ制限
@MultipartConfig
(
	//書き込みを開始すサイズ(1MB)
	fileSizeThreshold = 1024 * 1024 * 1,
	//個々のファイルの最大サイズ(10MB)
	maxFileSize = 1024 * 1024* 10,
	//リクエスト全体の最大サイズ(50MB)
	maxRequestSize = 1024 * 1024 * 50
)
//HttpServletを継承したサーブレットの公開クラス
public class FileUpload  extends HttpServlet
{
	//アップロードされたファイルを保存する物理的なディレクトリパスを定義
	private static final String UPLOAD_DIRECTORY = "/tmp/app_uploads";
	
	//HTTPのGETリクエストを処理するためのメソッドをオーバーライド
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		//GETリクエストが来た際にJSPファイルに転送する、フォームの表示
		String view = "/FileUpload.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
	
	//POSTリクエストを処理するメソッドをオーバーライド
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		//送られてきたデータの文字エンコードをUTF-8に設定し文字化けを防止
		request.setCharacterEncoding("UTF-8");
		
		//アップロード結果のパスを格納するためのデータ転送オブジェクト(DTO)インスタンスを生成
		UploadFile dto = new UploadFile();
		try 
		{
			//名前(imgなど)に対応するファイルを取得しfikeUploadメソッドで処理
			//第二引数でDTOのどのセッターを呼び出すか指定、第三引数で許可するファイル拡張子のリストを渡す
			fileUpload(request.getPart("img"), (s) -> dto.setImgPath(s), Arrays.asList(".jpg", "jpeg", ".png", ".gif"));
			fileUpload(request.getPart("audio"), (s) -> dto.setAudioPath(s), Arrays.asList(".mp3", ".wav", "aac"));
			fileUpload(request.getPart("video"), (s) -> dto.setVideoPath(s), Arrays.asList(".mp4", ".mov", ".avi"));
			//アップロード成功後、結果を格納したDTOをresultDtoに保存
			request.setAttribute("resultDto", dto);
		}
		catch(ServletException | IOException | IllegalArgumentException e)
		{
			//エラーハンドリング(スタックレースをコンソールに出力)
			e.printStackTrace();
			//errorMessageという名前でリクエスト属性に保存
			request.setAttribute("errorMessage", "ファイルのアップロード中にエラーが発生しました："+ e.getMessage());
		}
		//doGetメソッドを呼び出し、エラーメッセージや成功結果をJSPへ遷移し、結果を表示させる
		doGet(request, response);
	}
	
	//ファイルのアップロードを行うメソッド
	//Partオブジェクト、DTOをセッターを呼び出す関数型のインターフェースConsumer、許可拡張子のリストを引数に取る
	private void fileUpload(Part part, Consumer<String> pathConsumer, List<String> allowedExtensions) throws IOException, ServletException
	{
		//アップロードされたファイル名を取得し、ファイルが選択されていない(空かnull)の場合は何もせずに終了させる
		String submittedFileName = part.getSubmittedFileName();
		if(part == null || submittedFileName == null || submittedFileName.isEmpty())
		{
			return;
		}
		
		//ファイル名から拡張子を抽出し、ホワイトリストで検証する
		//java.nio.file.Pathsでファイル名だけを抽出し、そこから拡張子部分を抽出して小文字に変換
		String fileNameOnly = Paths.get(submittedFileName).getFileName().toString();
		String fileExtension = "";
		int dotIndex = fileNameOnly.lastIndexOf('.');
		if(dotIndex > 0)
		{
			fileExtension = fileNameOnly.substring(dotIndex).toLowerCase();
		}
		
		//もし拡張子がリストに含まれていない場合(ホワイトリストにない)IllegalArgumrnyExceptionをスローし、処理を停止
		if(!allowedExtensions.contains(fileExtension))
		{
			throw new IllegalArgumentException("許可されていないファイル形式です:" + fileExtension);
		}
		
		//ユーザー入力に基づかない、一意で安全なファイル名を生成
		//UUID(汎用一意識別子)を使用してランダムで一意な新しいファイル名を生成
		String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
		
		//保存先のディレクトリの存在を確認して、存在しなければ作成
		File uploadDir = new File(UPLOAD_DIRECTORY);
		if(!uploadDir.exists())
		{
			uploadDir.mkdirs();
		}
		
		//保存先のパスと生成したファイル名を組み合わせて最終的な保存場所のfileオブジェクトの作成
		File storeFile = new File(uploadDir, uniqueFileName);
		
		try
		{
			//絶対パスを使用してファイルを書き込む
			part.write(storeFile.getAbsolutePath());
			
			//DTOにアプリケーション内で管理するための相対パスやファイル名をセット
			//表示用に仮想的なパス(/uploads/)をDTOに格納
			String virtualPathForDB = "/uploads/" + uniqueFileName;
			pathConsumer.accept(virtualPathForDB);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			throw new IOException("ファイルの保存に失敗しました" + e);
		}
	}
}
