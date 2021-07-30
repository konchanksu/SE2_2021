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
        /** 木であるというタグ */
        trees,
        /** ブランチであるというタグ */
        branches,
        /** ノードであるというタグ */
        nodes
    }

    /**
     * FileクラスからForestDataクラスに変換する
     * @param aFile 入力のファイル
     * @return ForestData ファイルの中身の情報を格納
     * @throws IOException ファイル読み込み時例外
     * @throws IllegalArgumentException 変換時に想定していない例外
     * @throws NoSuchElementException branchData作成時に指定されたnode idが見つからない例外
     */
    private ForestData convertForestData(File aFile) throws IOException, IllegalArgumentException, NoSuchElementException {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(aFile))){
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
            var foundForestDataTypeList = new ArrayList<ForestDataType>();
            aLines.stream().forEach(data -> {
                if ("trees:".equals(data)) {
                    aForestDataType.value = ForestDataType.trees;
                    foundForestDataTypeList.add(ForestDataType.trees);
                    return;
                } else if ("branches:".equals(data)) {
                    aForestDataType.value = ForestDataType.branches;
                    foundForestDataTypeList.add(ForestDataType.branches);
                    return;
                } else if ("nodes:".equals(data)) {
                    aForestDataType.value = ForestDataType.nodes;
                    foundForestDataTypeList.add(ForestDataType.nodes);
                    return;
                }

                // if (aForestDataType.value == ForestDataType.trees) {
                //     ignore
                //}
                if (aForestDataType.value == ForestDataType.branches) {
                    branchStringList.add(data);
                } else if ( aForestDataType.value == ForestDataType.nodes) {
                    aNodeStringList.add(data);
                }
            });

            if(!foundForestDataTypeList.stream().anyMatch(type -> type == ForestDataType.branches)){
                throw new IllegalArgumentException("Branchの属性データが読み取れませんでした");
            }

            if(!foundForestDataTypeList.stream().anyMatch(type -> type == ForestDataType.nodes)) {
                throw new IllegalArgumentException("Nodeの属性データが読み取れませんでした");
            }

            var aNodeList = this.convertNodeData(aNodeStringList);
            var aBranchList = this.convertBranchData(branchStringList, aNodeList);

            return new ForestData(aNodeList, aBranchList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * String ListのnodeデータからNodeDataクラスにコンバートする
     * @param nodeStringList 文字列のリストの状態となっているノード情報
     * @return NodeData String状態のnode dataの情報を格納
     * @throws IllegalArgumentException 変換時に想定していない例外
     */
    private List<NodeData> convertNodeData(List<String> nodeStringList) throws IllegalArgumentException  {
        var nodeCount = nodeStringList.size();
        var errorSb = new StringBuilder();
        if(nodeCount >= Constant.MAX_NODE_COUNT)
        {
            errorSb.append("ノードの数が多すぎます. count : ").append(nodeCount);
            throw new IllegalArgumentException(errorSb.toString());
        }

        return nodeStringList.stream().map(aBeforeConvert -> {
            var data = aBeforeConvert.split(",");

            if(data.length != 2)
            {
                errorSb.append("Nodeの文字列フォーマットが正しくありません : ").append(data);
                throw new IllegalArgumentException(errorSb.toString());
            }
            var anId = data[0];
            var aName = data[1].trim();

            if(anId.isEmpty() || anId.isBlank() || aName.isBlank() || aName.isEmpty())
            {
                errorSb.append("Nodeの文字列フォーマットが正しくありません : ").append(aBeforeConvert.toString());
                throw new IllegalArgumentException(errorSb.toString());
            }

            if (aName.length() >= Constant.MAX_NODE_NAME_COUNT) {
                errorSb.append("指定されたノードの名前が長すぎます. name : ").append(aName);
                throw new IllegalArgumentException(errorSb.toString());
            }

            return new NodeData(anId, aName);
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
        var errorSb = new StringBuilder();

        return branchStringList.stream().map(beforeConvert -> {
            var ids = beforeConvert.split(",");
            if(ids.length != 2)
            {
                errorSb.append("Branchの文字列フォーマットが正しくありません : ").append(ids);
                throw new IllegalArgumentException(errorSb.toString());
            }
            var fromNodeData = map.get(ids[0]);
            var toNodeData = map.get(ids[1].trim());

            if(fromNodeData == null || fromNodeData == null || toNodeData == null || toNodeData == null)
            {
                errorSb.append("Branchの文字列フォーマットが正しくありません : ").append(beforeConvert.toString());
                throw new IllegalArgumentException(errorSb.toString());
            }


            return new BranchData(fromNodeData, toNodeData);
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
        return this.convertForestData(aFile);
    }
}
