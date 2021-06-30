package forest.repository;

import forest.data.ForestData;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IForestDataRepository {

	public ForestData getForestData() throws FileNotFoundException, IOException;

}
