package forest;

import forest.data.ForestData;
import forest.repository.ForestDataRepository;
import forest.repository.IForestDataRepository;

import javax.annotation.processing.FilerException;
import javax.swing.JFileChooser;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

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
            try
            {
                var aForestData = aForestDataRepository.getForestData(aFile);
                var example = new Example(aForestDataRepository);
                example.run(aForestData);
            }catch (FileNotFoundException e) {
                // TODO : 一旦即終了させ、後でファイルを再選択させる機能を実装する
                e.printStackTrace();
            }catch(IllegalArgumentException e) {
                // TODO : 一旦即終了させ、後でファイルを再選択させる機能を実装する
                e.printStackTrace();
            }
            catch (NoSuchElementException e) {
                // TODO : 一旦即終了させ、後でファイルを再選択させる機能を実装する
                e.printStackTrace();
            }
        }
    }

    public void run(ForestData forestData) {
        forestData.getBranchList().stream().forEach(x -> {
            System.out.println(x);
        });
        forestData.getNodeList().stream().forEach(x -> {
            System.out.println(x);
        });
    }
}
