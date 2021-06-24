package forest.view;

import java.awt.Graphics;
import java.awt.Point;

import mvc.View;
import mvc.Model;
import mvc.Controller;

import forest.model.ForestModel;
import forest.controller.ForestController;
import forest.model.NodeModel;

@SuppressWarnings("serial")
public class ForestView extends View implements IForestView {

	protected ForestModel model;

	protected ForestController controller;

	public ForestView(Model aModel, Controller aController) {
		super(aModel, aController);
	}

	public void paintComponent(Graphics aGraphics) {

	}

	public void showDialog(NodeModel aNode) {

	}

	public void showNodeFromPoint(Point aPoint) {

	}

}
