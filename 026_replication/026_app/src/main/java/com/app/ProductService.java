package com.app;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.core.instrument.MeterRegistry;

//このクラスガサービス層であることを定義
//このクラスが他の場所で必要になった時に注入
@Service
public class ProductService
{
	//データベースのアクセスを行うためのインスタンスを保持する変数
	private final ProductRepository productRepository;
	//MicrometerのRegistryを注入
	private final MeterRegistry meterRegistry;
	
	//@Autowired　定義しなくてもよさそうだが一応残しておく
	//このコンストラクタを使って注入を行う
	public ProductService(ProductRepository productRepository, MeterRegistry meterRegistry)
	{
		//引数として受け取ったproductRepositoryを、クラス内の変数に代入
		this.productRepository = productRepository;
		this.meterRegistry = meterRegistry;
	}
	
    public Product saveProduct(Product product)
    {
        // 製品が保存されるたびにカスタムカウンターを1増やす
        meterRegistry.counter("app.product.save.total").increment();

        return productRepository.save(product);
    }
	
	//書き込み走査はマスターへ
	//メソッド全体をトランザクションとして実行し、データの整合性を保証
	@Transactional
	//引数Productを受け取り戻り値はないメソッドの宣言
	public Product createProduct(Product product)
	{
		//カスタムメトリクスの記録を追加
		meterRegistry.counter("app.product.creation.total").increment();
		
		//保存した結果を返す
		return productRepository.save(product);
	}
	
	//読み取り専用のトランザクション
	@Transactional(readOnly = true)
	//データベースから取得したProductのリストを返すメソッド
	public List<Product> getAllProducts()
	{
		//fillAllで全てmpデータリストを取得し戻り値として返す
		return productRepository.findAll();
	}
}
