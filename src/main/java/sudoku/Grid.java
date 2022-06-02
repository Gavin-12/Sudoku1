package sudoku;

import java.util.ArrayList;


/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class Grid {
    
    int locX, locY;
    int num;
    
    ArrayList<Integer> domain;

    public Grid(int maxN, int x, int y, int n) 
    {
        this.locX = x;
        this.locY = y;
        this.num = n;

        this.domain = new ArrayList<>();
        if (n != 0) 
        {
            domain.add(num);
        } 
        else 
        {
            for (int i = 1; i <= maxN; i++) 
            {
                if (i != num)
                    domain.add(i);
            }
        }
    }

    public void setNum(int n) 
    {
        this.num = n;
    }

    public void remove(int v) 
    {
        domain.remove((Integer) v);
    }

    public boolean isEmpty() 
    {
        return num == 0;
    }


    public int getX() 
    {
        return locX;
    }

    public int getY() 
    {
        return locY;
    }

    public int getNum() 
    {
        return num;
    }

    public int domainSize() 
    {
        return domain.size();
    }

    public int getIdxValue(int i) 
    {
        return domain.get(i);
    }
    
}
