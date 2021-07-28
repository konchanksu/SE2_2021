package forest;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import javax.annotation.processing.FilerException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import condition.ConditionException;
import forest.data.ForestData;
import forest.model.ForestModel;
import forest.repository.ForestDataRepository;
import forest.view.ForestView;

/**
 * 本プログラムの
 */
public class Example extends Object {

    public static void main(String[] arguments) throws FilerException, FileNotFoundException {
        ForestDataRepository aForestDataRepository = new ForestDataRepository();
        Example example = new Example();
        Consumer argumentExists = (aCondition) -> {
            Arrays.asList(arguments).forEach((String argument) -> {
                File aFile = new File(argument);
                try {
                    ForestData aForestData = aForestDataRepository.getForestData(aFile);
                    example.run(aFile.getName(), aForestData);
                } catch (IllegalArgumentException | NoSuchElementException | IOException e) {
                    e.printStackTrace();
                    example.showErrorMessage(e.getMessage());
                }
            });
        };

        Consumer argumentNotExists = (aCondition) -> {
            Boolean endFlag = true;
            new Condition(() -> endFlag).whileTrue(() -> {
                JFileChooser aJFileChooser = new JFileChooser();
                Integer selected = aJFileChooser.showOpenDialog(null);
                try {
                    new Condition(() -> selected == JFileChooser.CANCEL_OPTION).ifTrue((aCondition) -> {
                        System.out.println("ファイル選択がキャンセルされました");
                        aCondition._return_();
                        return;
                    });
                    new Condition(() -> selected == JFileChooser.ERROR_OPTION.ifTrue((aCondition) -> {
                        throw new FilerException("ファイル選択中にエラーが発生しました。");
                        return;
                    }));
                    new Condition(() -> selected == JFileChooser.APPROVE_OPTION).ifTrue(() -> {
                        try {
                            File aFile = aJFileChooser.getSelectedFile();
                            var aForestData = aForestDataRepository.getForestData(aFile);
                            example.run(aFile.getName(), aForestData);
                            endFlag = false;
                        } catch (IllegalArgumentException | NoSuchElementException | IOException e) {
                            e.printStackTrace();
                            example.showErrorMessage(e.getMessage());
                        }
                    });
                } catch (ConditionException anException) {
                    return;
                } catch (FilerException anException) {
                    example.showErrorMessage(anException.getMessage());
                    return;
                }
            });
        };
    }

    public void run(String fileName, ForestData forestData) {

        ForestModel aForestModel = new ForestModel();
        aForestModel.initialize(forestData);
        ForestView aForestView = new ForestView(aForestModel);
        JFrame aWindow = aForestView.getWindow();
        Dimension windowSize = new Dimension(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
        Dimension minWindowSize = new Dimension(Constant.WINDOW_WIDTH_MINIMUM, Constant.WINDOW_HEIGHT_MINIMUM);
        aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aWindow.setSize(windowSize);
        aWindow.setMinimumSize(minWindowSize);
        aWindow.setTitle(fileName);
        aWindow.setLocationRelativeTo(null);
        aWindow.setVisible(true);
        aForestModel.listNodes();
        aForestModel.animate();
    }

    private void showErrorMessage(String statement) {
        JOptionPane.showConfirmDialog(null, statement, "エラー", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        return;
    }
}
