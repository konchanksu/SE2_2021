package forest.tests;

import forest.data.NodeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * NodeDataに対するテスト
 */
public class NodeDataTest {
    /**
     * NodeDataに対してコンストラクタで設定したidの値が正しく取得できるかのテスト
     */
    @Test
    public void コンストラクタで設定したidが値と一致する()
    {
        var nodeData = new NodeData("id", "name");
        assertEquals("id", nodeData.getId());
    }

    /**
     * NodeDataに対してコンストラクタで設定したnameの値が正しく取得できるかのテスト
     */
    @Test
    public void コンストラクタで設定したnameが値と一致する()
    {
        var nodeData = new NodeData("id", "name");
        assertEquals("name", nodeData.getName());
    }
}
