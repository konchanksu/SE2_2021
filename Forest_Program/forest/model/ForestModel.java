package forest.model;

import java.util.List;
import forest.view.IForestView;
import mvc.Model;
import forest.data.NodeData;
import forest.data.ForestData;
import java.awt.Point;

public class ForestModel extends Model {

	protected List<IForestView> dependents;

	/**
	 * ノードを記録するリスト
	 */
	private List<NodeModel> nodeList;

	/**
	 * この木の根を表すノード
	 */
	private List<NodeModel> roots;

	private List<BranchModel> branchList;

	public ForestModel() {
		super();
	}

	public void animate() {

	}

	public void addDependent(IForestView anIForestVIew) {

	}

	public void arrange(NodeModel aNodeModel) {

	}

	public void changed() {

	}

	private void draw() {

	}

	public NodeData getNodeFromPoint(Point aPoint) {
		return null;
	}

	public void initialize(ForestData aForestData) {

	}

	public void listNodes() {

	}

	private void waitAndBroadcast() {

	}

}
