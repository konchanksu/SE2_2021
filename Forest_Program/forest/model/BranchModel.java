package forest.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import forest.Constant;

/**
 * 一つの枝を表すクラス
 * 
 * @author Kodai Okayama
 */
public class BranchModel extends Object {

	/**
	 * この枝の始点を表すノード
	 */
	private NodeModel startNode;

	/**
	 * この枝の終点を表すノード
	 */
	private NodeModel endNode;

	/**
	 * コンストラクタ。始点と終点を受け取り束縛する
	 *
	 * @param from 始点を表すノード
	 * @param to 終点を表すノード
	 */
	public BranchModel(NodeModel from, NodeModel to) {
		this.startNode = from;
		this.endNode = to;
	}

	/**
	 * ブランチを表す線を描画する
	 *
	 * @param aGraphics 描画するグラフィックス
	 */
	public void draw(Graphics aGraphics) {

		//アンチエイリアス
		Graphics2D aGraphics2D = (Graphics2D) aGraphics;
		aGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		aGraphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// 始点座標を計算
		Point position = this.startNode.getPosition();
		Point extent = this.startNode.getExtent();
		Integer startX = position.x + extent.x;
		Integer startY = position.y + (extent.y / 2);
		// 終点座標を計算
		position = this.endNode.getPosition();
		extent = this.endNode.getExtent();
		Integer endX = position.x;
		Integer endY = position.y + (extent.y / 2);

		aGraphics.setColor(Constant.BRANCH_COLOR);
		aGraphics.drawLine(startX, startY, endX, endY);

		return;
	}

	/**
	 * この枝の始点を表すノードを返す
	 *
	 * @return 始点を表すノード
	 */
	public NodeModel getStart() {
		return this.startNode;
	}

	/**
	 * この枝の終点を表すノードを返す
	 *
	 * @return 終点を表すノード
	 */
	public NodeModel getEnd() {
		return this.endNode;
	}

	/**
	 * この枝を文字列に変換する
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BranchModel[");
		builder.append("startNode : ");
		builder.append(this.startNode);
		builder.append(", endNode : ");
		builder.append(this.endNode);
		builder.append("]");
		return builder.toString();
	}

}
