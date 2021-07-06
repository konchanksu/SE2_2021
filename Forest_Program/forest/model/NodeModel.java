package forest.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * 一つのノードを表すクラス
 *
 * @author Kodai Okayama
 */
public class NodeModel extends Component {

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
	private List<NodeModel> parents;

	/**
	 * このノードの位置を束縛する。座標は左上が原点
	 */
	private Point position;

	// TODO マージンの定数を追加する
	private final Point MARGIN = new Point(2, 2);
	// TODO Constant.javaにフォントサイズの要素を追加
	private final Font DEFAULT_FONT = new Font("Monospaced", Font.PLAIN, 12);

	/**
	 * コンストラクタ。 座標は(0,0)に設定され、幅と高さは文字の長さによって決定される。
	 *
	 * @param name ノード名
	 */
	public NodeModel(String name) {
		this.setName(name);
		this.setPosition(new Point(0, 0));
		this.children = new ArrayList<NodeModel>();
		this.parents = new ArrayList<NodeModel>();
		this.isVisited = false;

		Integer width = this.getStringWidth(name) + MARGIN.x * 2;
		Integer height = this.getStringHeight(name) + MARGIN.y * 2;
		this.setExtent(new Point(width, height));

		return;
	}

	/**
	 * このノードを描画する
	 *
	 * @param aGraphics 描画するグラフィクス
	 */
	public void draw(Graphics aGraphics) {
		// TODO 背景色の定数を追加

		aGraphics.setColor(Color.white);
		aGraphics.drawRect(this.position.x, this.position.y, this.extent.x, this.extent.y);

		Point namePosition = this.getPosition();
		namePosition.translate(MARGIN.x, MARGIN.y);

		aGraphics.setFont(DEFAULT_FONT);
		aGraphics.drawString(this.name, namePosition.x, namePosition.y);

		return;

	}

	/**
	 * このノードの名前を返す
	 *
	 * @return このノードの名前
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * このノードの名前を設定する。同時にノードの幅と高さも設定する
	 *
	 * @param name 設定する名前
	 */
	public void setName(String name) {
		this.name = name;
		return;
	}

	/**
	 * このノードの座標を取得する
	 * @return 座標
	 */
	public Point getPosition() {
		return this.position;
	}

	/**
	 * このノードの座標を設定する
	 * @param aPoint 座標
	 */
	public void setPosition(Point aPoint) {
		this.position = aPoint;
		return;
	}

	/**
	 * このノードの幅と高さを取得する
	 * @return 幅と高さ
	 */
	public Point getExtent() {
		return this.extent;
	}

	/**
	 * このノードの幅と高さを設定する
	 * @param aPoint 幅と高さ
	 */
	public void setExtent(Point aPoint) {
		this.extent = aPoint;
		return;
	}

	/**
	 * 描画済みかどうかを返す
	 * @return 描画済みかどうか
	 */
	public Boolean isVisited() {
		return this.isVisited;
	}

	/**
	 * 描画済みかどうか
	 * @param aBoolean 描画済みかどうか
	 */
	public void isVisited(Boolean aBoolean) {
		this.isVisited = aBoolean;
		return;
	}

	/**
	 * このノードが根(親要素を持たない)かどうか
	 * @return 根かどうか
	 */
	public Boolean isRoot() {
		return this.parents.isEmpty();
	}

	/**
	 * このノードが葉(子要素を持たない)かどうか
	 * @return 葉かどうか
	 */
	public Boolean isLeaf() {
		return this.children.isEmpty();
	}

	/**
	 * このノードの子要素を取得する
	 * @return 子要素のリスト
	 */
	public List<NodeModel> getChildren() {
		return this.children;
	}

	/**
	 * このノードの子要素を追加する
	 * @param child 子要素を表すノード
	 */
	public void addChild(NodeModel child) {
		this.children.add(child);
		return;
	}

	/**
	* このノードの子要素を追加する
	* @param child 子要素のリスト
	*/
	public void addChildren(List<NodeModel> children) {
		this.children.addAll(children);
		return;
	}

	/**
	 * このノードの親要素を追加する
	 * @param parent 親要素を表すノード
	 */
	public void addParent(NodeModel parent) {
		this.parents.add(parent);
		return;
	}

	/**
	* このノードの親要素を追加する
	* @param parents 親要素のリスト
	*/
	public void addParents(List<NodeModel> parents) {
		this.parents.addAll(parents);
		return;
	}

	/**
	 * 文字列の高さを取得する
	 * @param aString 対象の文字列
	 * @return 文字列の高さ
	 */
	private Integer getStringHeight(String aString) {
		FontMetrics fontMetrics = this.getFontMetrics(DEFAULT_FONT);
		return fontMetrics.getHeight();
	}

	/**
	 * 文字列の幅を取得する
	 * @param aString 対象の文字列
	 * @return 文字列の幅
	 */
	private Integer getStringWidth(String aString) {

		FontMetrics fontMetrics = this.getFontMetrics(DEFAULT_FONT);
		return fontMetrics.stringWidth(aString);
	}

}
