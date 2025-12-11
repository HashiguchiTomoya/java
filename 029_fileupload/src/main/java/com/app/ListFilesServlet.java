package com.app;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
	//アプリから見たダウンロード用の仮想的なURLパスを定数として指定
	private static final String VIRTUAL_PATH = "/uploads/";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException
	{
		//ファイル情報を格納するための空のリストを作成
		List<FileInfoDto> files = new ArrayList<>();
		//指定されたアップロードパスに基づいてFileオブジェクトを生成
		File uploadDir = new File(UPLOAD_DIRECTORY);
		
		//指定されたパスが存在し、かつそれがディレクトリである場合
		if(uploadDir.exists() && uploadDir.isDirectory())
		{
			//ディレクトリ内のファイルとサブディレクトリのリストを取得
			File[] fileList = uploadDir.listFiles();
			//ディレクトリが空でないか
			if(fileList != null)
			{
				//取得したファイルリスト内の各ファイルに対してループ処理
				for(File file : fileList)
				{
					//ファイル名をUTF-8形式でエンコードする
					String encodedFileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.toString());
					//ダウンロード用の完全なURLを作成
					String downloadUrl = request.getContextPath() + VIRTUAL_PATH + encodedFileName;
					//名前、サイズ、URLを含む新しいFikeInfoDtoを作成し、リストに追加
					files.add(new FileInfoDto(file.getName(), file.length(), downloadUrl));
				}
			}
		}
		
		//構築したリストをfileListという名前でリクエスト属性に保存、JSPのビューに渡される
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