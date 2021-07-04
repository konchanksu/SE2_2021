package forest.repository;

import forest.data.ForestData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class MockForestDataRepository implements IForestDataRepository {

	public MockForestDataRepository(File aFile) {

	}

	public ForestData getForestData() {
		return null;
	}

	private Boolean validation(List<String> aList) {
		return null;
	}

	@Override
	public ForestData getForestData(File aFile) throws FileNotFoundException { return null; }
}
