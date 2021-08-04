package forest.tests;

import forest.data.BranchData;
import forest.data.ForestData;
import forest.data.NodeData;

import forest.model.BranchModel;
import forest.model.NodeModel;
import forest.model.ForestModel;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ForestDataとForestModelに対するテスト
 * 一度ForestDataを作ってから、確認する
 * @author Ryutaro Kajiwara
 */
public class CombinationOutsideTest {
        /**
         * ForestData のBranchとNodeの内容が
         * ForestModelのBranchModelとNodeModelの内容とが合致しているか
         */
        @Test
        public void ForestDataのbranchDataとForestModelのbranchModelの最初の内容が合致しているか() {
                var startNodeData = new NodeData("id_1", "name_1");
                var endNodeData = new NodeData("id_2", "name_2");
                var nodeList = new ArrayList<NodeData>();
                nodeList.add(startNodeData);
                nodeList.add(endNodeData);

                var branchData = new BranchData(startNodeData, endNodeData);
                var branchList = new ArrayList<BranchData>();
                branchList.add(branchData);

                ForestData forestData = new ForestData(nodeList, branchList);

                List<NodeData> nodeModel = new ArrayList<NodeData>();
                nodeModel.add(forestData.getNodeList().get(0));
                nodeModel.add(forestData.getNodeList().get(1));
                var branchModel = new BranchData(nodeModel.get(0), nodeModel.get(1));

                assertEquals(startNodeData, branchModel.getStart());
        }

        @Test
        public void ForestDataのbranchDataとForestModelのbranchModelの最後の内容が合致しているか() {
                var startNodeData = new NodeData("id_1", "name_1");
                var endNodeData = new NodeData("id_2", "name_2");
                var nodeList = new ArrayList<NodeData>();
                nodeList.add(startNodeData);
                nodeList.add(endNodeData);

                var branchData = new BranchData(startNodeData, endNodeData);
                var branchList = new ArrayList<BranchData>();
                branchList.add(branchData);

                ForestData forestData = new ForestData(nodeList, branchList);

                List<NodeData> nodeModel = new ArrayList<NodeData>();
                nodeModel.add(forestData.getNodeList().get(0));
                nodeModel.add(forestData.getNodeList().get(1));
                var branchModel = new BranchData(nodeModel.get(0), nodeModel.get(1));

                assertEquals(endNodeData, branchModel.getEnd());
        }

        @Test
        public void ForestDataのnodeDataとForestModelのnodeModelのidが合致しているか() {
                var startNodeData = new NodeData("id_1", "name_1");
                var endNodeData = new NodeData("id_2", "name_2");
                var nodeList = new ArrayList<NodeData>();
                nodeList.add(startNodeData);
                nodeList.add(endNodeData);

                var branchData = new BranchData(startNodeData, endNodeData);
                var branchList = new ArrayList<BranchData>();
                branchList.add(branchData);

                ForestData forestData = new ForestData(nodeList, branchList);

                List<NodeData> nodeModel = new ArrayList<NodeData>();
                nodeModel.add(forestData.getNodeList().get(0));
                nodeModel.add(forestData.getNodeList().get(1));
                var branchModel = new BranchData(nodeModel.get(0), nodeModel.get(1));

                assertEquals("id_1", forestData.getNodeList().get(0).getId());
        }

        @Test
        public void ForestDataのnodeDataとForestModelのNodenodelのnameが合致しているか() {
                var startNodeData = new NodeData("id_1", "name_1");
                var endNodeData = new NodeData("id_2", "name_2");
                var nodeList = new ArrayList<NodeData>();
                nodeList.add(startNodeData);
                nodeList.add(endNodeData);

                var branchData = new BranchData(startNodeData, endNodeData);
                var branchList = new ArrayList<BranchData>();
                branchList.add(branchData);

                ForestData forestData = new ForestData(nodeList, branchList);

                List<NodeData> nodeModel = new ArrayList<NodeData>();
                nodeModel.add(forestData.getNodeList().get(0));
                nodeModel.add(forestData.getNodeList().get(1));
                var branchModel = new BranchData(nodeModel.get(0), nodeModel.get(1));

                assertEquals("name_1", forestData.getNodeList().get(0).getName());
        }
}