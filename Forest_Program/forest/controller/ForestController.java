package forest.controller;

import mvc.Controller;
import forest.view.IForestView;
import forest.model.ForestModel;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class ForestController extends Controller{

	protected IForestView view;

	protected ForestModel model;
	/**
	 * このクラスのインスタンスを作成するコンストラクタ
	 */
	public ForestController() {
		super();
		return;
	}

	/**
	 * マウスをドラックした際に動作するメソッド
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseDragged(MouseEvent aMouseEvent) {
		super.mouseDragged(aMouseEvent);
		return;
	}

	/**
	 * マウスをクリックした際に動作するメソッド
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseClicked(MouseEvent aMouseEvent) {
		Point aPoint = aMouseEvent.getPoint();
		aPoint.translate(this.view.scrollAmount().x, this.view.scrollAmount().y);
		this.view.showNodeFromPoint(aPoint);
        return;
	}

	/**
	 * IForestViewを束縛するメソッド
	 * @param aView 
	 */
	public void setView(IForestView aView){
		this.view = aView;
		return;
	}

	/**
	 * ForestModelを束縛するメソッド
	 * @param aModel 
	 */
	public void setModel(ForestModel aModel){
		this.model = aModel;
		return;
	}

}
