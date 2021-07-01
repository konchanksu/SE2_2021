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

	@Override
	public String toString() {
		var sb = new StringBuilder();
		sb.append("startNode : (")
				.append(startNodeData.toString())
				.append("), endNode : (")
				.append(endNodeData.toString())
				.append(")");
		return sb.toString();
	}
}
