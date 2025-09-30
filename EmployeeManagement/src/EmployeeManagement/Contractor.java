package EmployeeManagement;

public class Contractor extends Employee
{
	//フィールドの定義
	private double ContractAmount = 0;
	
	public Contractor(String Name, int Id, double ContractAmount)
	{
		//スーパークラスで呼び出す
		super(Name,Id);
		//引数で渡された値を代入
		this.ContractAmount = ContractAmount;
	}
	
	@Override
	public double CalculateSalary()
	{
		return ContractAmount;
	}

}
