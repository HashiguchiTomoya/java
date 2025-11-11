//現在表示中のスライドのインデックス
let currentIndex = 0;
const slides = document.querySelectorAll(".slide");
const thumbs = document.querySelectorAll(".thumb");
const prevButton = document.getElementById("prev");
const nextButton = document.getElementById("next");

//スライド表示
function showSlide(index)
{
	//slidesに含まれる全てをループ
	slides.forEach((slide, i) => 
	{
		//現在のスライド(i)が関数(index)と等しいかをチェックし、同じならactiveクラスが追加、異なる場合は削除
		slide.classList.toggle("active", i === index);
		//現在のサムネイルが等しいか
		thumbs[i].classList.toggle("active", i === index);
	});
	//現在表示中のスライドがどれかを記憶
	currentIndex = index;
}

//次へボタン
//次へボタンがクリックされたときに実行
nextButton.addEventListener("click", () => 
{
	//現在のスライドインデックス+1しスライドの総数で割ったあまりを格納
	let nextIndex = (currentIndex + 1) % slides.length;
	//格納されてたインデックスのスライドを表示
	showSlide(nextIndex);
});

//前へボタン
//前へボタンがクリックされたときに実行
prevButton.addEventListener("click", () => {
	//現在のスライドインデックスから-1(し+slides,lengthで負の数になるのを防ぐ)スライドの総数で割ったあまりを格納
	let prevIndex = (currentIndex - 1 + slides.length) % slides.length;
	//格納されたインデックスのスライドを表示
	showSlide(prevIndex);
});

//サムネイルをクリックで表示
//thumbsに含まれる全ての要素に実行
thumbs.forEach(thumb => 
{
	//サムネイルがクリックされたとき
	thumb.addEventListener("click", () => 
	{
		//thumb.dataset.indexでHTMLの属性値を取得し、それを数値に変換、変換した値のスライドを表示
		showSlide(parseInt(thumb.dataset.index));
	});
});

//自動スライド
//指定された関数を指定された秒数で繰り返し実行
setInterval(() => 
{
	//次へボタンと同じ処理
	let nextIndex = (currentIndex + 1) % slides.length;
	showSlide(nextIndex);
}, 5000);	//5秒ごと


//初期表示
//ページ読み込み完了字にcurrentIndexの初期値(0)を使って最初のスライドを表示]
showSlide(currentIndex);