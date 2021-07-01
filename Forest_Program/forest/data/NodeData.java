package forest.data;

public class NodeData {

	private String id;
	private String name;

	public NodeData(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
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
