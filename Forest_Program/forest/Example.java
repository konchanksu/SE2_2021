package forest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

import javax.annotation.processing.FilerException;
import javax.swing.JFileChooser;

import forest.data.ForestData;
import forest.model.ForestModel;
import forest.repository.ForestDataRepository;
import forest.repository.IForestDataRepository;

public class Example extends Object {

    private IForestDataRepository aForestDataRepository;

    public Example(IForestDataRepository anIForestDataRepository) {
        aForestDataRepository = anIForestDataRepository;
    }

    public static void main(String[] arguments) throws FilerException, FileNotFoundException {
        var aJFileChooser = new JFileChooser();
        var selected = aJFileChooser.showOpenDialog(null);

        if (selected == JFileChooser.CANCEL_OPTION) {
            System.out.println("ファイル選択がキャンセルされました");
            return;
        }

        if (selected == JFileChooser.ERROR_OPTION) {
            throw new FilerException("ファイル選択中にエラーが発生しました。");
        }

        if (selected == JFileChooser.APPROVE_OPTION) {
            var aFile = aJFileChooser.getSelectedFile();
            var aForestDataRepository = new ForestDataRepository();
            try {
                var aForestData = aForestDataRepository.getForestData(aFile);
                var example = new Example(aForestDataRepository);
                example.run(aForestData);
            } catch (FileNotFoundException e) {
                // TODO : 一旦即終了させ、後でファイルを再選択させる機能を実装する
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO : 一旦即終了させ、後でファイルを再選択させる機能を実装する
                e.printStackTrace();
            } catch (NoSuchElementException e) {
                // TODO : 一旦即終了させ、後でファイルを再選択させる機能を実装する
                e.printStackTrace();
            } catch (IOException e) {
                // TODO : 一旦即終了させ、後でファイルを再選択させる機能を実装する
                e.printStackTrace();
            }
        }
    }

    public void run(ForestData forestData) {
        ForestModel aModel = new ForestModel();
        aModel.initialize(forestData);
        System.out.println(aModel);

    }
}
