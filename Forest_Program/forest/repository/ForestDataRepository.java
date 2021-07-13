package forest.repository;

import forest.Constant;
import forest.data.BranchData;
import forest.data.ForestData;
import forest.data.NodeData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * 以下に示すような.txtフィアルをForestDataクラスにコンバートするクラス
 *
 * trees:
 * XXX
 * branches:
 * YYY
 * nodes:
 * ZZZ
 * @author RyogaYamauchi
 */
public class ForestDataRepository implements IForestDataRepository {

    /**
     * 読み込んできた.txtファイルの中に定義されている属性
     */
    private enum ForestDataType{
        trees,
        branches,
        nodes
    }

    /**
     * FileクラスからForestDataクラスに変換する
     * @param aFile
     * @return ForestData ファイルの中身の情報を格納
     * @throws IOException ファイル読み込み時例外
     * @throws IllegalArgumentException 変換時に想定していない例外
     * @throws NoSuchElementException branchData作成時に指定されたnode idが見つからない例外
     */
    private ForestData convertForestData(File aFile) throws IOException, IllegalArgumentException, NoSuchElementException {
        var reader = new BufferedReader(new FileReader(aFile));
        try {
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
        finally {
            reader.close();
        }
        return null;
    }

    /**
     * String ListのnodeデータからNodeDataクラスにコンバートする
     * @param nodeStringList
     * @return NodeData String状態のnode dataの情報を格納
     * @throws IllegalArgumentException　変換時に想定していない例外
     */
    private List<NodeData> convertNodeData(List<String> nodeStringList)throws IllegalArgumentException  {
        var nodeCount = nodeStringList.size();
        var errorSb = new StringBuilder();
        if(nodeCount >= Constant.MAX_NODE_COUNT)
        {
            errorSb.append("ノードの数が多すぎます. count : ").append(nodeCount);
            throw new IllegalArgumentException(errorSb.toString());
        }

        return nodeStringList.stream().map(aBeforeConvert -> {
            var data = aBeforeConvert.split(",");
            var aId = data[0];
            var aName = data[1].trim();
            if (aName.length() >= Constant.MAX_NODE_NAME_COUNT) {
                errorSb.append("指定されたノードの名前が長すぎます. name : ").append(aName);
                throw new IllegalArgumentException(errorSb.toString());
            }

            var aNodeData = new NodeData(aId, aName);
            return aNodeData;
        }).toList();
    }

    /**
     *  String ListのbranchデータからBranchDataクラスにコンバートする
     * @param branchStringList String List状態のbranch data
     * @param nodeList 変換後nodeList
     * @return BranchData String状態のbranch dataの情報を格納
     * @throws NoSuchElementException branchData作成時に指定されたnode idが見つからない例外
     */
    private List<BranchData> convertBranchData(List<String> branchStringList, List<NodeData> nodeList) throws NoSuchElementException {
        var map = nodeList.stream().collect(Collectors.toMap(data -> data.getId(), data -> data));

        return branchStringList.stream().map(beforeConvert -> {
            var ids = beforeConvert.split(",");
            var fromNodeData = map.get(ids[0]);
            var toNodeData = map.get(ids[1].trim());
            var branch = new BranchData(fromNodeData, toNodeData);
            return branch;
        }).toList();
    }

    /**
     * Fileクラスで渡された中身をForestDataに変換して返す
     * @param aFile .txtファイル
     * @return ForestData ファイルの中身の情報を格納
     * @throws IOException ファイル読み込み時例外
     * @throws IllegalArgumentException 変換時に想定していない例外
     * @throws NoSuchElementException branchData作成時に指定されたnode idが見つからない例外
     */
    public ForestData getForestData(File aFile) throws IOException, IllegalArgumentException, NoSuchElementException {
        var aForestData = this.convertForestData(aFile);
        return aForestData;
    }
}
