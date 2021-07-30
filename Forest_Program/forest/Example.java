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

import condition.Condition;
import condition.ConditionException;
import forest.data.ForestData;
import forest.model.ForestModel;
import forest.repository.ForestDataRepository;
import forest.view.ForestView;

/**
 * 本プログラムの例題プログラム
 * @author Kodai Okayama
 */
public class Example extends Object {

    /**
     * このプログラムのエントリーポイント
     * @param arguments 引数
     * @throws FilerException ファイル選択中のエラー
     * @throws FileNotFoundException ファイルが存在しない場合
     */
    public static void main(String[] arguments) throws FilerException, FileNotFoundException {
        ForestDataRepository aForestDataRepository = new ForestDataRepository();
        Example example = new Example();

        //引数が存在した場合のラムダ式
        Consumer<Condition> argumentExists = (aCondition) -> {
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
            return;
        };

        //ファイルを選択する処理のラムダ式
        Consumer<Condition> selectProcess = (selectCondition) -> {
            JFileChooser aJFileChooser = new JFileChooser();
            Integer selected = aJFileChooser.showOpenDialog(null);
            try {
                new Condition(() -> selected == JFileChooser.CANCEL_OPTION).ifTrue((aCondition) -> {
                    System.out.println("ファイル選択がキャンセルされました");
                    aCondition._return_();
                    return;
                });

                new Condition(() -> selected == JFileChooser.ERROR_OPTION).ifTrue((aCondition) -> {
                    try {
                        throw new FilerException("ファイル選択中にエラーが発生しました。");
                    } catch (FilerException anException) {
                        anException.printStackTrace();
                        example.showErrorMessage(anException.getMessage());
                        aCondition._return_();
                    }
                });

                new Condition(() -> selected == JFileChooser.APPROVE_OPTION).ifTrue(() -> {
                    try {
                        File aFile = aJFileChooser.getSelectedFile();
                        var aForestData = aForestDataRepository.getForestData(aFile);
                        example.run(aFile.getName(), aForestData);
                        selectCondition._break_();
                    } catch (IllegalArgumentException | NoSuchElementException | IOException e) {
                        e.printStackTrace();
                        example.showErrorMessage(e.getMessage());
                    }
                });
            } catch (ConditionException anException) {
                selectCondition._return_();
            }
            return;
        };

        //引数が存在しない場合のラムダ式
        Consumer<Condition> argumentNotExists = (aCondition) -> {
            try {
                new Condition(() -> true).whileTrue(selectProcess);
            } catch (ConditionException anException) {
                return;
            }
        };

        new Condition(() -> arguments.length > 0).ifThenElse(argumentExists, argumentNotExists);
        return;
    }

    /**
     * 木構造のデータをモデルに対して読み込ませ、樹状整列のアニメーションを実行する
     * @param fileName ファイル名
     * @param forestData 木構造のデータ
     */
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
        return;
    }

    /**
     * エラー文のダイアログを出力する
     * @param statement エラー文
     */
    private void showErrorMessage(String statement) {
        JOptionPane.showConfirmDialog(null, statement, "エラー", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        return;
    }
}
