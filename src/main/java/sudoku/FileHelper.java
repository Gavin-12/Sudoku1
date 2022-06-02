package sudoku;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class FileHelper {
    
    public static int[][] readSudokuFile(String instanceFile) 
    {
        int[][] ret = null;
        try 
        {
            BufferedReader in = new BufferedReader(new FileReader(instanceFile));
            String str = in.readLine();
            int N = Integer.parseInt(str);
            if (N <= 0) {
                System.err.println("N " + N + " is illegal!!!");
                System.exit(-1);
            }
            
            N = N * N;
            ret = new int[N][];

            for (int i = 0; i < N; i++) 
            {
                ret[i] = new int[N];
                str = in.readLine();
                String[] ss = str.split("\\s+"); 
                if (ss.length != N) {
                    System.err.println("N " + N + " is illegal!!!");
                    System.exit(-2);
                }
                
                for (int j = 0; j < N; j++) 
                {
                    ret[i][j] = Integer.parseInt(ss[j]);
                }
            }
            
            in.close();
            
        } catch (FileNotFoundException e) {
            System.err.println("file not found!!!");
            System.exit(-3);
        } catch (IOException e) {
            System.err.println("read file error!!!");
            System.exit(-4);
        } catch (NumberFormatException e) {
            System.err.println("Number Format error!!!");
            System.exit(-5);
        }
        
        return ret;
    }

    public static void saveSudokuFile(String solutionFile, Grid[][] grids) {
        try 
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(solutionFile));
            StringBuilder sb = new StringBuilder();
            sb.append(grids.length).append("\n");
            for (int i = 0; i < grids.length; i++) 
            {
                for (int j = 0; j < grids[i].length; j++) 
                {
                    sb.append(grids[i][j].getNum()).append(" ");
                }
                sb.append("\n");
            }
            
            out.write(sb.toString());
            out.close();
        } catch (IOException e) {
            System.err.println("write file error!!!");
            System.exit(-6);
        }
    }
            
    
}
