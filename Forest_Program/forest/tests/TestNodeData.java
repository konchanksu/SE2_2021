package forest.tests;

import forest.data.NodeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNodeData {
    @Test
    public void コンストラクタで設定したidが値と一致する()
    {
        var nodeData = new NodeData("id", "name");
        assertEquals("id", nodeData.getId());
    }

    @Test
    public void コンストラクタで設定したnameが値と一致する()
    {
        var nodeData = new NodeData("id", "name");
        assertEquals("name", nodeData.getName());
    }
}
