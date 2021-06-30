package forest.data;

public class BranchData {

	private NodeData startNodeData;

	private NodeData endNodeData;

	public BranchData(NodeData from, NodeData to) {
		this.startNodeData = from;
		this.endNodeData = to;
	}

	public NodeData getStart() {
		return this.startNodeData;
	}

	public NodeData getEnd() {
		return this.endNodeData;
	}

}
