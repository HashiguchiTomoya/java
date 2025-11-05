package employeeManager;

//パートタイム社員クラス
public class PartTimeEmployee extends Employee //継承
{
	String type = "パートタイム社員";
	double monthSalary = 0;
	
	//コンストラクタの定義
	public PartTimeEmployee(int id, String name, double hourlyWage, double wokingHours)
	{
		//親クラスのメンバーにアクセス
		super(id, name);
		this.monthSalary = Math.ceil(hourlyWage * wokingHours);
	}
	
	public String getType()
	{
		return type;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	@Override
	public double monthSalary()
	{
		return monthSalary;
	}
	
	@Override
	public double annualIncome()
	{
		return monthSalary *12;
	}

}
