public class NodeModel {

	private List<NodeModel> children;

	private Point position;

	private List<NodeModel> parent;

	private String name;

	/**
	 * ノードの幅と高さを記録する
	 */
	private Point extent;

	private Boolean isVisited;

	private ForestView forestView;

	public void NodeModel(String name) {

	}

	public void changed() {

	}

	public void draw(Graphics aGraphics) {

	}

	public String getName() {
		return null;
	}

	public void setName(String name) {

	}

	public Point getExtent() {
		return null;
	}

	public void setExtent(Point aPoint) {

	}

	public Boolean isVisited() {
		return null;
	}

	public void isVisited(Boolean aBooelan) {

	}

}
