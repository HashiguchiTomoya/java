package EmployeeManagement;

public class PartTimeEmployee extends Employee
{
	//フィールドの定義
	public double HourlyRate = 0;
	public double HoursWorked = 0;
	
	public PartTimeEmployee(String Name, int Id, double HourlyRate, double HoursWorked)
	{
		//スーパークラスで呼び出す
		super(Name, Id);
		//引数で渡された値を代入
		this.HourlyRate = HourlyRate;
		this.HoursWorked = HoursWorked;
	}
	
	@Override
	public double CalculateSalary()
	{
		return HourlyRate * HoursWorked;
	}
}
