package forest.model;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import condition.Condition;
import condition.ConditionException;
import forest.Constant;

/**
 * 一つのノードを表すクラス
 *
 * @author Kodai Okayama
 */
@SuppressWarnings("serial")
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
	 * このノードのID。同値性の比較に使用
	 */
	private String id;

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
	private Point location;

	/**
	 * コンストラクタ。 座標は(0,0)に設定され、幅と高さは文字の長さによって決定される。
	 *
	 * @param name ノード名
	 * @param id id
	 */
	public NodeModel(String id,String name) {
		this.id = id;
		this.setName(name);
		this.setLocation(new Point(0, 0));
		this.children = new ArrayList<NodeModel>();
		this.parents = new ArrayList<NodeModel>();
		this.isVisited = false;

		Integer width = this.getStringWidth(this.name) + Constant.MARGIN.x * 2;
		Integer height = this.getStringHeight() + Constant.MARGIN.y * 2;
		this.setExtent(new Point(width, height));

		return;
	}

	/**
	 * このノードを描画する
	 *
	 * @param aGraphics 描画するグラフィクス
	 */
	public void draw(Graphics aGraphics) {
		//アンチエイリアス
		Graphics2D aGraphics2D = (Graphics2D) aGraphics;
		aGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		aGraphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		aGraphics.setColor(Constant.NODE_COLOR);

		//枠を描画する
		aGraphics.drawRect(this.location.x, this.location.y, this.extent.x, this.extent.y);

		//名前を描画する座標。参照渡しにならないようにgetLocationをつける
		Point nameLocation = this.getLocation().getLocation();
		//fontは左下座標が原点!!
		nameLocation.translate(Constant.MARGIN.x, this.extent.y - Constant.MARGIN.y - 1);

		aGraphics.setFont(Constant.FONT);
		aGraphics.drawString(this.name, nameLocation.x, nameLocation.y);
		return;
	}

	/**
	* このノードの矩形の領域を返す
	*
	* @return 矩形の領域
	*/
	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.location.x, this.location.y, this.extent.x, this.extent.y);
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
	 * このノードのIDを返す
	 * @return このノードのID
	 */
	public String getId(){
		return this.id;
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
	@Override
	public Point getLocation() {
		return this.location;
	}

	/**
	 * このノードの座標を設定する
	 * @param aPoint 座標
	 */
	@Override
	public void setLocation(Point aPoint) {
		this.location = aPoint;
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
	* @param children 子要素のリスト
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
	 * @return 文字列の高さ
	 */
	private Integer getStringHeight() {
		FontMetrics fontMetrics = this.getFontMetrics(Constant.FONT);
		return fontMetrics.getHeight();
	}

	/**
	 * 文字列の幅を取得する
	 * @param aString 対象の文字列
	 * @return 文字列の幅
	 */
	private Integer getStringWidth(String aString) {

		FontMetrics fontMetrics = this.getFontMetrics(Constant.FONT);
		return fontMetrics.stringWidth(aString);
	}

	/**
	 * このオブジェクトと他のオブジェクトが等しいかを示す
	 * @param anObject 比較対象のオブジェクト
	 */
	@Override
	public boolean equals(Object anObject){
		try{
		new Condition(()->anObject == this).ifTrue((aCondition)->{
			aCondition._return_();
		});

		new Condition(()->anObject instanceof NodeModel).ifTrue((aCondition)->{
			NodeModel aNodeModel = (NodeModel) anObject;
			new Condition(()->aNodeModel.getId().equals(this.getId())).ifTrue(()->{
				aCondition._return_();
			});
		});

		}catch(ConditionException anException){
			return true;
		}

		return false;
	}

	/**
	 * このオブジェクトのハッシュコードを返す
	 */
	@Override
	public int hashCode(){
		return this.id.hashCode();
	}

	/**
	* このノードを文字列に変換する
	*/
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NodeModel(");
		builder.append(this.name);
		builder.append(")");
		return builder.toString();
	}

}
