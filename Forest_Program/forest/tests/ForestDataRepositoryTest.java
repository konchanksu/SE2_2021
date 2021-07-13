package forest.tests;

import forest.data.BranchData;
import forest.data.NodeData;
import forest.repository.ForestDataRepository;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * ForestDataRepositoryに対するテスト
 * @author RyogaYamauchi
 */
public class ForestDataRepositoryTest {
    /**
     * 文字列からForestDataに正しく変換できるかのテスト
     * @throws IOException
     */
    @Test
    public void 正しくForestDataを変換できるかTest() throws IOException {
        var data = new StringBuilder();
        data.append("trees:").append(System.lineSeparator())
                .append("Object").append(System.lineSeparator())
                .append("nodes:").append(System.lineSeparator())
                .append("1, Magnitude").append(System.lineSeparator())
                .append("2, ArithmeticValue").append(System.lineSeparator())
                .append("branches:").append(System.lineSeparator())
                .append("1, 2").append(System.lineSeparator());
        var path = "./temp.txt";
        var aFileWriter = new FileWriter(path);
        aFileWriter.write(data.toString());
        aFileWriter.close();
        var aFile = new File(path);
        var aForestDataRepository = new ForestDataRepository();
        var aForestData = aForestDataRepository.getForestData(aFile);
        var branchArray = aForestData.getBranchList().toArray(new BranchData[]{});
        var nodeArray = aForestData.getNodeList().toArray(new NodeData[]{});

        assertEquals("1", nodeArray[0].getId());
        assertEquals("Magnitude", nodeArray[0].getName());

        assertEquals("2", nodeArray[1].getId());
        assertEquals("ArithmeticValue", nodeArray[1].getName());

        assertEquals( nodeArray[0], branchArray[0].getStart());
        assertEquals( nodeArray[1], branchArray[0].getEnd());

        Files.delete(Paths.get(path));
    }

    /**
     * 文字列からForestDataに変換する際にnodeの名前が長すぎる場合に発生する想定されたエラーに対するテスト
     * @throws IOException ファイル入出力時に発生する想定しないException
     */
    @Test
    public void Nodeの名前が100文字以上の例外をキャッチできるかテスト() throws IOException {
        var data = new StringBuilder();
        data.append("trees:").append(System.lineSeparator())
                .append("Object").append(System.lineSeparator())
                .append("nodes:").append(System.lineSeparator())
                .append("1, abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuv").append(System.lineSeparator()) // 100文字
                .append("2, ArithmeticValue").append(System.lineSeparator())
                .append("branches:").append(System.lineSeparator())
                .append("1, 2").append(System.lineSeparator());
        var path = "./temp.txt";
        var aFileWriter = new FileWriter(path);
        aFileWriter.write(data.toString());
        aFileWriter.close();
        var aFile = new File(path);
        var aForestDataRepository = new ForestDataRepository();
        try{
            var aForestData = aForestDataRepository.getForestData(aFile);
            fail();
        }catch (IllegalArgumentException e) {
            assertEquals("java.lang.IllegalArgumentException: 指定されたノードの名前が長すぎます. name : abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuv", e.toString());
        }
        finally {
            Files.delete(Paths.get(path));
        }
    }

    /**
     * Nodeの個数が10000以上の場合に発生する想定されたエラーに対するテスト
     * @Throws IOException ファイル入出力時に発生する想定しないException
     */
    @Test
    public void Nodeの個数が10000以上の例外をキャッチできるかテスト() throws IOException{
        var data = new StringBuilder();
        data.append("trees:").append(System.lineSeparator())
                .append("Object").append(System.lineSeparator())
                .append("nodes:").append(System.lineSeparator());

        var tempSb = new StringBuilder();
        for(Integer i = 0; i < 10000; i++)
        {
            tempSb.append(i.toString())
                    .append(", ")
                    .append(i.toString())
            .append(System.lineSeparator());
        }
        data.append(tempSb.toString());
        data.append("branches:").append(System.lineSeparator())
                .append("1, 2").append(System.lineSeparator());

        var path = "./temp.txt";
        var aFileWriter = new FileWriter(path);
        aFileWriter.write(data.toString());
        aFileWriter.close();
        var aFile = new File(path);
        var aForestDataRepository = new ForestDataRepository();
        try{
            var aForestData = aForestDataRepository.getForestData(aFile);
            fail();
        }catch (IllegalArgumentException e) {
            System.out.print(e);
            assertEquals("java.lang.IllegalArgumentException: ノードの数が多すぎます. count : 10000", e.toString());
        }
        finally {
            Files.delete(Paths.get(path));
        }
    }
    /**
     * Nodeの文字列形式がフォーマット通りではなかった場合のエラーに対するテスト
     * @Throws IOException ファイル入出力時に発生する想定しないException
     */
    @Test
    public void Nodeの文字列形式がフォーマット通りではなかった場合の例外をキャッチできるかテスト() throws IOException {
        var data = new StringBuilder();
        data.append("trees:").append(System.lineSeparator())
                .append("Object").append(System.lineSeparator())
                .append("nodes:").append(System.lineSeparator())
                .append("1, ").append(System.lineSeparator()) // エラーNode
                .append("2, ABC").append(System.lineSeparator())
                .append("branches:").append(System.lineSeparator())
                .append("1, 2").append(System.lineSeparator());
        var path = "./temp.txt";
        var aFileWriter = new FileWriter(path);
        aFileWriter.write(data.toString());
        aFileWriter.close();
        var aFile = new File(path);
        var aForestDataRepository = new ForestDataRepository();
        try{
            var aForestData = aForestDataRepository.getForestData(aFile);
            fail();
        }catch (IllegalArgumentException e) {
            assertEquals("java.lang.IllegalArgumentException: Nodeの文字列フォーマットが正しくありません : 1, ", e.toString());
        }
        finally {
            Files.delete(Paths.get(path));
        }
    }
}
