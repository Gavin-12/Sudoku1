package sudoku;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class Model {
    
    Solver solver;
    String instanceFile;

    Grid[][] grids;
    int puzzleSize;

    public Model(String instanceFile) 
    {
        this.instanceFile = instanceFile;
        readFile();
        solver = new Solver(grids);
    }

    private void readFile() 
    {
       int[][] nums =  FileHelper.readSudokuFile(this.instanceFile);
        puzzleSize = nums.length;
        grids = new Grid[puzzleSize][];
        
        for (int i = 0; i < puzzleSize; i++) 
        {
            grids[i] = new Grid[puzzleSize];

            for (int j = 0; j < puzzleSize; j++) 
            {
                grids[i][j] = new Grid(puzzleSize, i, j, nums[i][j]);
            }
        }
    }


    public Solver getSolver() 
    {
        return solver;
    }

    public void printGrids() 
    {
        int SIZE = (int) Math.sqrt(puzzleSize);
        for (int i = 0; i < puzzleSize; i++) 
        {
            for (int j = 0; j < puzzleSize; j++) 
            {
                System.out.print(grids[i][j].getNum() + " ");
                
                if (j % SIZE == 2 && j != puzzleSize - 1)
                {
                    System.out.print("| ");
                }                   
            }
            
            if (i % SIZE == 2 && i != puzzleSize - 1) 
            {
                System.out.println();
                for (int j = 0; j < puzzleSize * 2 + SIZE; j++) 
                {
                    System.out.print("-");
                }
            }
            
            System.out.println();
        }
    }

    public void saveSolution()
    {
        FileHelper.saveSudokuFile("Solution for "+instanceFile,grids);
    }
}
