package forest.view;

import java.awt.Point;

import javax.swing.JFrame;

import forest.model.NodeModel;

/**
 * ForestViewのためのインターフェース
 * 
 * @author Kodai Okayama
 */
public interface IForestView {

	/**
	 * ノードの名前をダイアログで出力する
	 * 
	 * @param aNode ノード
	 */
	public abstract void showDialog(NodeModel aNode);

	/**
	 * ノードを座標から取得し、ノードの名前をダイアログで出力する
	 * 
	 * @param aPoint 座標
	 */
	public abstract void showNodeFromPoint(Point aPoint);

	/**
	 * このViewのウィンドウを返す
	 *
	 * @return このViewのウィンドウ
	 */
	public abstract JFrame getWindow();

	/**
	 * スクロール量（offsetの逆向きの大きさ）を応答する。
	 * 
	 * @return X軸とY軸のスクロール量を表す座標
	 */
	public abstract Point scrollAmount();

	/**
	 * スクロール量を指定された座標分だけ相対スクロールする。
	 * 
	 * @param aPoint X軸とY軸のスクロール量を表す座標
	 */
	public abstract void scrollBy(Point aPoint);

	/**
	 * スクロール量を指定された座標に設定（絶対スクロール）する。
	 * 
	 * @param aPoint X軸とY軸の絶対スクロール量を表す座標
	 */
	public abstract void scrollTo(Point aPoint);

	/**
	 * ビューの全領域を再描画する。
	 */
	public abstract void update();

}
