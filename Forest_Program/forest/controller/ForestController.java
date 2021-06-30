package forest.controller;

import mvc.Controller;
import forest.view.IForestView;
import forest.model.ForestModel;
import java.awt.event.MouseEvent;

public class ForestController extends Controller {

	protected IForestView view;

	protected ForestModel model;

	public ForestController() {
            super();
	}

	public void mouseDragged(MouseEvent aMouseEvent) {
            super.mouseDragged(aMouseEvent);
	}

	public void mouseClicked(MouseEvent aMouseEvent) {
            Point aPoint = aMouseEvent.getPoint();
            aPoint.translate(view.scrollAmount().x, view.scrollAmount().y);
            IForestView aView = this.view;
            aView.showNodeFromPoint(aPoint);
	}

}
