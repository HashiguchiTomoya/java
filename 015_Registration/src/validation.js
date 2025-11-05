//ID:entryがクリックされたら、中の処理を実行 
document.getElementById("entry").onclick = function()
{
	//入力された値を変数に保存
	const name =document.getElementById("name").value;
	const nameHuri = document.getElementById("nameHuri").value;
	const birthdate = document.getElementById("birthdate").value;
	const phone = document.getElementById("phone").value;
	const mail = document.getElementById("mail").value;
	const pass = document.getElementById("pass").value;
	const repass = document.getElementById("repass").value;
	const addnumber = document.getElementById("addnumber").value;
	
	//変数の定義
	var flag = 0;
	
	//未記入チェック
	if(name.length == 0)
	{
		flag = 1;
	}
	if(nameHuri.length == 0)
	{
		flag = 1;
	}
	if(birthdate.length == 0)
	{
		flag = 1;
	}
	if(phone.length == 0)
	{
		flag = 1;
	}
	if(mail.length == 0)
	{
		flag = 1;
	}
	if(pass.length == 0)
	{
		flag = 1;
	}
	if(repass.length == 0)
	{
		flag = 1;
	}
	if(addnumber.length == 0)
	{
		flag = 1;
	}
	if(address.length == 0)
	{
		flag = 1;
	}
	
	if(flag == 1)
	{
		alert("必須項目に未記入箇所があります。");
	}
	
	//パスワードをチェック
	if(pass != repass)
	{
		alert("パスワードが一致しませんでした。");
		flag = 2;
	}			
	//チェックボックスにチェックが入っているか
	if(!check.checked)
	{
		alert("利用規約のチェックが必要です");
		flag = 2;
	}
}