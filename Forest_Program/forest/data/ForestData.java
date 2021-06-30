package forest.data;

import java.util.List;

public class ForestData {

	private List<NodeData> nodeList;

	private List<BranchData> branchList;

	public ForestData(List<NodeData> aNodeList, List<BranchData> aBranchList) {
		this.nodeList = aNodeList;
		this.branchList = aBranchList;
	}

	public List<NodeData> getNodeList() {
		return this.nodeList;
	}

	public List<BranchData> getBranchList() {
		return this.branchList;
	}

}
