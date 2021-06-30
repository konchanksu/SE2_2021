package forest.repository;

import forest.data.ForestData;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ForestDataRepository implements IForestDataRepository {
	private ForestData aForestData;
	private File aFile;

	private void ConvertForestData(File aFile) throws FileNotFoundException {
		try (BufferedReader reader = new BufferedReader(new FileReader(aFile))){
			String str;
			List<String> aLines = new ArrayList<String>();
			while ((str = reader.readLine()) != null) {
				aLines.add(str);
			}
			aLines.stream().forEach(x -> System.out.println(x));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ForestDataRepository(File aFile) {
		this.aFile = aFile;
	}

	public ForestData getForestData() throws FileNotFoundException {
		if(!validation(this.aFile))
		{
			throw new IllegalArgumentException("指定されたファイルが読み込めませんでした");
		}
		if(aForestData == null)
		{
			ConvertForestData(this.aFile);
		}
		return aForestData;
	}

	private Boolean validation(File aFile) {
		return true;
	}

}
