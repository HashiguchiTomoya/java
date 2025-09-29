//タスク追加フォーム
const form = document.getElementById("todo-form");
//タスク入力
const input = document.getElementById("todo-input");
//タスク一覧
const list = document.getElementById("todo-list");
//ボタン
const filters = document.querySelectorAll(".filters button");

//データの初期化
let todos = JSON.parse(localStorage.getItem("todos")) || [];
let currentFilter = "all";

//データの保存
function saveTodos()
{
	localStorage.setItem("todos", JSON.stringify(todos));
}

//タスクの描画処理
function renderTodos()
{
	list.innerHTML = "";
	const filtered = todos.filter(todo => {
		if(currentFilter === "active") return !todo.completed;
		if(currentFilter === "completed") return todo.completed;
		return true;
	});
	
	//タスクの表示と操作
	filtered.forEach((todo, index) => {
		const li = document.createElement("li");
		li.textContent = todo.text;
		li.className = todo.completed ? "completed" : "";
		li.draggable = true;
		
		//ドラッグアンドドロップによる並び替え
		li.addEventListener("dragstart", e =>{
			e.dataTransfer.setData("text/plain", index);
		});
		li.addEventListener("dragover", e => e.preventDefault());
		li.addEventListener("drop", e =>{
			const fromIndex = e.dataTransfer.getData("text/plain");
			const toIndex = index;
			const moved = todos.splice(fromIndex, 1)[0];
			todos.splice(toIndex, 0, moved);
			saveTodos();
			renderTodos();
		});
		
		//削除ボタンの追加
		const delBtn = document.createElement("button");
		delBtn.textContent = "削除";
		delBtn.addEventListener("click", e =>{
			e.stopPropagation();
			todos.splice(index, 1);
			saveTodos();
			renderTodos();
		});
		
		li.appendChild(delBtn);
		list.appendChild(li);
	});
}

//タスクの追加処理
form.addEventListener("submit", e => {
	e.preventDefault();
	const text = input.value.trim();
	if(text)
	{
		todos.push({ text, completed: false });
		input.value = "";
		saveTodos();
		renderTodos();
	}
});

//フィルター切り替え
filters.forEach(btn =>{
	btn.addEventListener("click", () => {
		currentFilter = btn.dataset.filter;
		renderTodos();
	});
});

renderTodos();