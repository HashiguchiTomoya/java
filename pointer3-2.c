#include <stdio.h>
#include <stdlib.h>

typedef struct Node
{
	int m_value;
	struct Node *m_prev;
	struct Node *m_next;
}Node;

//4.2つのノードを相互にリンクする(lhs:左側、rhs:右側)
void link(Node *lhs, Node *rhs)
{
	//どちらか一方でもNULLの場合
	if(lhs == NULL || rhs == NULL)
	{
		return;
	}
	//lhsの次の要素としてrhsを指す
	lhs -> m_next = rhs;
	//rhsの前の要素としてlhsを指す
	rhs -> m_prev = lhs;
}

//5.6.新しいNodeをmallocし、自分自身と相互リンクして返す
Node *newList()
{
	Node *node = (Node *)malloc(sizeof(Node));
	//失敗した場合はNULLを返してエラー
	if(node == NULL)
	{
		return NULL;
	}
	//新しく作ったノードの値を0にセット
	node -> m_value = 0;
	//自分自身にリンク
	link(node, node);
	return node;
}

//7.環状リストの最後のノードを返す
Node *lastNode(Node *root)
{
	//リストの起点(root)が存在しない場合エラー
	if(root == NULL)
	{
		return NULL;
	}
	//rootの前にある要素を返す
	return root -> m_prev;
}

//8.全てのノードの値を表示する
void printNodes(Node *root)
{
	if(root == NULL)
	{
		return;
	}
	//現在の要素を示すcurrに開始地点であるrootを代入
	Node *curr = root;
	printf("Nodes:");
	do
	{
		//値を表示
		printf("%d", curr -> m_value);
		//次の要素に進む
		curr = curr -> m_next;
	}
	//rootに戻ってきたら終了
	while(curr != root);
	printf("\n");
}

//9.nth番目のノードを返す
Node *nthNode(Node *root, int nth)
{
	//リストがない場合または指定された番号がマイナスの場合エラー
	if(root == NULL || nth < 0)
	{
		return NULL;
	}
	//currをrootからスタート
	Node *curr = root;
	//nth回分だけm_next
	for(int i = 0; i < nth; i++)
	{
		curr = curr -> m_next;
		//先頭に戻ってきた場合
		if(curr == root)
		{
			return NULL;
		}
	}
	return curr;
}

//10.ptrを環状リストの最後に追加
void appendNode(Node *root, Node *ptr)
{
	//どちらかが空の場合
	if(root == NULL || ptr == NULL)
	{
		return;
	}
	//現在の末尾にあるノードを特定しlast変数に置く
	Node *last = root -> m_prev;
	//リンクの張り直し
	//これまでの最後尾の次に新しいノードを繋ぐ
	link(last, ptr);
	//新しいノードの次を先頭に繋ぐ
	link(ptr, root);
}

//11.ptrをcurの直前に挿入
void insertNode(Node *cur, Node *ptr)
{

	//どちらかが空の場合
	if(cur == NULL || ptr == NULL)
	{
		return;
	}
	//挿入したい場所(cur)の現在の１つ前のノードを特定しprevに保管
	Node *prev = cur -> m_prev;
	//リンクの張り直し
	link(prev, ptr);
	link(ptr, cur);
}

//12.要素をリストから外す
void removeNode(Node *ptr)
{
	//対象が存在しない、一つしか存在しない
	if(ptr == NULL || ptr -> m_next == ptr)
	{
		return;
	}
	//ptrの前と後ろのノードをlink関数で繋ぐ
	link(ptr -> m_prev, ptr -> m_next);
}

//13.ノードを開放
void deleteAllNodes(Node *root)
{
	if(root == NULL)
	{
		return;
	}
	//現在削除使用としているノード(curr)を先頭に
	Node *curr = root;
	//次のノードの場所を記録
	Node *next;

	do
	{
		//次の場所を保管
		next = curr -> m_next;
		//削除
		free(curr);
		//保管していたノードを代入
		curr = next;
	}
	//最初に戻ってきたら終了
	while(curr != root);
}

int main(void)
{
	//リストの作成
	Node *root = newList();
	root -> m_value = 10;

	//ノードの作成と追加
	Node *n20 = (Node*)malloc(sizeof(Node));
	n20 -> m_value = 20;
	appendNode(root,  n20);

	Node *n30 = (Node *)malloc(sizeof(Node));
	n30 -> m_value = 30;
	appendNode(root, n30);

	//挿入
	Node *n15 = (Node *)malloc(sizeof(Node));
	n15 -> m_value = 15;
	insertNode(n20, n15);

	//結果表示
	printf("--リスト--\n");
	printNodes(root);

	//nthNodeのテスト
	Node *target = nthNode(root, 2);
	if(target)
	{
		printf("2番目のノード:%d\n", target -> m_value);
	}

	//removeNodeテスト
	printf("15を削除します\n");
	removeNode(n15);
	free(n15);
	printNodes(root);
	
	//最後尾の確認
	printf("最後尾:%d\n", lastNode(root) -> m_value);

	//全削除
	deleteAllNodes(root);
	printf("すべて削除しました\n");

	return 0;
}
