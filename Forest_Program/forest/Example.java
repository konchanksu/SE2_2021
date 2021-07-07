package forest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

import javax.annotation.processing.FilerException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import forest.data.ForestData;
import forest.model.ForestModel;
import forest.repository.ForestDataRepository;
import forest.repository.IForestDataRepository;
import forest.view.ForestView;

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

        // Example anExample = new Example(new MockForestDataRepository(new File("")));
        // List<NodeData> aNodeList = new ArrayList<>();
        // List<BranchData> aBranchList = new ArrayList<>();
        // aNodeList.add(new NodeData("1", "temp1"));
        // aNodeList.add(new NodeData("2", "temp2"));
        // aBranchList.add(new BranchData(aNodeList.get(0), aNodeList.get(1)));
        // anExample.run(new ForestData(aNodeList, aBranchList));
    }

    public void run(ForestData forestData) {
        ForestModel aForestModel = new ForestModel();
        aForestModel.initialize(forestData);
        ForestView aForestView = new ForestView(aForestModel);

        JFrame aWindow = aForestView.getWindow();
        aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aWindow.setSize(800, 600);
        aWindow.setLocationRelativeTo(null);
        aWindow.setVisible(true);

        System.out.println(aForestModel);
        aForestModel.listNodes();
        aForestModel.animate();
    }
}
