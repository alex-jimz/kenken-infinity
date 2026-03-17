package LogicTier.Translator;
import org.junit.Test;

import LogicTier.Region.Region;

import java.util.*;
import static org.junit.Assert.assertEquals;
import LogicTier.TestRunner.TestRunner;

public class TranslatorTest {

    public static void main(String[] args) {
        TestRunner.runTestClass(TranslatorTest.class);
    }

    @Test
    public void testTranslateKenken() {
        int N = 2;
        int R = 2;
        Translator translator = new Translator();
        Vector<Vector<Integer>> kenkenIntegerFormat = new Vector<>();

        Vector<Integer> header = new Vector<>(Arrays.asList(N, R));
        kenkenIntegerFormat.add(header);
        Vector<Integer> region1 = new Vector<>(Arrays.asList(1, 1, 2, 1, 1, 1, 2));
        Vector<Integer> region2 = new Vector<>(Arrays.asList(1, 1, 2, 2, 1, 2, 2));
        kenkenIntegerFormat.add(region1);
        kenkenIntegerFormat.add(region2);

        Vector <Region> RegionFormat = new Vector<Region>();
        RegionFormat = translator.translateKenken(kenkenIntegerFormat);
        int[][] RegionBoard = translator.getRegionBoard();
        int[][] RegionInfo = translator.getRegionInfo();

        Vector<Region> expectedRegionFormat = new Vector<>();
        Vector<Integer> positions1 = new Vector<>(Arrays.asList(0, 0, 0, 1));
        Vector<Integer> positions2 = new Vector<>(Arrays.asList( 1, 0, 1, 1));
        Region region1Expected = new Region(1, 1, positions1);
        Region region2Expected = new Region(1, 1, positions2);

        expectedRegionFormat.add(region1Expected);
        expectedRegionFormat.add(region2Expected);

        Vector<Vector<Integer>> expectedRegionBoard = new Vector<>();
        Vector<Integer> ExpectedBoardRow1 = new Vector<>(Arrays.asList(0, 0));
        Vector<Integer> ExpectedBoardRow2 = new Vector<>(Arrays.asList( 1, 1));

        expectedRegionBoard.add(ExpectedBoardRow1);
        expectedRegionBoard.add(ExpectedBoardRow2);

        Vector<Vector<Integer>> expectedRegionInfo = new Vector<>();
        Vector<Integer> ExpectedInfoRow1 = new Vector<>(Arrays.asList(1, 1));
        Vector<Integer> ExpectedInfoRow2 = new Vector<>(Arrays.asList( 1, 1));

        expectedRegionInfo.add(ExpectedInfoRow1);
        expectedRegionInfo.add(ExpectedInfoRow2);

        assertEquals (RegionFormat.get(0).getOperation(),expectedRegionFormat.get(0).getOperation());
        assertEquals (RegionFormat.get(0).getTarget(),expectedRegionFormat.get(0).getTarget());
        for (int i = 0; i < RegionFormat.get(0).getCells().size(); ++i){
            assertEquals (RegionFormat.get(0).getCells().get(i).getX(),expectedRegionFormat.get(0).getCells().get(i).getX());
            assertEquals (RegionFormat.get(0).getCells().get(i).getY(),expectedRegionFormat.get(0).getCells().get(i).getY());
        }
        assertEquals (RegionFormat.get(1).getOperation(),expectedRegionFormat.get(1).getOperation());
        assertEquals (RegionFormat.get(1).getTarget(),expectedRegionFormat.get(1).getTarget());
        for (int i = 0; i < RegionFormat.get(1).getCells().size(); ++i){
            assertEquals (RegionFormat.get(1).getCells().get(i).getX(),expectedRegionFormat.get(1).getCells().get(i).getX());
            assertEquals (RegionFormat.get(1).getCells().get(i).getY(),expectedRegionFormat.get(1).getCells().get(i).getY());
        }

        for(int i = 0; i< RegionBoard.length; ++i){
            for(int j = 0; j< RegionBoard[i].length; ++j){
                assertEquals ((Integer) RegionBoard[i][j],expectedRegionBoard.get(i).get(j));
            }
        }
        for(int i = 0; i< RegionBoard.length; ++i){
            for(int j = 0; j< RegionBoard[i].length; ++j){
                assertEquals ((Integer) RegionInfo[i][j],expectedRegionInfo.get(i).get(j));
            }
        }
       
    }

    
}
