package forest.model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import condition.Condition;
import condition.ConditionException;
import condition.ValueHolder;
import forest.Constant;
import forest.data.BranchData;
import forest.data.ForestData;
import forest.data.NodeData;
import mvc.Model;
import mvc.View;

/**
 * 樹状整列におけるモデルを担当する
 * 
 * @author Kodai Okayama
 */
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
		ValueHolder<Integer> y = new ValueHolder<Integer>(0);
		this.roots.forEach((aNode) -> {
			aNode.setPosition(new Point(0, y.get()));
			y.set(this.arrange(aNode).y);
			y.setDo((value) -> value + aNode.getExtent().y + Constant.VERTICAL_MOVE);
		});

		return;
	}

	/**
	 * 再起的に樹状整列を行う
	 * @param aNode 起点となるノード
	 * @return 子要素の描画に必要だった幅と高さ
	 */
	public Point arrange(NodeModel aNode) {
		aNode.isVisited(true);
		this.waitAndBroadcast();

		List<NodeModel> children = aNode.getChildren();
		try {
			new Condition(() -> children.isEmpty()).ifTrue((aCondition) -> {
				aCondition._return_();
			});
		} catch (ConditionException anException) {
			return aNode.getPosition();
		}

		ValueHolder<Integer> x = new ValueHolder<Integer>(aNode.getPosition().x);
		ValueHolder<Integer> height = new ValueHolder<Integer>(aNode.getPosition().y);
		ValueHolder<Integer> top = new ValueHolder<Integer>(aNode.getPosition().y);
		ValueHolder<Integer> y = new ValueHolder<Integer>(aNode.getPosition().y);
		aNode.getChildren().forEach((child) -> {
			new Condition(() -> child.isVisited()).ifFalse(() -> {
				Point extent = child.getExtent();
				height.setDo(value -> value + extent.y + Constant.VERTICAL_MOVE);
				child.setPosition((new Point(x.get() + aNode.getExtent().x + Constant.HORIZONTAL_MOVE, y.get())));
				y.set(this.arrange(child).y);
				y.setDo((value) -> value + Constant.VERTICAL_MOVE + extent.y);
				new Condition(() -> y.get() > height.get()).ifTrue(() -> {
					height.set(y.get());
				});
			});
		});

		y.setDo(value -> value - Constant.VERTICAL_MOVE);
		y.set(top.get() + (y.get() - top.get() - aNode.getExtent().y) / 2);

		new Condition(() -> aNode.getPosition().y > y.get()).ifTrue(() -> {
			y.set(aNode.getPosition().y);
		});

		height.setDo(value -> value - Constant.VERTICAL_MOVE - aNode.getExtent().y);
		new Condition(() -> aNode.getPosition().y > height.get()).ifTrue(() -> {
			height.set(aNode.getPosition().y);
		});

		aNode.setPosition(new Point(x.get(), y.get()));
		this.waitAndBroadcast();
		return new Point(x.get(), height.get());
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
		aGraphics.setColor(Constant.BACKGROUND_COLOR);
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

		return;
	}

	/**
	 * 与えられた座標に存在するノードのデータを返す。ない場合はnullを返す
	 * @param aPoint 座標
	 * @return ノード。ない場合はnull
	 */
	public NodeModel getNodeFromPoint(Point aPoint) {
		/*
		以下の処理と同じ
		this.nodes.forEach((aNode) -> {
			if(aNode.getBounds().contains(aPoint)){
				return aNode;
			};
		});
		return null
		*/
		ValueHolder<NodeModel> resultNode = new ValueHolder<>(null);
		try {
			this.nodes.forEach((aNode) -> {
				new Condition(() -> aNode.getBounds().contains(aPoint)).ifTrue((aCondition) -> {
					resultNode.set(aNode);
					aCondition._break_();
				});
			});
		} catch (ConditionException anException) {
			;
		}
		return resultNode.get();
	}

	/**
	 * このフォレストの矩形の領域を返す
	 * @return 矩形の領域
	 */
	public Rectangle getBounds() {
		ValueHolder<Rectangle> aRectangle = new ValueHolder<>(new Rectangle());
		this.nodes.forEach((aNode) -> {
			aRectangle.get().add(aNode.getBounds());
		});

		//座標ぴったりに確保すると端が描画されないので1ピクセル大きめにとる
		aRectangle.get().grow(1, 1);
		return aRectangle.get();
	}

	/**
	 * ForestDataを読み込み、ノード、ブランチ、ルートを設定する
	 * @param aForestData フォレストのデータ
	 */
	public void initialize(ForestData aForestData) {
		List<BranchData> branchDataList = aForestData.getBranchList();
		List<NodeData> nodeDataList = aForestData.getNodeList();
		ValueHolder<Map<String, NodeModel>> nodeMap = new ValueHolder<Map<String, NodeModel>>(
				new HashMap<String, NodeModel>());

		//NodeDataをNodeModelに変換し、フォレストに追加する
		nodeDataList.forEach((aNodeData) -> {
			NodeModel aNodeModel = new NodeModel(aNodeData.getName());
			new Condition(() -> nodeMap.get().containsKey(aNodeData.getId())).ifThenElse(() -> {
				throw new IllegalArgumentException(String.format("ID:{%s}を持つノードが複数存在します", aNodeData.getId()));
			}, () -> {
				nodeMap.get().put(aNodeData.getId(), aNodeModel);
			});
			this.nodes.add(aNodeModel);
		});

		//BranchDataをBranchModelに変換し、フォレストに追加する
		branchDataList.forEach((aBranchData) -> {
			NodeData startNodeData = aBranchData.getStart();
			NodeData endNodeData = aBranchData.getEnd();

			//ブランチには存在するが、ノードリストには存在しない場合エラーを出力する
			new Condition(() -> nodeMap.get().containsKey(startNodeData.getId())).ifFalse(() -> {
				throw new IllegalArgumentException(String.format("ID:{%s}のノードは定義されていません", startNodeData.getId()));
			});
			new Condition(() -> nodeMap.get().containsKey(endNodeData.getId())).ifFalse(() -> {
				throw new IllegalArgumentException(String.format("ID:{%s}のノードは定義されていません", startNodeData.getId()));
			});

			NodeModel startNodeModel = nodeMap.get().get(startNodeData.getId());
			NodeModel endNodeModel = nodeMap.get().get(endNodeData.getId());

			startNodeModel.addChild(endNodeModel);
			endNodeModel.addParent(startNodeModel);

			BranchModel aBranchModel = new BranchModel(startNodeModel, endNodeModel);
			this.branches.add(aBranchModel);
		});

		this.roots = this.nodes.stream().filter((aNode) -> aNode.isRoot()).toList();

		return;
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

		return;
	}

	/**
	 * 一定時間待ち、その後更新通知を出す
	 */
	private void waitAndBroadcast() {
		try {
			Thread.sleep(Constant.SLEEP_TIME);
		} catch (InterruptedException anException) {
			;
		}
		this.changed();

		return;
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
