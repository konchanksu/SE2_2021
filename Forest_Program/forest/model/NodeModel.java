package forest.model;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

/**
 * 一つのノードを表すクラス
 * 
 * @author Kodai Okayama
 */
public class NodeModel {

	/**
	 * このノードの子要素を束縛するリスト
	 */
	private List<NodeModel> children;

	/**
	 * ノードの幅と高さを束縛する
	 */
	private Point extent;

	/**
	 * 一度の描画ですでに描画済みか
	 */
	private Boolean isVisited;

	/**
	 * このノードの名前を束縛する
	 */
	private String name;

	/**
	 * このノードの親要素を束縛する
	 */
	private List<NodeModel> parent;

	/**
	 * このノードの位置を束縛する。座標は左上が原点
	 */
	private Point position;

	/**
	 * コンストラクタ。 座標は(0,0)に設定され、幅と高さは文字の長さによって決定される。
	 * 
	 * @param name ノード名
	 */
	public NodeModel(String name) {
		this.setName(name);
		this.setPosition(new Point(0, 0));
		this.isVisited = false;
	}

	/**
	 * このノードを描画する
	 * 
	 * @param aGraphics 描画するグラフィクス
	 */
	public void draw(Graphics aGraphics) {

	}

	/**
	 * このノードの名前を返す
	 * 
	 * @return このノードの名前
	 */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		// Integer width = this.stringWidth(this.name) + (Constant.Margin.x * 2);
		// Integer height = this.stringHeight(this.name) + (Constant.Margin.y * 2);
		// this.setExtent(new Point(width, height));
	}

	public Point getPosition() {
		return this.position;
	}

	public void setPosition(Point aPoint) {
		this.position = aPoint;
		return;
	}

	public Point getExtent() {
		return null;
	}

	public void setExtent(Point aPoint) {

	}

	public Boolean isVisited() {
		return null;
	}

	public void isVisited(Boolean aBoolean) {

	}

}
