package com.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

//このクラスがSpringの設定クラスBeanを定義できる
@Configuration
//Spring Securtyを有効にし、デフォルトでセキュリティ設定を適応
@EnableWebSecurity
public class SecurityConfig
{
	//このメソッドが返すオブジェクトをSpringコンテナに登録し、アプリ全体で利用可能
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		//パスワードのハッシュ化にBCryptを使ってエンコーダーの新しいインスタンスを生成し返す
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() 
	{
		//アクセスが拒否されたら403にリダイレクト
		AccessDeniedHandlerImpl handler = new AccessDeniedHandlerImpl();
		handler.setErrorPage("/403");
		return handler;
	}
	
	@Bean
	//HttpSecurityオブジェクトを引数ニトリ、SecurityDilterChainを返すメソッドを定義
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		//引数HttpSecurityを使ってセキュリティ設定を構成
		http
			//Httpリクエストに対する認可の設定
			.authorizeHttpRequests(authorize -> authorize
				//認証なしで許可
				.requestMatchers("/","/register", "/403").permitAll()
				//ADMINロールを持つユーザーのみ許可
				.requestMatchers("admin_dashboard").hasRole("ADMIN")
				//指定されていない他のリクエストはログイン済みのユーザーのみ許可
				.anyRequest().authenticated()
			)
			//フォームログインの設定
			.formLogin(form -> form
				//ログインページとして/login.htmlを使う用に指定
				.loginPage("/login")
				//ログイン失敗時に/login?errorにリダイレクト
				.failureUrl("/login?error")
				//ログイン成功後、デフォルトでで√ＵＲＬに遷移
				.defaultSuccessUrl("/", true)
				//ログインページとログイン処理のエンドポイントは認証なしでアクセスを許可
				.permitAll()
			)
			//ログアウト載せてい
			.logout(logout -> logout
				//ログアウト処理を行うＵＲＬを/logoutに設定
				.logoutUrl("/logout")
				//ログアウト成功後もログインに遷移
				.logoutSuccessUrl("/login?logout")
				//認証なしで許可
				.permitAll()
			)
			//セッション管理
			.sessionManagement(session -> session
				//セッションが無効になった時に遷移するURL
				.invalidSessionUrl("/logion?invalidSession")
				//最大セッション数を１に制限
				.maximumSessions(1)
				//制限を超えた場合にログインを拒否(flaseの場合古いセッションを無効に)
				.maxSessionsPreventsLogin(true)
			)
			.exceptionHandling(exceptions -> exceptions
				//権限がない場合のエラーハンドリング
				.accessDeniedHandler(accessDeniedHandler())
			);
		//設定が完了したオブジェクトを構成して返す。
		return http.build();
	}
}
