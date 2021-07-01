package forest.repository;

import forest.data.BranchData;
import forest.data.ForestData;
import forest.data.NodeData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ForestDataRepository implements IForestDataRepository {

    private enum ForestDataType{
        trees,
        branches,
        nodes
    }

    private ForestData convertForestData(File aFile) throws FileNotFoundException, IllegalArgumentException, NoSuchElementException {
        try (BufferedReader reader = new BufferedReader(new FileReader(aFile))) {
            String str;
            List<String> aLines = new ArrayList<String>();
            while ((str = reader.readLine()) != null) {
                aLines.add(str);
            }
            var aNodeStringList = new ArrayList<String>();
            var branchStringList = new ArrayList<String>();
            var aForestDataType = new Object() {
                ForestDataType value =  null;
            };
            aLines.stream().forEach(data -> {
                if (data.equals("trees:")) {
                    aForestDataType.value = ForestDataType.trees;
                    return;
                } else if (data.equals("branches:")) {
                    aForestDataType.value = ForestDataType.branches;
                    return;
                } else if (data.equals("nodes:")) {
                    aForestDataType.value = ForestDataType.nodes;
                    return;
                }

                if (aForestDataType.value == ForestDataType.trees) {
                    // ignore
                } else if (aForestDataType.value == ForestDataType.branches) {
                    branchStringList.add(data);
                } else if ( aForestDataType.value == ForestDataType.nodes) {
                    aNodeStringList.add(data);
                }
            });
            var aNodeList = this.convertNodeData(aNodeStringList);
            var aBranchList = this.convertBranchData(branchStringList, aNodeList);
            return new ForestData(aNodeList, aBranchList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<NodeData> convertNodeData(List<String> nodeStringList)throws IllegalArgumentException  {
        return nodeStringList.stream().map(aBeforeConvert -> {
            var data = aBeforeConvert.split(",");
            var aId = data[0];
            if (!aId.chars().allMatch(Character::isDigit)) {
                throw new IllegalArgumentException("指定されたIdが数値ではありませんでした. id : " + aId);
            }
            var aName = data[1].trim();
            if (aName.length() > 50) {
                throw new IllegalArgumentException("指定されたノードの名前が長すぎます. name : " + aName);
            }
            var aNodeData = new NodeData(aId, aName);
            return aNodeData;
        }).toList();
    }

    private List<BranchData> convertBranchData(List<String> branchStringList, List<NodeData> nodeList) throws NoSuchElementException {
        var map = nodeList.stream().collect(Collectors.toMap(data -> data.getId(), data -> data));

        return branchStringList.stream().map(beforeConvert -> {
            var ids = beforeConvert.split(",");
            var fromNodeData = map.get(ids[0]);
            var toNodeData = map.get(ids[1]);
            var branch = new BranchData(fromNodeData, toNodeData);
            return branch;
        }).toList();
    }

    public ForestData getForestData(File aFile) throws FileNotFoundException, IllegalArgumentException, NoSuchElementException {
        var aForestData = this.convertForestData(aFile);
        return aForestData;
    }
}
