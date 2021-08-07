package forest.repository;

import condition.Condition;
import condition.ConditionException;
import condition.ValueHolder;
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
 * <p>
 * trees:
 * XXX
 * branches:
 * YYY
 * nodes:
 * ZZZ
 *
 * @author RyogaYamauchi
 */
public class ForestDataRepository implements IForestDataRepository {

    /**
     * 読み込んできた.txtファイルの中に定義されている属性
     */
    private enum ForestDataType {
        /**
         * 木であるというタグ
         */
        trees,
        /**
         * ブランチであるというタグ
         */
        branches,
        /**
         * ノードであるというタグ
         */
        nodes
    }

    /**
     * FileクラスからForestDataクラスに変換する
     *
     * @param aFile 入力のファイル
     * @return ForestData ファイルの中身の情報を格納
     * @throws IOException              ファイル読み込み時例外
     * @throws IllegalArgumentException 変換時に想定していない例外
     * @throws NoSuchElementException   branchData作成時に指定されたnode idが見つからない例外
     */
    private ForestData convertForestData(File aFile) throws IOException, IllegalArgumentException, NoSuchElementException {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(aFile))) {
            ValueHolder<List<String>> aLines = new ValueHolder<List<String>>(new ArrayList<String>());
            reader.lines().forEach(str -> {
                aLines.get().add(str);
            });
            ValueHolder<ArrayList<String>> aNodeStringList = new ValueHolder<ArrayList<String>>(new ArrayList<String>());
            ValueHolder<ArrayList<String>> branchStringList = new ValueHolder<ArrayList<String>>(new ArrayList<String>());
            var aForestDataType = new Object() {
                ForestDataType value = null;
            };
            ValueHolder<ArrayList<ForestDataType>> foundForestDataTypeList = new ValueHolder<ArrayList<ForestDataType>>(new ArrayList<ForestDataType>());
            aLines.get().stream().forEach(data -> {
                try {
                    new Condition(() -> "trees:".equals(data)).ifTrue((aCondition) -> {
                        aCondition._return_();
                    });
                } catch (ConditionException anException) {
                    aForestDataType.value = ForestDataType.trees;
                    foundForestDataTypeList.get().add(ForestDataType.trees);
                    return;
                }
                try {
                    new Condition(() -> "branches:".equals(data)).ifTrue((aCondition) -> {
                        aCondition._return_();
                    });
                } catch (ConditionException anException) {
                    aForestDataType.value = ForestDataType.branches;
                    foundForestDataTypeList.get().add(ForestDataType.branches);
                    return;
                }
                try {
                    new Condition(() -> "nodes:".equals(data)).ifTrue((aCondition) -> {
                        aCondition._return_();
                    });
                } catch (ConditionException anException) {
                    aForestDataType.value = ForestDataType.nodes;
                    foundForestDataTypeList.get().add(ForestDataType.nodes);
                    return;
                }

                // if (aForestDataType.value == ForestDataType.trees) {
                //     ignore
                //}
                try {
                    new Condition(() -> aForestDataType.value == ForestDataType.branches).ifTrue((aCondition) -> {
                        aCondition._return_();
                    });
                } catch (ConditionException anException) {
                    branchStringList.get().add(data);
                    return;
                }

                try {
                    new Condition(() -> aForestDataType.value == ForestDataType.nodes).ifTrue((aCondition) -> {
                        aCondition._return_();
                    });
                } catch (ConditionException anException) {
                    aNodeStringList.get().add(data);
                    return;
                }
            });

            new Condition(() -> foundForestDataTypeList.get().stream().anyMatch(type -> type == ForestDataType.branches)).ifFalse(() -> {
                throw new IllegalArgumentException("Branchの属性データが読み取れませんでした");
            });

            new Condition(() -> foundForestDataTypeList.get().stream().anyMatch(type -> type == ForestDataType.nodes)).ifFalse(() -> {
                throw new IllegalArgumentException("Nodeの属性データが読み取れませんでした");
            });

            ValueHolder<List<NodeData>> aNodeList = new ValueHolder<List<NodeData>>(this.convertNodeData(aNodeStringList.get()));
            ValueHolder<List<BranchData>> aBranchList = new ValueHolder<List<BranchData>>(this.convertBranchData(branchStringList.get(), aNodeList.get()));

            return new ForestData(aNodeList.get(), aBranchList.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * String ListのnodeデータからNodeDataクラスにコンバートする
     *
     * @param nodeStringList 文字列のリストの状態となっているノード情報
     * @return NodeData String状態のnode dataの情報を格納
     * @throws IllegalArgumentException 変換時に想定していない例外
     */
    private List<NodeData> convertNodeData(List<String> nodeStringList) throws IllegalArgumentException {
        var nodeCount = nodeStringList.size();
        var errorSb = new StringBuilder();

        new Condition(() -> nodeCount >= Constant.MAX_NODE_COUNT).ifTrue(() -> {
            errorSb.append("ノードの数が多すぎます. count : ").append(nodeCount);
            throw new IllegalArgumentException(errorSb.toString());
        });

        return nodeStringList.stream().map(aBeforeConvert -> {
            var data = aBeforeConvert.split(",");

            new Condition(() -> data.length != 2).ifTrue(() -> {
                errorSb.append("Nodeの文字列フォーマットが正しくありません : ").append(data);
                throw new IllegalArgumentException(errorSb.toString());
            });


            var anId = data[0];
            var aName = data[1].trim();

            new Condition(() -> anId.isEmpty() || anId.isBlank() || aName.isBlank() || aName.isEmpty()).ifTrue(() -> {
                errorSb.append("Nodeの文字列フォーマットが正しくありません : ").append(aBeforeConvert.toString());
                throw new IllegalArgumentException(errorSb.toString());
            });

            new Condition(() -> aName.length() >= Constant.MAX_NODE_NAME_COUNT).ifTrue(() -> {
                errorSb.append("指定されたノードの名前が長すぎます. name : ").append(aName);
                throw new IllegalArgumentException(errorSb.toString());
            });

            return new NodeData(anId, aName);
        }).toList();
    }

    /**
     * String ListのbranchデータからBranchDataクラスにコンバートする
     *
     * @param branchStringList String List状態のbranch data
     * @param nodeList         変換後nodeList
     * @return BranchData String状態のbranch dataの情報を格納
     * @throws NoSuchElementException branchData作成時に指定されたnode idが見つからない例外
     */
    private List<BranchData> convertBranchData(List<String> branchStringList, List<NodeData> nodeList) throws NoSuchElementException {
        var map = nodeList.stream().collect(Collectors.toMap(data -> data.getId(), data -> data));
        var errorSb = new StringBuilder();

        return branchStringList.stream().map(beforeConvert -> {
            var ids = beforeConvert.split(",");

            new Condition(() -> ids.length != 2).ifTrue(() -> {
                errorSb.append("Branchの文字列フォーマットが正しくありません : ").append(ids);
                throw new IllegalArgumentException(errorSb.toString());
            });

            var fromNodeData = map.get(ids[0]);
            var toNodeData = map.get(ids[1].trim());

            new Condition(() -> fromNodeData == null || fromNodeData == null || toNodeData == null || toNodeData == null).ifTrue(() -> {
                errorSb.append("Branchの文字列フォーマットが正しくありません : ").append(beforeConvert.toString());
                throw new IllegalArgumentException(errorSb.toString());
            });

            return new BranchData(fromNodeData, toNodeData);
        }).toList();
    }

    /**
     * Fileクラスで渡された中身をForestDataに変換して返す
     *
     * @param aFile .txtファイル
     * @return ForestData ファイルの中身の情報を格納
     * @throws IOException              ファイル読み込み時例外
     * @throws IllegalArgumentException 変換時に想定していない例外
     * @throws NoSuchElementException   branchData作成時に指定されたnode idが見つからない例外
     */
    public ForestData getForestData(File aFile) throws IOException, IllegalArgumentException, NoSuchElementException {
        return this.convertForestData(aFile);
    }
}
