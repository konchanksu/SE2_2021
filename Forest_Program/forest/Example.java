package forest;

import forest.data.ForestData;
import forest.repository.ForestDataRepository;
import forest.repository.IForestDataRepository;

import javax.annotation.processing.FilerException;
import javax.swing.JFileChooser;
import java.io.File;

public class Example extends Object{

	private IForestDataRepository aForestDataRepository;

	public Example(IForestDataRepository anIForestDataRepository) {
		aForestDataRepository = anIForestDataRepository;
	}


	public static void main(String[] arguments) throws FilerException {
		var aFileChooser = new FileChooser();
		var selected = aFileChooser.showOpenDialog(aFileChooser);

		if(selected == JFileChooser.CANCEL_OPTION)
		{
			System.out.println("ファイル選択がキャンセルされました");
			return;
		}

		if(selected == JFileChooser.ERROR_OPTION)
		{
			throw new FilerException("ファイル選択中にエラーが発生しました。");
		}

		if(selected == JFileChooser.APPROVE_OPTION)
		{
			var fileName = aFileChooser.getName();
			var file = new File(fileName);
			var aForestDataRepository = new ForestDataRepository(file);
			var forestData = aForestDataRepository.getForestData();
			var example = new Example(aForestDataRepository);
			example.run(forestData);
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
