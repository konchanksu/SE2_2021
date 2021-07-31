package forest.data;

/**
 * ブランチの開始NodeData、終了NodeDataの情報を保持するクラス
 * @author RyogaYamauchi
 */
public class BranchData {
	/**
	 * 開始NodeData
	 */
	private NodeData startNodeData;

	/**
	 * 終了NodeData
	 */
	private NodeData endNodeData;

	/**
	 * 開始、終了ノードを格納する
	 * @param from 開始ノード
	 * @param to 終了ノード
	 */
	public BranchData(NodeData from, NodeData to) {
		this.startNodeData = from;
		this.endNodeData = to;
	}

	/**
	 * 開始NodeDataを取得する
	 * @return 開始NodeData
	 */
	public NodeData getStart() {
		return this.startNodeData;
	}

	/**
	 * 終了NodeDataを取得する
	 * @return 終了NodeData
	 */
	public NodeData getEnd() {
		return this.endNodeData;
	}

	/**
	 * このクラスを文字列に変換する
	 */
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
