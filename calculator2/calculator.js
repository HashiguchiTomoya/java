//計算結果や数字を表示要素を取得
const display = document.getElementById("display");
//全てのボタンを取得
const buttons = document.querySelectorAll("button");
//履歴を取得
const historyList = document.getElementById("history-list");

let currentInput = "";

//ボタンのクリックされた時の処理
buttons.forEach(button => {
	button.addEventListener("click", () =>{
		const value = button.textContent;
		
		//計算実行
		if(value === "=")
		{
			try
			{
				const result = eval(convertInput(currentInput));
				addToHistory(currentInput + "=" + result);
				currentInput = result.toString();
				display.value = currentInput;
			}
			catch
			{
				display.value = "Error";
				currentInput = "";
			}
		}
		//全削除
		else if(value === "C")
		{
			currentInput = "";
			display.value = "";
		}
		//一文字削除
		else if(value === "←")
		{
			currentInput = currentInput.slice(0 , -1);
			display.value = currentInput;
		}
		//関数処理
		else if(["sin", "cos", "tan", "√"].includes(value))
		{
			currentInput += convertFunction(value);
			display.value = currentInput;
		}
		else
		{
			currentInput += value;
			display.value = currentInput;
		}
	});
});

//関数処理
function convertFunction(func)
{
	switch(func)
	{
		case "sin": return "Math.sin(";
		case "cos": return "Math.cos(";
		case "tan": return "Math.tan(";
		case "√": return "Math.sqrt(";
		default: return "";
	}
}

//入力値の変換
function convertInput(input)
{
	return input.replace(/Math\.\w+\(/g, match => match).replace(/[^0-9+\-*/().]/g, '');
}

//履歴の追加
function addToHistory(entry)
{
	const li = document.createElement("li");
	li.textContent = entry;
	historyList.prepend(li);
}

//キーボード入力対応
document.addEventListener("keydown", e => {
	if(e.key.match(/[0-9+\-*().]/))
	{
		currentInput += e.key;
		display.value = currentInput;
	}
	else if(e.key === "Enter")
	{
		try
		{
			const result = eval(convertInput(currentInput));
			addToHistory(currentInput + "=" + result);
			currentInput = result.toString();
			display.value = currentInput;
		}
		catch
		{
			display.value = "Error";
			currentInput = "";
		}
	}
	else if(e.key === "Backspace")
	{
		currentInput = currentInput.slice(0, -1);
		display.value = currentInput;
	}
})