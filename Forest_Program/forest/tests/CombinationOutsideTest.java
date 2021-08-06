package forest.tests;

import forest.data.BranchData;
import forest.data.ForestData;
import forest.data.NodeData;

import forest.model.ForestModel;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ForestDataとForestModelに対するテスト
 * @author Ryutaro Kajiwara
 */
public class CombinationOutsideTest {
        /**
         * BranchDataの先頭NodeのidとBranchModelの先頭Nodeのidが合致しているか
         * の確認
         * @author Ryutaro Kajiwara
         */
        @Test
        public void BranchDataの先頭NodeのidとBranchModelの先頭Nodeのidが合致しているか() {
                var startNodeData = new NodeData("id_1", "name_1");
                var endNodeData = new NodeData("id_2", "name_2");
                var nodeList = new ArrayList<NodeData>();
                nodeList.add(startNodeData);
                nodeList.add(endNodeData);

                var branchData = new BranchData(startNodeData, endNodeData);
                var branchList = new ArrayList<BranchData>();
                branchList.add(branchData);

                ForestData forestData = new ForestData(nodeList, branchList);

                ForestModel forestModel = new ForestModel();
                forestModel.initialize(forestData);

                //assertEquals(branchData.getStart().getId(), forestModel.getNodes().get(0).getId());
                assertEquals(branchData.getStart().getId(), forestModel.getBranches().get(0).getStart().getId());
        }

        /**
         * BranchDataの先頭NodeのnameとBranchModelの先頭Nodeのnameが合致しているか
         * の確認
         * @author Ryutaro Kajiwara
         */
        @Test
        public void BranchDataの先頭NodeのnameとBranchModelの先頭Nodeのnameが合致しているか() {
                var startNodeData = new NodeData("id_1", "name_1");
                var endNodeData = new NodeData("id_2", "name_2");
                var nodeList = new ArrayList<NodeData>();
                nodeList.add(startNodeData);
                nodeList.add(endNodeData);

                var branchData = new BranchData(startNodeData, endNodeData);
                var branchList = new ArrayList<BranchData>();
                branchList.add(branchData);

                ForestData forestData = new ForestData(nodeList, branchList);

                ForestModel forestModel = new ForestModel();
                forestModel.initialize(forestData);

                assertEquals(branchData.getStart().getName(), forestModel.getBranches().get(0).getStart().getName());
        }

        /**
         * BranchDataの最後のNodeのnameとBranchModelの最後のNodeのidが合致しているか
         * の確認
         * @author Ryutaro Kajiwara
         */
        @Test
        public void BranchDataの最後のNodeのnameとBranchModelの最後のNodeのidが合致しているか() {
                var startNodeData = new NodeData("id_1", "name_1");
                var endNodeData = new NodeData("id_2", "name_2");
                var nodeList = new ArrayList<NodeData>();
                nodeList.add(startNodeData);
                nodeList.add(endNodeData);

                var branchData = new BranchData(startNodeData, endNodeData);
                var branchList = new ArrayList<BranchData>();
                branchList.add(branchData);

                ForestData forestData = new ForestData(nodeList, branchList);

                ForestModel forestModel = new ForestModel();
                forestModel.initialize(forestData);

                assertEquals(branchData.getEnd().getId(), forestModel.getBranches().get(0).getEnd().getId());
        }

        /**
         * BranchDataの最後のNodeのnameとBranchModelの最後のNodeのnameが合致しているか
         * の確認
         * @author Ryutaro Kajiwara
         */
        @Test
        public void BranchDataの最後のNodeのnameとBranchModelの最後のNodeのnameが合致しているか() {
                var startNodeData = new NodeData("id_1", "name_1");
                var endNodeData = new NodeData("id_2", "name_2");
                var nodeList = new ArrayList<NodeData>();
                nodeList.add(startNodeData);
                nodeList.add(endNodeData);

                var branchData = new BranchData(startNodeData, endNodeData);
                var branchList = new ArrayList<BranchData>();
                branchList.add(branchData);

                ForestData forestData = new ForestData(nodeList, branchList);

                ForestModel forestModel = new ForestModel();
                forestModel.initialize(forestData);

                assertEquals(branchData.getEnd().getName(), forestModel.getBranches().get(0).getEnd().getName());
        }
}