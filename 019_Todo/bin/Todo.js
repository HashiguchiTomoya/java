//HTMLドキュメントが完全に読み込まれて解析された後に実行
//DOMContentLoaded:onloadよりも速く発火のタイミングがHTMLの解析完了後、DOMツリーが構築された直後で、対象がdocumentオブジェクト
document.addEventListener("DOMContentLoaded", () =>
	{
	//特定のHTML要素への参照をひとまとめにして管理するためのオブジェクト
	const DOM = {
		todoForm : document.getElementById("todoForm"),
		newTodoInput : document.getElementById("todoInput"),
		todoList : document.getElementById("taskList"),
		filterAllButton : document.getElementById("filterAll"),
		filterActiveButton : document.getElementById("filterActive"),
		filterCompletedButton : document.getElementById("filterCompleted")
	};
	
	//todoデータを保持する配列
	let todos = [];
	//現在のフィルター状態(全て表示)
	let currentFilter = "all";
	
	//ローカルストレージ
	const getStoredTodos = () => 
	{
		//ブラウザのlocalStorageから"todos"であるデータを取得し、stored変数に格納(データがあれば文字列、なければnull)
		const stored = localStorage.getItem("todos");
		//(?:三項演算子)storedに値があるかをチェックJSON.parse(ある場合):localStoregeに文字列として保存、[](ない場合):空の配列を返す
		return stored ? JSON.parse(stored) : [];
	};
	
	//データの永続化(保存)
	const saveTodos = () =>
	{
		//"todos"でデータを取り出し、JSON.stringifyで保存(JSON.stringify:JSON形式の文字列に変換)
		localStorage.setItem("todos", JSON.stringify(todos));
	};
	
	//表示
	const renderTodos = () => {
		//リストを空にする(DOM.todoList:HTMLのコンテナ要素(<ul>)への参照、innerHTML="":参照したHTMLを空の文字列で上書き)
		DOM.todoList.innerHTML = "";
		//todoアイテムを一時的に格納するための配列を宣言
		let filteredTodos =  [];
		//現在のフィルターの状態が"active"の場合
		if(currentFilter === "active")
		{
			//todos配列の各要素をチェックし、!todo,completedのものだけを抽出
			filteredTodos = todos.filter(todo => !todo.completed);
		}
		//現在のフィルターの状態が"completed"の場合
		else if(currentFilter ==="completed")
		{
			//todos配列の各要素をチェックし、todo.completedの物だけを抽出
			filteredTodos = todos.filter(todo => todo.completed);
		}
		//現在のフィルターの状態が"all"の場合
		else
		{
			//todosのすべてを抽出
			filteredTodos = todos;
		}
		
		//配列に含まれるtodoアイテムに対して、指定された処理を順番に実行
		filteredTodos.forEach(todo =>
		{
			//新しいHTML要素<li>を作成
			const li = document.createElement("li");
			//作成したli要素にtodoItemというCSSクラスを追加
			li.classList.add("todoItem");
			//もしtodoアイテムのデータがcompletedの場合
			if(todo.completed)
			{
				//completedクラスも追加
				li.classList.add("completed");
			}
			//li要素にdata-id要素という属性を設定し、todoアイテムの一意なID(todo.id)を値として格納
			li.setAttribute("dataId", todo.id);
			//この要素をドラッグ可能に設定
			li.setAttribute("draggable", true);
			
			//li要素の内部にHTMLコンテンツを設定、チェックボックス、タスクのテキスト、削除ボタンを生成
			//81行目:todoがcomletedならchecked属性を付加し最初からチェックが入った状態にし、そうでなければチェックが入らない
			li.innerHTML = `
				<input type="checkbox" ${todo.completed ? 'checked' : ''}>
				<label>${todo.text}</label>
				<button class="deleteButton">削除</button>
			`;
			
			//todoリスト全体を参照し、.appendChildで実際に画面に表示
			DOM.todoList.appendChild(li);
		});
		
		//setupDragAndDropを呼び出し
		setupDragAndDrop();
	};
	
	//機能実装
	
	//タスク追加機能(e:イベントオブジェクト)
	const addTodo = (e) =>
	{
		console.log("追加ボタンが押されました");
		//イベントのデフォルト動作(フォーム送信のデフォルトは、ページをリロード)をキャンセル、非同期にtodoを追加
		e.preventDefault();
		//タスクの入力テキストボックスの参照し、入力された値を取得、空白を削除
		const taskText = DOM.newTodoInput.value.trim();
		//もし空だったら
		if(taskText === "")
		{
			console.log("入力が空です");
			//それ以降の処理を中止し、空のタスクが追加されるのを防ぐ
			return;
		}
		
		console.log("新しいタスクデータを作成",taskText);
		//新しいtodoオブジェクトの生成
		const newTodo = 
		{
			//現在のタイムスタンプ(ミリ単位の数値)を一意なIDとして使用
			id: Date.now(),
			//入力されたタスクのテキストを格納
			text: taskText,
			//初期状態としてタスクは未完了に設定
			completed: false
		};
		
		//todos配列に作成したnewTodoオブジェクトを最後に追加
		todos.push(newTodo);
		//saveTodos()を呼び出し(ローカルストレージに保存)
		saveTodos();
		//renderTodos()を呼び出し(画面表示を最新の状態に更新)
		renderTodos();
		//タスク追加後、入力ドームのテキストボックスを空に
		//DOM.newTodoInput.value = "";
	};
	
	//タスク削除、完了切り替え機能
	const handleListClick = (e) =>
	{
		//e.target:実際にクリックされた最も内側の要素(チェックボックスや削除ボタン)
		//.closest(".todoItem"):クリックされた要素自身または、親要素等を遡って、.todoItemクラスを持つ要素を探す
		const item = e.target.closest(".todoItem");
		//もしクリックされた要素が、todoItem能津川ではなかった場合
		if(!item)
		{
			//それ以降の処理を中止
			return;
		}
		
		//指定されたtodoアイテムの<li>要素から、dataIdの値を取得(parseInt():取得した文字列を数値に変換)
		const todoId = parseInt(item.getAttribute("dataId"));
		
		//クリックされた要素がdeleteButtonの場合
		if(e.target.classList.contains("deleteButton"));
		{
			//todos配列からクリックされたtodoのIDではないものだけを抽出して、新しい配列をtodosに再代入
			todos = todos.filter(todo => todo.id !== todoId);
		}
		//もしチェックボックスだった場合
		elseif(e.target.type === "checkbox")
		{
			//todos配列からクリックされたアイテムとIDが一致するものを探し出し、そのオブジェクトをtodo変数に代入
			const todo = todos.find(t => t.id === todoId);
			//もし対象が見つかれば
			if(todo)
			{
				//対象のオブジェクトのcompletedプロパティをチェックボックスの現在の状態(tureまたはfalse)に更新
				todo.completed = e.target.checked;
			}
		}
		
		//saveTodos()の呼び出し
		saveTodos();
		//renderTodos()の呼び出し
		renderTodos();
	};
	
	//フィルタリング機能
	const setFilter = (filterType) =>
	{
		//全てのボタンのアクティブクラスを解除(現在選択されていない状態(デフォルト)に戻すための処理)
		DOM.filterAllButton.classList.remove("active");
		DOM.filterActiveButton.classList.remove("active");
		DOM.filterCompletedButton.classList.remove("active");
		
		//選択されたボタンにアクティブクラスを追加
		//引数として受け取ったfilterTypeがallの場合
		if(filterType === "all")
		{
			//アクティブクラスを追加
			DOM.filterAllButton.classList.add("active");
		}
		//引数として受け取ったfilterTypeがactiveの場合
		else if(filterType === "active")
		{
			//アクティブクラスを追加
			DOM.filterActiveButton.classList.add("active");
		}
		//引数として受け取ったfilterTypeがcompletedの場合
		else if(filterType === "completed")
		{
			//アクティブクラスを追加
			DOM.filterCompletedButton.classList.add("active");
		}
		
		//全体で現在のフィルター状態を保持している変数(currentFilter)を新しく選択されたfilterTypeに更新
		currentFilter = filterType;
		//renderTodos()を呼び出し
		renderTodos();
	};
	
	
	//ドラッグアンドドロップ機能
	let dragSrcEl = null;
	
	//ドラッグ開始時
	function handleDragStart(e)
	{
		//detatransfer:ドラッグアンドドロップ操作中のデータを保持・管理する
		//effectAllpwedプロパティを"move"に設定することで。このドラッグ操作が移動を目的としていることをドロップ先に伝える
		e.dataTransfer.effectAllowed = "move";
		//ドラッグ操作と一緒に転送するデータを設定(text/HTML:データがHTML形式であることを示す)
		e.dataTransfer.setData("text/html", this.innerHTML);
		//グローバル変数にドラッグを開始した要素自身(this)への参照を格納(移動後に度の要素が移動元だったかを識別)
		dragSrcEl = this;
		//移動中の要素にdraggingクラスを追加(setTimeout(...0):ドラッグ操作の初期化処理完了する直後のクラスを追加)
		setTimeout(() => this.classList.add("dragging"), 0); 
	}
	
	//ドラッグ終了時
	function handleDragEnd(e)
	{
		//追加した"dragging"クラスを削除
		this.classList.remove("dragging");
	}
	
	
	//ドラッグ要素が別の要素の上に来た時
	function handleDragOver(e)
	{
		//""HTMLのデフォルト動作では、ほとんどの要素へのドロップは禁止されている""
		
		//e.preventDefault():を呼び出してドロップ禁止の動作を抑制
		e.preventDefault();
		//dataRtansferオブジェクトのdropEffectプロパティにmoveを設定(ドロップが成功した際に移動操作が行われていることを視覚的に示す(例。マウスカーソルが変わる))
		e.dataTransfer.dropEffect = "move";
	}
	
	//ドロップ時
	function handleDrop(e)
	{
		//handleDragOverで抑制したデフォルト動作に加えて、dropイベントでもデフォルト動作をキャンセルする
		e.preventDefault();
		//ドロップされた場所が元の要素(DragSrcEl)ではないとき
		if(dragSrcEl !== this)
		{
			//画面上のHTML要素の物理的な位置に入れ替える
			DOM.todoList.insertBefore(dragSrcEl, this.nextSibling);
		
			//todos配列の序列を更新
			//ドラッグを開始した要素のカスタム属性(dataId)ぁら一意なIDを取得
			const draggedId = parseInt(dragSrcEl.getAttribute("dataId"));
			//ドロップされた場所の要素のタスクIDを取得
			const droppedId = parseInt(this.getAttribute("dataId"));
			
			//todos配列の中からdraggedIdと一致するIDを持つ要素が何番目かを見つける
			const draggedIndex = todos.findIndex(t => t.id === draggedId);
			//todos配列の中からドロップ先が何番目課を見つける
			const droppedIndex = todos.findIndex(t => t.id === droppedId);
			
			//配列から要素を削除して、新しい位置に挿入
			//splice:配列のないほうの変更、draggedIndexの位置から1つの要素を削除,、[movedItem]:分割代入を使って削除された要素を取り出す
			const[movedItem] = todos.splice(draggedIndex, 1);
			//moveItemを配列に挿入、第二引数の0は要素を削除しないことを示す
			//droppedIndex > draggedIndex ? droppedIndex: droppedIndex:下から上へドラッグした場合droppedIndexの位置に挿入
			//上から下の場合元の要素が削除されて配列が詰まっているため、挿入位置をdroppedIndex+1に調節
			todos.splice(droppedIndex > draggedIndex ? droppedIndex: droppedIndex + 1, 0, movedItem);
			
			//saveTodos()の呼び出し
			saveTodos();
			//renderTodos()の呼び出し
			renderTodos();
		}
	}
	
	function setupDragAndDrop()
	{
		//ドキュメント全体から、.todoItemに一致する全ての要素を取得し代入
		const items = document.querySelectorAll(".todoItem");
		//取得した各アイテムを順番に処理
		items.forEach(item =>
		{
			//drafstartを実行したとき、handleDrafStartを実行する
			item.addEventListener("dragstart", handleDragStart);
			//dragendを実行したとき、handleDragEndを実行する
			item.addEventListener("dragend", handleDragEnd);
			//dragoverを実行したときに、handleDragOverを実行する
			item.addEventListener("dragover", handleDragOver);
			//dropを実行したときに、handleDropを実行する
			item.addEventListener("drop", handleDrop);
		});
	}
	
	//イベントリスナー
	//Enterキーか送信ボタンを押したときにaddTodoを事項
	DOM.todoForm.addEventListener("submit", addTodo);
	//リスト内のどこかがクリックされた時にhandleListClickを実行
	DOM.todoList.addEventListener("click", handleListClick);
	///全てボタンをクリックされたとき、setFilter("all")を実行
	DOM.filterAllButton.addEventListener("click", () => setFilter("all"));
	///未完了ボタンをクリックされたとき、setFilter("active")を実行
	DOM.filterAllButton.addEventListener("click", () => setFilter("active"));
	///完了ボタンをクリックされたとき、setFilter("completed")を実行
	DOM.filterAllButton.addEventListener("click", () => setFilter("completed"));
	
	//初期化
	//開始時に一度だけ実行される関数
	const init = () =>
	{
		//getStoredTodos()を呼び出す(localStorageから保存されたデータを読み込む)
		todos = getStoredTodos();
		//renderTodos()呼び出し
		renderTodos();
	};
	
	//初期化を実行
	init();
});