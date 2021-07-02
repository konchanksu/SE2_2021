package forest.repository;

import forest.data.ForestData;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * ForestDataを変換するリポジトリのinterface
 */
public interface IForestDataRepository {
	/**
	 * Fileクラスで渡された中身をForestDataに変換して返す
	 * @param aFile .txtファイル
	 * @return ForestData ファイルの中身の情報を格納
	 * @throws IOException ファイル読み込み時例外
	 * @throws IllegalArgumentException 変換時に想定していない例外
	 * @throws NoSuchElementException branchData作成時に指定されたnode idが見つからない例外
	 */
	public ForestData getForestData(File aFile) throws IOException, IllegalArgumentException, NoSuchElementException;
}
