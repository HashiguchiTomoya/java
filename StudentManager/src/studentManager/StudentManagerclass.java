package studentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//studentクラス（学生一人の情報を格納する）
class student
{
	//変数を定義
	int id = 0;					//学生番号
	String name = "";			//氏名
	int scores[] = new int[5];	//五科目の成績
	int total = 0;				//合計
	double average = 0;			//平均
	int rank = 0;				//順位
	
	public student(int id, String name, int[] scores)
	{
		this.id = id;								//受けとったidを格納
		this.name = name;							//受けとったnameを格納
		this.scores = scores;						//受けとったscoresを格納
		this.total = Arrays.stream(scores).sum();	//合計を計算
		this.average = total / 5.0;					//平均を計算
	}
	
}

public class StudentManagerclass
{
	static final int MAX_STUDENT = 50;						//最大50名に定義
	static ArrayList<student> students = new ArrayList<>();	//リストの作成
	static final String[] subjects = {"国語", "数学", "理科", "社会", "英語"};
	
	public static void main(String[] args)
	{
		int num = 0; //入力された学生数を格納
		int id = 0;	//学生番号
		String name = "";
		int[] scores = new int[5];
		
		Scanner scanner = new Scanner(System.in);
		
		//学生数を入力
		System.out.print("学生数を入力（最大50名）：");
		//入力する学生数
		num = scanner.nextInt();
		//バッファをクリア
		scanner.nextLine();
		
		//各学生の情報を入力
		for(int i = 0; i < num && i < MAX_STUDENT; i++)
		{
			//学生番号
			System.out.print("学生番号：");
			id = scanner.nextInt();
			scanner.nextLine();
			//氏名
			System.out.print("氏名：");
			name = scanner.nextLine();
			//五科目の成績
			for(int j = 0; j < 5; j++)
			{
				System.out.printf("%sの成績：", subjects[j]);
				scores[j] = scanner.nextInt();
			}
			scanner.nextLine();
			
			students.add(new student(id, name, Arrays.copyOf(scores, scores.length)));
		}
		
		//calculateRanksの呼び出し
		calculateRanks();
		//printALLStudentsの呼び出し
		printAllStudents();
		
		//scannerをクローズ
		scanner.close();
	}
	
	//順位を計算
	static void calculateRanks()
	{	
		for(student s : students)
		{
			//初期値を一に設定
			int rank = 1;
			
			//順位付けの処理
			for(student other : students)
			{
				if(other.total > s.total)
				{
					rank++;
				}
			}
			
			//順位を保存
			s.rank = rank;
		}
	}
	
	//一覧表示
	static void printAllStudents()
	{
		//ヘッダー部分の表示
		System.out.printf("%-3s %-8s", "番号", "氏名");
		for(String subject : subjects)
		{
			System.out.printf("%-4s", subject);
		}
		System.out.printf("%-5s %-5s %-3s\n", "総合点", "平均点", "順位");
		
		//学生の情報を表示
		for(student s: students)
		{
			System.out.printf("%-6d %-10s", s.id, s.name);
			for(int score : s.scores)
			{
				System.out.printf("%-6d", score);
			}
			
			System.out.printf("%-7d %-10.2f %-6d\n", s.total, s.average, s.rank);
		}
	}
}
