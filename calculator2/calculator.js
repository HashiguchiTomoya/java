const display = document.getElementById("display");
const buttons = document.querySelectorAll("button");
const historyList = document.getElementById("history-list");

let currentInput = "";

buttons.forEach(button => {
	button.addEventListener("click", () =>{
		const value = button.textContent;
		
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
		else if(value === "C")
		{
			currentInput = "";
			display.value = "";
		}
		else if(value === "←")
		{
			currentInput = currentInput.slice(0 , -1);
			display.value = currentInput;
		}
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

function convertInput(input)
{
	return input.replace(/Math\.\w+\(/g, match => match).replace(/[^0-9+\-*/().]/g, '');
}

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