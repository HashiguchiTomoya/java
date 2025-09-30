package StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StringUtilsclass
{
	//回文判定メソッド
	public static boolean palindrome(String input)
	{
		String cleane = input.replaceAll("\\s+", "").toLowerCase();		//空白を削除、小文字に変換
		String reverse = new StringBuilder(cleane).reverse().toString();	//文字列を反転
		
		return cleane.equals(reverse);									//文字列と反転した文字列が同じならtureを返す
	}
	
	//文字出現回数カウントメソッド
	public static Map<Character, Integer> countCharacters(String input)
	{
		Map<Character, Integer> charCount = new HashMap<>();		//文字とその出現回数を記憶するマップを作成
		for(char c : input.toCharArray())						//一つずつchar型に分解
		{
			if(Character.isWhitespace(c)) continue;				//空白をスキップ
			charCount.put(c, charCount.getOrDefault(c , 0) + 1);	//出現回数をカウント
		}
		
		return charCount;
	}
	
	//文字列反転メソッド
	public static String reverseString(String input)
	{
		return new StringBuilder(input).reverse().toString();	//文字列を反転
	}
	
	//単語数カウントメソッド
	public static int countWords(String input)
	{
		if(input == null || input.trim().isEmpty()) return 0;	//nullもしくは空文字列の場合0返す
		String[] words = input.trim().split("\\s+");			//文字列の両端の空白を取り除き分割して配列に
		
		return words.length;									//単語数を返す
	}
	
	//main処理
	public static void main(String[] args)
	{
		//例題
		String test = "し ん ぶ ん し";
		
		System.out.printf("例).%s\n", test);
		System.out.println("1.回文判定     :" + palindrome(test));
		System.out.println("2.文字出現回数 :" + countCharacters(test));
		System.out.println("3.文字列反転   :" + reverseString(test));
		System.out.println("4.単語数       :" + countWords(test));
		
		//入力した文字列を判定
		String  words = "";
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("回文を入力 :");
		words = scanner.nextLine();
		
		System.out.println("1.回文判定     :" + palindrome(words));
		System.out.println("2.文字出現回数 :" + countCharacters(words));
		System.out.println("3.文字列反転   :" + reverseString(words));
		System.out.println("4.単語数       :" + countWords(words));
		
		scanner.close();
	}
}
