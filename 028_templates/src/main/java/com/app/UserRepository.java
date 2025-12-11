package com.app;

import java.util.Optional;

//import com.app.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Springのコンポーネントとして認識させる
@Repository
//後悔のインターフェースを定義(Usersは走査対象のエンティティでLongはその主キーの型)
public interface UserRepository extends JpaRepository<Users, Long>
{
	//usernameを使って検索するという操作を自動的に解釈させる
	Optional<Users> findByUsername(String username);
}
