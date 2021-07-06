package forest.data;

import java.util.List;

/**
 * forestに必要なデータを格納するクラス
 */
public class ForestData {

	/**
	 * ノードのリスト
	 */
	private List<NodeData> nodeList;

	/**
	 * ブランチのリスト
	 */
	private List<BranchData> branchList;

	/**
	 * NodeList、BranchListを格納する
	 * @param aNodeList
	 * @param aBranchList
	 */
	public ForestData(List<NodeData> aNodeList, List<BranchData> aBranchList) {
		this.nodeList = aNodeList;
		this.branchList = aBranchList;
	}

	/**
	 * NodeListを取得する
	 * @return NodeList
	 */
	public List<NodeData> getNodeList() {
		return this.nodeList;
	}

	/**
	 * BranchListを格納する
	 * @return BranchList
	 */
	public List<BranchData> getBranchList() {
		return this.branchList;
	}

}
