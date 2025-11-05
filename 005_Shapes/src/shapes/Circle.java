package shapes;

//円（具象クラス）
public class Circle extends Shapes
{
	//図形名と半径の初期化
	String name = "";
	double redius = 0;
	
	//コンストラクタの定義
	public Circle(String name, double redius)
	{
		this.name = name;
		this.redius = redius;
	}
	
	//ゲッター
	//名前を返す
	public String getName()
	{
		return name;
	}
	
	//面積を計算して返す(Math.PIは円周率)
	public double getArea()
	{
		//半径*半径*円周率
		return Math.PI * redius * redius;
	}
	
	//周囲長を計算して返す
	public double getChircumferene()
	{
		//直径*円周率
		return redius * 2 * Math.PI;
	}
	
}
