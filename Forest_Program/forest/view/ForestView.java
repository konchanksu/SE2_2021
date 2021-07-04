package forest.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import condition.Condition;
import condition.ConditionException;
import forest.controller.ForestController;
import forest.model.ForestModel;
import forest.model.NodeModel;
import mvc.View;

/**
 * ビュー画面周りを担当する
 * 
 * @author Kodai Okayama
 */
@SuppressWarnings("serial")
public class ForestView extends View implements IForestView {

	/**
	 * モデルのインスタンスを束縛する
	 */
	protected ForestModel model;

	private JFrame aWindow = new JFrame();

	/**
	 * インスタンスを生成し、モデルを束縛する。コントローラは新規で作成
	 *
	 * @param aModel このビューのモデル
	 */
	public ForestView(ForestModel aModel) {
		super(aModel, new ForestController());
		this.model = aModel;
		this.aWindow.getContentPane().add(this);
		return;
	}

	/**
	 * インスタンスを生成し、モデルを束縛する
	 *
	 * @param aModel      このビューのモデル
	 * @param aController このビューのコントローラ
	 */
	public ForestView(ForestModel aModel, ForestController aController) {
		super(aModel, aController);
		this.model = aModel;
		this.aWindow.getContentPane().add(this);
		return;
	}

	/**
	 * このViewのウィンドウを返す
	 *
	 * @return このViewのウィンドウ
	 */
	public JFrame getWindow() {
		return this.aWindow;
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

		/**
		 * 以下の処理は次の処理と同じ
		 *
		 * if(this.model == null)return;
		 * BufferedImage anImage = this.model.picture();
		 * if(anImage == null) return;
		 * Point offset = this.scrollAmount();
		 * aGraphics.drawImage(anImage, offset.x, offset.y, null);
		 */

		try {
			new Condition(() -> this.model == null).ifTrue((aCondition) -> {
				aCondition._return_();
			});
			BufferedImage anImage = this.model.picture();
			new Condition(() -> anImage == null).ifTrue((aCondition) -> {
				aCondition._return_();
			});
			Point offset = this.scrollAmount();
			aGraphics.drawImage(anImage, offset.x, offset.y, null);
		} catch (ConditionException exception) {
			//Condition._retnrn_()が実行された == メソッドが終了
			exception.isReturnTrue(() -> System.out.println("Condition._return_()が実行"));
			return;
		}
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

}
