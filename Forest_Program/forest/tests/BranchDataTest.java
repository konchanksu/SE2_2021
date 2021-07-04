package forest.tests;

import forest.data.BranchData;
import forest.data.NodeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * BranchDataに対するテスト
 * @author RyogaYamauchi
 */
public class BranchDataTest {
    /**
     * BranchDataに対してコンストラクタで設定したendNodeDataの値が正しく取得できるかのテスト
     */
    @Test
    public void コンストラクタで設定したendNodeDataと値が一致する()
    {
        var startNodeData = new NodeData("id", "name");
        var endNodeData = new NodeData("id", "name");
        var branshData = new BranchData(startNodeData, endNodeData);
        assertEquals(endNodeData, branshData.getEnd());
    }

    /**
     * BranchDataに対してコンストラクタで設定したstartNodeDataの値が正しく取得できるかのテスト
     */
    @Test
    public void コンストラクタで設定したstartNodeDataと値が一致する()
    {
        var startNodeData = new NodeData("id", "name");
        var endNodeData = new NodeData("id", "name");
        var branshData = new BranchData(startNodeData, endNodeData);
        assertEquals(startNodeData, branshData.getStart());
    }
}
