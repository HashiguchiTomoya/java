package com.app;

//import com.app.Users;
//import com.app.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//このクラスがspring MVCコントローラーであることを示し、HTTPリクエストを処理
@Controller
public class AuthController
{
	//必要なオブジェクトを自ら動的にこのクラスに注入
	@Autowired
	//データベースに保存・検索するためのインターフェース
	private UserRepository userRepository;
	
	@Autowired
	//パスワードをハッシュ化するためのセキュリティ機能
	private PasswordEncoder passwordEncoder;
	
	//HTTPGETリクエストを処理するメソッドの定義し対応するビューの名前を返す
	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
	
	@GetMapping("/register")
	public String register()
	{
		return "register";
	}
	
	//HTTPPOSTリクエストを/redisterエンドポイントで受け付ける
	@PostMapping("/register")
	//（）内でフォームから送信された名前、パスワードの値を取得
	public String registerUser(@RequestParam String username, @RequestParam String password)
	{
		//入力されたパスワードをBCryptでハッシュ化
		String encodedPassword = passwordEncoder.encode(password);
		
		//Userオブジェクトを生成し、ハッシュ化したパスワードを設定
		Users newUser = new Users();
		newUser.setUsername(username);
		newUser.setPassword(encodedPassword);
		newUser.setRole("USER");
		
		//データベースに保存
		try
		{
			userRepository.save(newUser);
			//成功したらログインページへ
			return "redirect:/login?registered";
		}
		catch(Exception e)
		{
			//失敗したら登録ページに戻る
			return "redirect:/register?error";
		}
	}
	
	//トップページと管理者ページに遷移できる
	
	@GetMapping("/")
	public String home()
	{
		return "home";
	}
	//管理者ページはDOLE＿ADMINを持つユーザーのみアクセス可能
	@GetMapping("/admin_dashboard")
	public String adminDashboard()
	{
		return "admin_dashboard";
	}
	
}
