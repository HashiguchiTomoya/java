package dto;

public class UploadFile
{
	//画像パス
	private String imgPath;
	
	//音声パス
	private String audioPath;
	
	//動画パス
	private String videoPath;
	
	//コンストラクタ
	public UploadFile()
	{
		
	}
	
	public UploadFile(String imgPath, String audioPath, String videoPath)
	{
		this.imgPath = imgPath;
		this.audioPath = audioPath;
		this.videoPath = videoPath;
	}
	
	public String getImgPath()
	{
		return imgPath;
	}
	public void setImgPath(String imgPath)
	{
		this.imgPath = imgPath;
	}
	
	public String getAudioPath()
	{
		return audioPath;
	}
	public void setAudioPath(String audioPath)
	{
		this.audioPath = audioPath;
	}
	
	public String getVideoPath()
	{
		return videoPath;
	}
	public void setVideoPath(String videoPath)
	{
		this.videoPath = videoPath;
	}
	
	@Override
	public String toString()
	{
		return "UploadFile{imgPath= '" + imgPath +"', audioPath='" + audioPath + "', videoPath='" + videoPath + "'}";
	}
}
