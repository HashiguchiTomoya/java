package model;

public class AddCommentLogic
{
	public void executeAddComment(Board bo)
	{
		new AddCommentDAO(bo);
	}
}
