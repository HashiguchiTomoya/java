package EmployeeManagement;

public class FullTimeEmployee extends Employee
{
	private double MonthlySalary = 0;
	
	//コンストラクタ
	public FullTimeEmployee(String Name, int Id, double MonthlySalary)
	{
		//スーパークラスで呼び出す
		super(Name ,Id);
		//引数で渡された値を代入
		this.MonthlySalary = MonthlySalary;
	}
	
	@Override
	public double CalculateSalary()
	{
		return MonthlySalary;
	}
}
