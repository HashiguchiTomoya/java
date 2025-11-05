package employeeManager;

//正社員クラス
public class FullTimeEmployee extends Employee //継承
{
	String type = "正社員";
	double monthSalary = 0;
	
	//コンストラクタの定義
	public FullTimeEmployee(int id, String name, double salary)
	{
		//親クラスのメンバーにアクセス
		super(id, name);
		//月収を計算
		this.monthSalary = Math.ceil(salary);
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
