package studentManager;

import java.util.ArrayList;
import java.util.Scanner;

//studentクラスの作成
class student
{
	//学生番号
	int id = 0;
	//名前
	String name = "";
	//五教科の成績
	int scores[] = new int[5];
	//合計
	int total = 0;
	//平均
	double ave = 0;
	//順位
	int rank = 0;
	
	//メンバー変数を指定
	public student(int id, String name, int[] scores, int total)
	{
		this.id = id;
		this.name = name;
		this.scores = scores;
		this.total = total;
		this.ave = total / 5.0;
	}
}


public class StudentManager
{
	//最大50人に定義
	final int MAX_STUDENT = 50;
	//リストの作成
	static ArrayList<student> st = new ArrayList<>();
	//変更不可な共通の文字列配列の作成
	final static String[] sub = {"国語", "数学", "社会", "理科", "英語"};
	
	public static void main(String[] args)
	{
		//入力された学生数を格納
		int num = 0;
		int id = 0;
		String name = "";
		int[] scores = new int[5];
		int total = 0;
		
		//Scannerの定義
		Scanner sr = new Scanner(System.in);
		
		System.out.println("学生数を入力（最大50名）：");
		num = sr.nextInt();
		
		//各学生の情報を入力
		for(int i = 0; i < num; i++)
		{
			System.out.println("学生番号：");
			id= sr.nextInt();
			System.out.println("名前（スペース不要）：");
			name = sr.next();
			//五教科
			for(int j = 0; j < 5; j++)
			{
				System.out.printf("%sの成績：\n", sub[j]);
				scores[j] = sr.nextInt();
				total += scores[j];
			}
			st.add(new student(id, name, scores, total));
		}
		
		//Ranksの呼び出し
		Ranks();
		//Displayの呼び出し
		Display();
		
		sr.close();
	}
	
	//順位の計算
	static void Ranks()
	{
		//st[]を順番に処理
		for(student s : st)
		{
			//初期値に1を設定
			int rank = 1;
			
			//順番に処理
			for(student other : st)
			{
				//other.totalがs.totalより大きい時
				if(other.total > s.total)
				{
					//ランクを++する
					rank++;
				}
			}
			
			//順位を保存
			s.rank = rank;
			
		}
	}
	
	//一覧表示
	static void Display()
	{
		//表示情報
		System.out.printf("%s\t%s", "番号", "氏名");
		for(String subject : sub)
		{
			System.out.printf("\t%s", subject);
		}
		System.out.printf("\t%s \t%s \t%s\n", "総合点", "平均点", "順位");
		
		//学生の情報を表示
		for(student s : st)
		{
			System.out.printf("%d\t%s", s.id, s.name);
			for(int score : s.scores)
			{
				System.out.printf("\t%d", score);
			}
			System.out.printf("\t%d \t\t%.2f \t\t%d\n", s.total, s.ave, s.rank);
		}
	}
}
