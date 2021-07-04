package forest;

import java.io.File;

import javax.swing.JFrame;

import forest.model.ForestModel;
import forest.repository.IForestDataRepository;
import forest.repository.MockForestDataRepository;
import forest.view.ForestView;
import forest.view.IForestView;

public class Example extends Object {

	private IForestDataRepository aForestDataRepository;

	public Example(IForestDataRepository anIForestDataRepository) {

	}

	public static void main(String[] arguments) {
		IForestDataRepository repository = new MockForestDataRepository(new File(""));
		Example anExample = new Example(repository);
		anExample.run();
	}

	public void run() {
		ForestModel aModel = new ForestModel();
		IForestView aView = new ForestView(aModel);
		JFrame aWindow = aView.getWindow();
		aWindow.addNotify();
		Integer titleBarHeight = aWindow.getInsets().top;
		aWindow.setSize(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT + titleBarHeight);
		aWindow.setResizable(true);
		aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aWindow.setTitle("Forest");
		aWindow.setVisible(true);
		aWindow.toFront();

	}

}
