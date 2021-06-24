package forest.view;

import java.awt.Graphics;
import java.awt.Point;

import mvc.View;
import forest.model.ForestModel;
import forest.controller.ForestController;
import forest.model.NodeModel;

@SuppressWarnings("serial")
public class MockForestView extends View implements IForestView {

	protected ForestModel forestModel;

	protected ForestController forestController;

	/**
	 *  
	 */
	public MockForestView(ForestModel aModel, ForestController aController) {
		super(aModel, aController);
	}

	public void paintComponent(Graphics aGraphics) {

	}

	public void showDialog(NodeModel aNode) {

	}

	public void showNodeFromPoint(Point aPoint) {

	}

}
