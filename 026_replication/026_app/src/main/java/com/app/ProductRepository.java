package com.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//ProductRepository が検出され、アプリケーションコンテキスト内で管理されることを保証するアノテーション
@Repository
//JpaRepositoryを継承(自分で実装せずにsaveや、findAllが使える)
public interface ProductRepository extends JpaRepository<Product, Long>
{
	
}
