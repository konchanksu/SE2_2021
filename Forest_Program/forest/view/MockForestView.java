package forest.view;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;

import forest.controller.ForestController;
import forest.model.ForestModel;
import forest.model.NodeModel;
import mvc.View;

/**
 * ForestViewのテスト用クラス
 */
@SuppressWarnings("serial")
public class MockForestView extends View implements IForestView {

	/**
	 * モデルのインスタンスを束縛する
	 */
	protected ForestModel forestModel;

	/**
	 * コントローラのインスタンスを束縛する
	 */
	protected ForestController forestController;

	/**
	 * インスタンスを生成し、モデルを束縛する。コントローラは新規で作成
	 *
	 * @param aModel このビューのモデル
	 */
	public MockForestView(ForestModel aModel) {
		super(aModel, new ForestController());
		return;
	}

	/**
	 * インスタンスを生成し、モデルを束縛する
	 *
	 * @param aModel      このビューのモデル
	 * @param aController このビューのコントローラ
	 */
	public MockForestView(ForestModel aModel, ForestController aController) {
		super(aModel, aController);

		return;
	}

	/**
	 * このViewのウィンドウを返す
	 *
	 * @return このViewのウィンドウ
	 */
	public JFrame getWindow() {
		return new JFrame();
	}

	/**
	 * 指定されたグラフィクスに背景色（白色）でビュー全体を塗り、その後にモデルの内容物を描画する。
	 * それはスクロール量（offset）を考慮してモデル画像（picture）をペイン（パネル）内に描画することである。
	 * 
	 * @param aGraphics グラフィックス・コンテキスト
	 */
	public void paintComponent(Graphics aGraphics) {

		return;
	}

	/**
	 * ノードの名前をダイアログで出力する
	 * 
	 * @param aNode ノード
	 */
	public void showDialog(NodeModel aNode) {

		return;
	}

	/**
	 * ノードを座標から取得し、ノードの名前をダイアログで出力する
	 * 
	 * @param aPoint 座標
	 */
	public void showNodeFromPoint(Point aPoint) {

		return;
	}

}
