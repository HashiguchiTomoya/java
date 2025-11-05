  package employeeManager;

public abstract class Employee
{
	//社員番号と社員名の定義
	String type = "";
	int id = 0;
	String name = "";

	//コンストラクタの定義
	public Employee(int id, String name) 
	{
		this.id = id;
		this.name = name;
	}
	
	//オーバーライド
	//雇用形態
	abstract String getType();
	//社員番号
	abstract int getId();
	//社員名
	abstract String getName();
	//月収
	abstract double monthSalary();
	//年収予測
	abstract double annualIncome();
	
	//表示内容
	public void indication()
	{
		System.out.println("----一覧表示----");
		System.out.println("雇用形態：" + getType());
		System.out.println("社員番号：" + getId());
		System.out.println("社員名：" + getName());
		System.out.println("---給料切り上げ---");
		//Math.ceil()で切り上げ
		System.out.printf("月収：%.0f円\n", monthSalary());
		System.out.printf("年収：%.0f円\n", annualIncome());
		System.out.print("\n\n");
	}
	
}
