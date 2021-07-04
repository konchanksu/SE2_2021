package forest.tests;

import forest.data.BranchData;
import forest.data.ForestData;
import forest.data.NodeData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ForestDataに対するテスト
 * @author RyogaYamauchi
 */
public class ForestDataTest {
    /**
     * ForestDataに対してコンストラクタで設定したnodeListの値が正しく取得できるかのテスト
     */
    @Test
    public void コンストラクタで設定したnodeListと値がすべて一致する() {
        var startNodeData = new NodeData("id", "name");
        var endNodeData = new NodeData("id", "name");
        var branchData = new BranchData(startNodeData, endNodeData);
        var nodeList = new ArrayList<NodeData>();
        nodeList.add(startNodeData);
        nodeList.add(endNodeData);

        var branchList = new ArrayList<BranchData>();
        branchList.add(branchData);
        var forestData = new ForestData(nodeList, branchList);

        assertEquals(startNodeData, forestData.getNodeList().get(0));
        assertEquals(endNodeData, forestData.getNodeList().get(1));
    }
    /**
     * ForestDataに対してコンストラクタで設定したbranchListの値が正しく取得できるかのテスト
     */
    @Test
    public void コンストラクタで設定したbranchListと値がすべて一致する() {
        var startNodeData = new NodeData("id", "name");
        var endNodeData = new NodeData("id", "name");
        var branchData = new BranchData(startNodeData, endNodeData);
        var nodeList = new ArrayList<NodeData>();
        nodeList.add(startNodeData);
        nodeList.add(endNodeData);

        var branchList = new ArrayList<BranchData>();
        branchList.add(branchData);
        var forestData = new ForestData(nodeList, branchList);

        assertEquals(branchData, forestData.getBranchList().get(0));
    }
}
