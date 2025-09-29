//現在表示中のスライドのインデックス
let currentIndex = 0;
//全てのスライド要素を取得
const slides = document.querySelectorAll('.slide');
//全てのサムネイル要素を取得
const thumbs = document.querySelectorAll('.thumb');
//ナビゲーションボタン
const nextBtn = document.getElementById('next');
const prevBtn = document.getElementById('prev');

//スライド表示関数
function showSlide(index)
{
	slides.forEach((slide, i) =>
	{
		slide.classList.toggle('active', i === index);
		thumbs[i].classList.toggle('active', i === index);
	});
	currentIndex = index;
}

//次へボタン
nextBtn.addEventListener('click', () =>
{
	let nextIndex = (currentIndex + 1) % slides.length;
	showSlide(nextIndex);
});

//前へボタン
prevBtn.addEventListener('click', () =>
{
	let prevIndex = (currentIndex - 1 + slides.length) % slides.length;
	showSlide(prevIndex);
});

//サムネイルクリックでジャンプ
thumbs.forEach(thumb =>
{
	thumb.addEventListener('click', () =>
	{
		showSlide(parseInt(thumb.dataset.index));
	});
});

//自動スライド
setInterval(() =>
{
	let nextIndex = (currentIndex + 1) % slides.length;
	showSlide(nextIndex);
}, 5000);	//五秒ごと

//初期表示
showSlide(currentIndex);