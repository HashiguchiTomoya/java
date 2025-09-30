package EmployeeManagement;

public abstract class Employee
{
	//フィールドの定義
	protected String Name = "";
	protected int Id = 0;
	
	public Employee(String name, int id)
	{
		//引数で渡された値を代入
		this.Name = name;
		this.Id = id;
	}
	
	public abstract double CalculateSalary();
	
	public void displayInfo()
	{
		System.out.println("ID :" + Id + "、名前 :" + Name + "、給料 :" + CalculateSalary());
	}
}
