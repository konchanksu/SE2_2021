public interface IForestView {

	private ForestModel forestModel;

	public void showDialog(NodeModel aNode);

	public void showNodeFromPoint(Point aPoint);

	public abstract Point scrollAmount();

	public abstract void scrollBy(Point aPoint);

	public abstract void scrollTo(Point aPoint);

	public abstract void update();

}
