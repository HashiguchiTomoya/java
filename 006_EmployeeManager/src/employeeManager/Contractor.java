package employeeManager;

//契約社員クラス
public class Contractor extends Employee //継承
{
	String type = "契約社員";
	double monthSalary = 0;
	
	//コンストラクタの定義
	public Contractor(int id, String name, double contractMoney)
	{
		//親クラスのメンバーにアクセル
		super(id, name);
		//月収を計算
		this.monthSalary = Math.ceil(contractMoney);
	}
	//ゲッター
	//雇用形態
	public String getType()
	{
		return type;
	}
	
	//社員番号
	public int getId()
	{
		return id;
	}
	//社員名
	public String getName()
	{
		return name;
	}
	
	//親クラスのメソッドを子クラスで定義
	//月収
	@Override
	public double monthSalary()
	{
		return monthSalary;
	}
	
	//推定年収
	@Override
	public double annualIncome()
	{
		return monthSalary * 12;
	}
}
