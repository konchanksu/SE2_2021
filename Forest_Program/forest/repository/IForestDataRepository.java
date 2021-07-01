package forest.repository;

import forest.data.ForestData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

public interface IForestDataRepository {
	public ForestData getForestData(File aFile) throws IOException, IllegalArgumentException, NoSuchElementException;
}
