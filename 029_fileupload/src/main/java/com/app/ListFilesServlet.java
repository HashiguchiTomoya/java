package com.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//保存場所は同じものを参照
//このサーブレットが/lostFilesというURLでアクセスできるように設定
@WebServlet("/listFiles")
public class ListFilesServlet extends HttpServlet
{
	//ファイルが保持されているサーバー上のパスを定数として指定
	private static final String UPLOAD_DIRECTORY = "/tmp/app_uploads";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//クリエパラメータ"filename"があるか確認
		String fileName = request.getParameter("filename");
		
		if(fileName != null && !fileName.isEmpty())
		{
			//ダウンロード機能の実行
			executeDownload(request, response, fileName);
		}
		else
		{
			//一覧表示機能の実行
			prepareFileList(request, response);
		}
	}
	
	//ダウンロードメソッド
	private void executeDownload(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException
	{
		//保存先のディレクトリとファイル名からファイルオブジェクトを作成
		File file = new File(UPLOAD_DIRECTORY, fileName);
		
		//ファイルが存在しないまたはディレクトリである場合
		if(!file.exists() || !file.isFile())
		{
			//ファイルがない場合、404エラーと返す
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "ファイルが見つかれません");
			return;
		}
		
		//文字化け対策
		String encodedName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
		
		//レスポンスヘッダーの設定
		//レスポンスの種類をバイナリ形式のダウンロードに設定
		response.setContentType("application/octet-stream");
		//ブラウザにダウンロードを促し、保存時のファイルを指定
		response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedName);
		//レスポンスのデータサイズを設定
		response.setContentLength((int)file.length());
		
		//ファイルを読み込む入力ストリームと、ブラウザへ送る出力ストリームを準備
		try (FileInputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream())
			{
				//一度に読み書きするバッファを8KB分用意
				byte[] buffer = new byte[8192];
				//読み込んだバイト数を格納する変数
				int bytesRead;
				//ファイルが終端に達するまで切り返す
				while ((bytesRead = in.read(buffer)) != -1)
				{
					//読み込んだ分だけブラウザへデータを出力
					out.write(buffer, 0, bytesRead);
				}
			}
	}
	
	//リクエスト属性にセットするメソッド
	private void prepareFileList(HttpServletRequest request, HttpServletResponse response)
	{
		//ファイル情報を格納するための空リスト
		List<FileInfoDto> files = new ArrayList<>();
		//保存先のオブジェクトを作成
		File uploadDir = new File(UPLOAD_DIRECTORY);
		
		//ディレクトリが存在し、正しいフォルダである場合
		if(uploadDir.exists() && uploadDir.isDirectory())
		{
			//フォルダ内のファイル・ふぉるたー一覧を取得
			File[] fileList = uploadDir.listFiles();
			//一覧の取得に成功(nullではない)場合
			if(fileList != null)
			{
				//ファイル一つ一つに対してループ
				for(File file : fileList)
				{
					//フォルダーではなくファイルのみ
					if(file.isFile())
					{
						//ダウンロード用のURLを作成
						String downloadUrl = request.getContextPath() + "/listFiles?filename=" + file.getName();
						//名前、サイズ、URLを一つのデータとしてリストに追加
						files.add(new FileInfoDto(file.getName(), file.length(), downloadUrl));
					}
				}
			}
		}
		
		//JSPに渡す
		request.setAttribute("fileList", files);
	}

	//ファイル情報を格納するクラスの定義
	public static class FileInfoDto
	{
		private String name;
		private long size;
		private String url;
	
		public FileInfoDto(String name, long size, String url)
		{
			this.name = name;
			this.size = size;
			this.url = url;
		}
	
		public String getName()
		{
			return name;
		}
		public long getSize()
		{
			return size;
		}
		public String getUrl()
		{
			return url;
		}
	}
}