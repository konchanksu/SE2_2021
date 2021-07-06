package forest.data;

/**
 * ノードの情報を格納するクラス
 * @RyogaYamauchi
 */
public class NodeData {

	/**
	 * ノードのID
	 */
	private String id;

	/**
	 * ノードの名前
	 */
	private String name;

	/**
	 * idとnameを格納する
	 * @param id
	 * @param name
	 */
	public NodeData(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * nameを取得する
	 * @return nodeの名前
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * idを取得する
	 * @return nodeのid
	 */
	public String getId() {
		return this.id;
	}

	@Override
	public String toString()
	{
		var sb = new StringBuilder();
		sb.append("id : ")
				.append(id)
				.append(", name : ")
				.append(name);
		return sb.toString();
	}
}
