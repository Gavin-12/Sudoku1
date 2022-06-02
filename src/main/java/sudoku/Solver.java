package sudoku;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class Solver {
    
    private final Grid[][] grids;
    private final int puzzleSize;

    private boolean knightRule = false;
    private boolean kingRule = false;
    private boolean queenRule = false;

    private final ArrayList<Grid> variables = new ArrayList<>();

    public Solver(Grid[][] grids) 
    {
        this.grids = grids;
        puzzleSize = grids.length;
        prepare();
    }
  
    public void solve() 
    {
        int times = variables.size();
        
        if (times == 0)
        {
            return;
        }
        
        int level = 0;
        int[] pointer = new int[times];
        Arrays.fill(pointer, 0);
        
        while (true) 
        {
            if (!placingANumber(level, pointer)) 
            {
                int row = variables.get(level).getX();
                int col = variables.get(level).getY();
                grids[row][col].setNum(0);
                pointer[level] = 0;
                level--; 
                if (level == 0 && pointer[level] >= variables.get(level).domainSize()) 
                {
                    break;
                }                    
                continue;
            }
            if (checkValue(variables.get(level))) 
            {
                level++;
                if (level == times) 
                { 
                    break;
                }
            }
        }
    }

    private boolean placingANumber(int level, int[] pointer) 
    {
        if (pointer[level] < variables.get(level).domainSize()) 
        {
            int row = variables.get(level).getX();
            int col = variables.get(level).getY();
            grids[row][col].setNum(variables.get(level).getIdxValue(pointer[level]));
            pointer[level]++;
            return true;
        } 
        else
        {
            return false;
        }
    }


    private void prepare() 
    {
        for (int i = 0; i < puzzleSize; i++) 
        {
            for (int j = 0; j < puzzleSize; j++) 
            {
                if (grids[i][j].isEmpty()) 
                {
                    variables.add(grids[i][j]);
                }
            }
        }
        
        int SIZE = (int) Math.sqrt(puzzleSize);
        
        for (int i = 0; i < puzzleSize; i++) 
        {
            for (int j = 0; j < puzzleSize; j++) 
            {
                if (grids[i][j].isEmpty()) 
                { 
                    for (int k = 0; k < variables.size(); k++) 
                    {
                        Grid e = variables.get(k);
                        if (e.getX() == i)
                        {
                            e.remove(grids[i][j].getNum());
                        }                        
                        if (e.getY() == j)
                        {
                            e.remove(grids[i][j].getNum());
                        }
                        
                        int block_i = i / SIZE;
                        int block_j = j / SIZE;
                        
                        if (e.getY() / SIZE == block_j && e.getX() / SIZE == block_i) 
                        {
                            e.remove(grids[i][j].getNum());
                        }
                    }
                }
            }
        }
    }

    private boolean checkValue(Grid vi) 
    {
        int row = vi.getX();
        int col = vi.getY();

        for (int i = 0; i < puzzleSize; i++) 
        {
            if (i != col && grids[row][i].getNum() == grids[row][col].getNum())
            {
                return false;
            }               
            if (i != row && grids[i][col].getNum() == grids[row][col].getNum())
            {
                return false;
            }              
        }


        int SIZE = (int) Math.sqrt(puzzleSize);

        int Block_i = SIZE * (row / SIZE);
        int Block_j = SIZE * (col / SIZE);

        
        for (int i = Block_i; i < Block_i + SIZE; i++) 
        {
            for (int j = Block_j; j < Block_j + SIZE; j++) 
            {
                if (!(i == row && j == col) && grids[i][j].getNum() == grids[row][col].getNum())
                {
                    return false;
                }        
            }
        }

        
        if (knightRule) 
        {
            int[] direction = {-2, -1, 1, 2};
            for (int short_L : direction) 
            {
                int i = row + short_L;
                
                if (i < 0)
                {
                    continue;
                }            
                if (i >= puzzleSize)
                {
                    break;
                }
                    

                int long_L = Math.abs(short_L) == 2 ? -1 : -2;
                int j = col + long_L;
                if (j >= puzzleSize)
                {
                    continue;
                }         
                else if (j >= 0 && grids[i][j].getNum() == grids[row][col].getNum())
                {
                    return false;
                }
                    
                j = col - long_L;
                if (j >= 0 && j < puzzleSize && grids[i][j].getNum() == grids[row][col].getNum())
                {
                    return false;
                }
            }
        }

        
        if (kingRule)
        {
            for (int i = row - 1; i <= row + 1; i += 2) 
            {
                if (i < 0)
                {
                    continue;
                }                  
                if (i >= puzzleSize)
                {
                    break;
                }                 
                for (int j = col - 1; j <= col + 1; j += 2) 
                {
                    if (j < 0)
                    {
                        continue;
                    }                      
                    if (j >= puzzleSize)
                    {
                        break;
                    }                   
                    if (grids[i][j].getNum() == grids[row][col].getNum())
                    {
                        return false;
                    }      
                }
            }
        }

        
        if (queenRule) 
        {
            if (grids[row][col].getNum() == puzzleSize) 
            {
                int diff = col - row;
                
                for (int i = 0; i < puzzleSize; i++) 
                {
                    int j = i + diff;
                    if (j < 0 || i == row)
                    {
                        continue;
                    }                        
                    if (j >= puzzleSize)
                    {
                        break;
                    }                       
                    if (grids[i][j].getNum() == grids[row][col].getNum())
                    {
                        return false;
                    }                       
                }
                
                int sum = row + col;
                for (int i = 0; i < puzzleSize; i++) 
                {
                    int j = sum - i;
                    if (j < 0)
                    {
                        break;
                    }   
                    if (j >= puzzleSize || i == row)
                    {
                        continue;
                    }                      
                    if (grids[i][j].getNum() == grids[row][col].getNum())
                    {
                        return false;
                    }                     
                }
            }
        }

        return true;
    }


    public void setKnightRule() 
    {
        knightRule = true;
    }

    public void setKingRule() 
    {
        kingRule = true;
    }

    public void setQueenRule() 
    {
        queenRule = true;
    }
    
}
