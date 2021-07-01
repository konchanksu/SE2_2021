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

public class ForestDataRepository implements IForestDataRepository {

    private ForestData ConvertForestData(File aFile) throws FileNotFoundException, IllegalArgumentException, NoSuchElementException {
        try (BufferedReader reader = new BufferedReader(new FileReader(aFile))) {
            String str;
            List<String> aLines = new ArrayList<String>();
            while ((str = reader.readLine()) != null) {
                aLines.add(str);
            }
            var nodeStringList = new ArrayList<String>();
            var branchStringList = new ArrayList<String>();
            var state = new Object() {
                Integer value = 1;
            };
            aLines.stream().forEach(x -> {
                if (x.equals("trees:")) {
                    state.value = 1;
                    return;
                } else if (x.equals("branches:")) {
                    state.value = 2;
                    return;
                } else if (x.equals("nodes:")) {
                    state.value = 3;
                    return;
                }

                if (state.value == 1) {
                    // ignore
                } else if (state.value == 2) {
                    branchStringList.add(x);
                } else if (state.value == 3) {
                    nodeStringList.add(x);
                }
            });
            var nodeList = convertNodeData(nodeStringList);
            var branchList = convertBranchData(branchStringList, nodeList);
            return new ForestData(nodeList, branchList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<NodeData> convertNodeData(List<String> nodeStringList)throws IllegalArgumentException  {

        return nodeStringList.stream().map(x -> {
            var data = x.split(",");
            var id = data[0];
            if (!id.chars().allMatch(Character::isDigit)) {
                throw new IllegalArgumentException("指定されたIdが数値ではありませんでした. id : " + id);
            }
            var name = data[1].trim();
            if (name.length() > 50) {
                throw new IllegalArgumentException("指定されたノードの名前が長すぎます. name : " + name);
            }
            var nodeData = new NodeData(id, name);
            return nodeData;
        }).toList();
    }

    private List<BranchData> convertBranchData(List<String> branchStringList, List<NodeData> nodeList) throws NoSuchElementException {
        return branchStringList.stream().map(x -> {
            var ids = x.split(",");
            var from = nodeList.stream().filter(nodeData -> nodeData.getId().equals(ids[0])).findFirst();
            var to = nodeList.stream().filter(nodeData -> nodeData.getId().equals(ids[1].trim())).findFirst();
            var branch = new BranchData(from.get(), to.get());
            return branch;
        }).toList();
    }

    public ForestData getForestData(File aFile) throws FileNotFoundException, IllegalArgumentException, NoSuchElementException {
        var aForestData = ConvertForestData(aFile);
        return aForestData;
    }
}
