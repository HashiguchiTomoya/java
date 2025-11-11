
const display = document.getElementById("display");
const buttons = document.querySelectorAll("button");
const historyList = document.getElementById("historylist");

let currrntInput = "";

//全てのボタンにイベントリスナーを設定する
buttons.forEach(button =>
{
	//ボタンがクリックされた時の処理
	button.addEventListener("click", () =>
	{
		//クリックされた要素を取得
		const value = button.textContent;
		
		//＝だった場合
		if(value === "=")
		{
			//エラーは起きてもアプリが停止しない要にする
			try
			{
				
				let expression = currrntInput;
				//expressionの中身のｘなどの記号をjsで使える*などに変換
				expression = converInput(expression);
				
				//expressionに含まれる(の数を数える、ない場合は0
				const openParens = (expression.match(/\(g/) || []).length;
				//expressionに含まれる)の数を数える、ない場合は0
				const closeParens = (expression.match(/\)/g) || []).length;
				
				//(の数が)よりも多い場合
				if(openParens > closeParens)
				{
					//不足している）の数だけ生成し末尾に追加
					expression += ")".repeat(openParens - closeParens);
				}
				//テストログ
				console.log("eval:", expression);
				//jsの組み込み関数ecal()を使って、文字列exoressionを計算式として実行し、結果を取得
				const result = eval(convertInput(currrntInput));
				//addToHistoryを呼び出し、履歴に計算式と結果を表示
				addToHistory(currrntInput + "=" + result);
				//次の計算用に入力状態から計算結果を上書きして表示
				currrntInput = result.toString();
				display.value = currrntInput;
			}
			catch
			{
				//画面表示領域にErrorを表示
				display.value = "Error";
				//入力状態の文字列をリセット
				currrntInput = "";
			}
		}
		//Cが押された場合
		else if(value === "C")
		{
			//入力状態の文字列をリセット
			currrntInput = "";
			//表示領域もリセット
			display.value = "";
		}
		//←が押された場合
		else if(value === "←")
		{
			//currntInputの最後の１文字を削除
			currrntInput = currrntInput.slice(0 , -1);
			//画面を更新
			display.value = currrntInput;
		}
		//[]ないのいずれかが押された場合
		else if(["sin", "cos", "tan", "√"].includes(value))
		{
			//convertFunctionを呼び出してcurrentInputに対応する文字列を追加
			currrntInput += convertFunction(value);
			//画面を更新
			display.value = currrntInput;
		}
		//上記以外の場合
		else
		{
			//そのままcurrntInputに追加
			currrntInput += value;
			//画面の更新
			display.value = currrntInput;
		}
	});
});

//高度な計算の処理

//対応する数学関数を返す関数の定義、fancという１つの入力値を取り出す
function convertFunction(func)
{
	//fancの値に応じて分岐
	switch(func)
	{
		//sinの場合
		case "sin":
			//Math.sin（を返す
			return "Math.sin(";
		//cosの場合
		case "con":
			//Math.cos(を返す
			return "Math.cos(";
		//tanの場合
		case "tan":
			//Math.tan(を返す
			return "Math.tan(";
		//√の場合
		case "√":
			//Math.sqrt(を返す
			return "Math.sqrt(";
		//もし一致しなかった場合空の文字を返す
		default:
			return "";
	}
}

//eval関数で扱える形式に変換する関数
function convertInput(input)
{
	//すでに有効な値が含まれている時のために一時的に保護
	//input.replace(:正規表現が一致する部分を置き換える、/Math\.\w+\([^()]*\)/g:括弧で終わる関数呼び出しのパターンを探す
	//__${match}__見つかった関数呼び出しの文字列の前後にアンダーバー２つつけて保護領域であることを示す
	const protectedInput = input.replace(/Math\.\w+\([^()]*\)/g, match => `__${match}__`);
	//不要な文字列の削除(protectedInput.replace:ほふぉマークがついた文字列に対して置き換えを行う^は以下の文字列以外を意味)
	const cleaned = protectedInput.replace(/[^0-9+\-*/()._]/g, '');
	//cleaned.replace:フィルタリング後の文字列に対して置き換える,
	//match.slice(2, -2):見つかった文字列(アンダーバーが二つついた文字列)の両端を取り除き、それを返す
	return cleaned.replace(/_Math\/\w+\([^()]*\)_/g, match => match.slice(2, -2));
}

//履歴を残す関数(entryという追加したい履歴の文字列を受け取る)
function addToHistory(entry)
{
	//新しHTMLに<li>を作成し、それを参照
	const li = document.createElement("li");
	//<li>に引数として受け取った文字列をテキストコンテンツとして設定
	li.textContent = entry;
	//履歴リストの要素に、<li>要素をリストの先頭に追加
	historyList.prepend(li);
}

//キーボード入力
//ドキュメント全体に対してキーボードが押されたときのイベントリスナーの設定
document.addEventListener("keydown", e =>
{
	//押されたキーが正規表現に一致する場合
	if(e.key.match(/[0-9+\-*().]/))
	{
		//押された文字追加
		currrntInput += e.key;
		//画面の更新
		display.value = currrntInput;
	}
	//押されたキーがEnterの場合
	else if(e.key === "Enter")
	{
		try
		{
			//=の時と同じ処理を行う
			const result = eval(convertInput(currrntInput));
			addToHistory(currrntInput + "=" + result);
			convertInput = result.toString();
			display.value = currrntInput;
		}
		catch
		{
			display.value = "Error";
			currrntInput = "";
		}
	}
	//押されたキーがBackspaceの場合
	else if(e.key === "Backspace")
	{
		//←と同じ処理を行う
		currrntInput = currrntInput.slice(0, -1);
		display.value = currrntInput;
	}
});