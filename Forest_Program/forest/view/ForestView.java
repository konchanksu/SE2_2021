package forest.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import condition.Condition;
import forest.controller.ForestController;
import forest.model.ForestModel;
import forest.model.NodeModel;
import mvc.View;

/**
 * ビュー画面周りを担当する
 */
@SuppressWarnings("serial")
public class ForestView extends View implements IForestView {

	/**
	 * モデルのインスタンスを束縛する
	 */
	protected ForestModel model;

	/**
	 * コントローラのインスタンスを束縛する
	 */
	protected ForestController controller;

	/**
	 * スクロール量としてPointのインスタンスを束縛する。
	 */
	private Point offset;

	/**
	 * インスタンスを生成して応答する。 指定されたモデルの依存物となり、指定されたコントローラにモデルとビューを設定し、スクロール量を(0, 0)に設定する。
	 * 
	 * @param aModel      このビューのモデル
	 * @param aController このビューのコントローラ
	 */
	public ForestView(ForestModel aModel, ForestController aController) {
		super(aModel, aController);
		this.model = aModel;
		this.controller = aController;
		this.offset = new Point(0, 0);

		return;
	}

	/**
	 * 指定されたグラフィクスに背景色（白色）でビュー全体を塗り、その後にモデルの内容物を描画する。
	 * それはスクロール量（offset）を考慮してモデル画像（picture）をペイン（パネル）内に描画することである。
	 *
	 * @param aGraphics グラフィックス・コンテキスト
	 */
	public void paintComponent(Graphics aGraphics) {
		int width = this.getWidth();
		int height = this.getHeight();
		aGraphics.setColor(Color.white);
		aGraphics.fillRect(0, 0, width, height);
		if (this.model == null) {
			return;
		}
		BufferedImage anImage = this.model.picture();
		if (anImage == null) {
			return;
		}
		aGraphics.drawImage(anImage, this.offset.x, this.offset.y, null);

		return;

	}

	/**
	 * ノードの名前をダイアログで出力する
	 *
	 * @param aNode ノード
	 */
	public void showDialog(NodeModel aNode) {
		new Condition(() -> aNode != null).ifTrue(() -> {
			JOptionPane.showConfirmDialog(null, aNode.getName(), "ノード情報", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
		});

		return;
	}

	/**
	 * ノードを座標から取得し、ノードの名前をダイアログで出力する
	 *
	 * @param aPoint 座標
	 */
	public void showNodeFromPoint(Point aPoint) {
		NodeModel aNode = this.model.getNodeFromPoint(aPoint);
		this.showDialog(aNode);

		return;
	}

	/**
	 * スクロール量（offsetの逆向きの大きさ）を応答する。
	 *
	 * @return X軸とY軸のスクロール量を表す座標
	 */
	public Point scrollAmount() {
		int x = 0 - this.offset.x;
		int y = 0 - this.offset.y;
		return (new Point(x, y));
	}

	/**
	 * スクロール量を指定された座標分だけ相対スクロールする。
	 * 
	 * @param aPoint X軸とY軸のスクロール量を表す座標
	 */
	public void scrollBy(Point aPoint) {
		int x = this.offset.x + aPoint.x;
		int y = this.offset.y + aPoint.y;
		this.scrollTo(new Point(x, y));
		return;
	}

	/**
	 * スクロール量を指定された座標に設定（絶対スクロール）する。
	 * 
	 * @param aPoint X軸とY軸の絶対スクロール量を表す座標
	 */
	public void scrollTo(Point aPoint) {
		this.offset = aPoint;
		return;
	}

}
