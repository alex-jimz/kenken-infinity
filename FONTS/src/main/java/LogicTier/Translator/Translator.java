package LogicTier.Translator;


import java.util.*;

import LogicTier.Region.Region;

public class Translator{

    private Vector<Region> kenkenRegionFormat;
    private int[][] RegionBoard;
    private int[][] RegionInfo;
    int N;
    
    public Translator(){
    }

    public Vector<Region> translateKenken (Vector<Vector<Integer>> kenkenIntegerFormat){

            //reads the header of the 
            int R = kenkenIntegerFormat.get(0).get(1);
            N = kenkenIntegerFormat.get(0).get(0);
            kenkenRegionFormat  = new Vector<Region> (R);

            RegionBoard = new int[N][N];
           
            RegionInfo = new int[R][2];
            //generaes the regions from the input
            for (int i = 1; i < kenkenIntegerFormat.size(); ++i){
                    
                    int oper  = kenkenIntegerFormat.get(i).get(0);
                    int result  = kenkenIntegerFormat.get(i).get(1);
                    int regSize = kenkenIntegerFormat.get(i).get(2);

                    Vector<Integer> positions =  new Vector<Integer> (2*regSize);

                    int x = 0;
                    int y = 0;
                    for (int  j = 3; j < kenkenIntegerFormat.get(i).size(); ++j){
                        positions.add(j-3, kenkenIntegerFormat.get(i).get(j)-1);
                        if(j %2 == 0){
                            y = kenkenIntegerFormat.get(i).get(j)-1;
                            RegionBoard[x][y] = i-1;
                        }
                        else{
                            x = kenkenIntegerFormat.get(i).get(j)-1;
                        }
                    }
                    RegionInfo[i-1][0] = oper;
                    RegionInfo[i-1][1] = result;

                    Region reg;
                    reg = new Region (oper, result, positions);
                    kenkenRegionFormat.add (i-1, reg);
                    
            }
            return kenkenRegionFormat;
    }

    public int getN(){
        return N;
    }
    public int[][] getRegionInfo(){
        return RegionInfo;
    }
    public int[][] getRegionBoard(){
        return RegionBoard;
    }
}