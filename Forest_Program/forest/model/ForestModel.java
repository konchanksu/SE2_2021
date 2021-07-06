package forest.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import condition.Condition;
import condition.ValueHolder;
import forest.Constant;
import forest.data.BranchData;
import forest.data.ForestData;
import forest.data.NodeData;
import mvc.Model;
import mvc.View;

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

	private static final Color BACKGROUND = Color.white;

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
	 * 自身が変更したことを依存物に通知する
	 */
	@Override
	public void changed() {
		// フォレストの領域を求め、その領域の幅と高さの画像を生成
		Rectangle aRectangle = this.getBounds();
		this.picture(new BufferedImage(aRectangle.width, aRectangle.height, BufferedImage.TYPE_INT_RGB));

		//背景色で塗りつぶす。
		Graphics aGraphics = this.picture().createGraphics();
		aGraphics.setColor(BACKGROUND);
		aGraphics.fillRect(0, 0, aRectangle.width, aRectangle.height);

		// 樹状整列を画像の描画コンテクスト（グラフィックス）に描き出す。
		this.draw(aGraphics);

		// モデルが変化していることを依存物であるビューたちへ放送（updateを依頼）する。
		this.dependents.forEach((View aView) -> {
			aView.update();
		});

		return;

	}

	/**
	 * このフォレストを描画する
	 */
	private void draw(Graphics aGraphics) {
		this.nodes.forEach((aNode) -> {
			aNode.draw(aGraphics);
		});
		this.branches.forEach((aBranch) -> {
			aBranch.draw(aGraphics);
		});
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

		//座標ぴったりに確保すると端が描画されないので1ピクセル大きめにとる
		aRectangle.grow(1, 1);
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
				nodeMap.put(aNodeData.getId(), aNodeModel);
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
		ValueHolder<Integer> index = new ValueHolder<Integer>(0);
		this.nodes.forEach((aNode) -> {
			aNode.setPosition(new Point(0, index.get() * (Constant.VERTICAL_MOVE + aNode.getExtent().y)));
			index.setDo(value -> value + 1);
		});
		this.changed();
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
