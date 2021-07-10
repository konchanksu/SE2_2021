package forest.controller;

import mvc.Controller;
import forest.view.IForestView;
import forest.model.ForestModel;
import java.awt.event.MouseEvent;
import java.awt.Point;

public class ForestController extends Controller {

	protected IForestView view;

	protected ForestModel model;
	/**
	 * このクラスのインスタンスを作成するコンストラクタ
	 */
	public ForestController() {
            super();
	}

	/**
	 * マウスをドラックした際に動作するメソッド
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseDragged(MouseEvent aMouseEvent) {
            super.mouseDragged(aMouseEvent);
	}

	/**
	 * マウスをクリックした際に動作するメソッド
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseClicked(MouseEvent aMouseEvent) {
            Point aPoint = aMouseEvent.getPoint();
            aPoint.translate(view.scrollAmount().x, view.scrollAmount().y);
            IForestView aView = this.view;
            aView.showNodeFromPoint(aPoint);
	}

}
