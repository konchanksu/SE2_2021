package forest.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import condition.Condition;
import forest.data.BranchData;
import forest.data.ForestData;
import forest.data.NodeData;
import mvc.Model;

public class ForestModel extends Model {

	/**
	 * ノードを束縛するリスト
	 */
	private List<NodeModel> nodes;

	/**
	 * 根を束縛するリスト
	 */
	private List<NodeModel> roots;

	/**
	 * 枝を束縛するリスト
	 */
	private List<BranchModel> branches;

	/**
	 * コンストラクタ
	 */
	public ForestModel() {
		super();

		this.nodes = new ArrayList<NodeModel>();
		this.branches = new ArrayList<BranchModel>();
		this.roots = new ArrayList<NodeModel>();

		return;
	}

	/**
	 * 樹状整列をアニメーションで行う
	 */
	public void animate() {

	}

	/**
	 * 樹状整列を行う
	 * @param aNodeModel 起点となるノード
	 */
	public void arrange(NodeModel aNodeModel) {

	}

	/**
	 * このフォレストを描画する
	 */
	private void draw() {

	}

	/**
	 * 与えられた座標に存在するノードのデータを返す。ない場合はnullを返す
	 * @param aPoint 座標
	 * @return ノード。ない場合はnull
	 */
	public NodeModel getNodeFromPoint(Point aPoint) {
		return null;
	}

	/**
	 * このフォレストの矩形の領域を返す
	 * @return 矩形の領域
	 */
	public Rectangle getBounds() {
		Rectangle aRectangle = new Rectangle();
		this.nodes.forEach((aNode) -> {
			aRectangle.add(aNode.getBounds());
		});

		return aRectangle;
	}

	/**
	 * ForestDataを読み込み、ノード、ブランチ、ルートを設定する
	 * @param aForestData フォレストのデータ
	 */
	public void initialize(ForestData aForestData) {
		List<BranchData> branchDataList = aForestData.getBranchList();
		List<NodeData> nodeDataList = aForestData.getNodeList();
		Map<String, NodeModel> nodeMap = new HashMap<String, NodeModel>();

		//NodeDataをNodeModelに変換し、フォレストに追加する
		nodeDataList.forEach((aNodeData) -> {
			NodeModel aNodeModel = new NodeModel(aNodeData.getName());
			new Condition(() -> nodeMap.containsKey(aNodeData.getId())).ifThenElse(() -> {
				throw new IllegalArgumentException(String.format("ID:{%s}を持つノードが複数存在します", aNodeData.getId()));
			}, () -> {
				nodeMap.put(aNodeData.getId(), new NodeModel(aNodeData.getName()));
			});
			this.nodes.add(aNodeModel);
		});

		//BranchDataをBranchModelに変換し、フォレストに追加する
		branchDataList.forEach((aBranchData) -> {
			NodeData startNodeData = aBranchData.getStart();
			NodeData endNodeData = aBranchData.getEnd();

			//ブランチには存在するが、ノードリストには存在しない場合エラーを出力する
			new Condition(() -> nodeMap.containsKey(startNodeData.getId())).ifFalse(() -> {
				throw new IllegalArgumentException(String.format("ID:{%s}のノードは定義されていません", startNodeData.getId()));
			});
			new Condition(() -> nodeMap.containsKey(endNodeData.getId())).ifFalse(() -> {
				throw new IllegalArgumentException(String.format("ID:{%s}のノードは定義されていません", startNodeData.getId()));
			});

			NodeModel startNodeModel = nodeMap.get(startNodeData.getId());
			NodeModel endNodeModel = nodeMap.get(endNodeData.getId());

			startNodeModel.addChild(endNodeModel);
			endNodeModel.addParent(startNodeModel);

			BranchModel aBranchModel = new BranchModel(startNodeModel, endNodeModel);
			this.branches.add(aBranchModel);
		});

	}

	/**
	 * フォレストを縦一列に並べて表示する
	 */
	public void listNodes() {

	}

	/**
	 * 一定時間待ち、その後更新通知を出す
	 */
	private void waitAndBroadcast() {

	}

	/**
	* この枝を文字列に変換する
	*/
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ForestModel[");
		builder.append("nodes:");
		builder.append(this.nodes);
		builder.append(", branch:");
		builder.append(this.branches);
		builder.append("]");
		return builder.toString();
	}

}
