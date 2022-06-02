package bean;

import java.util.Arrays;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class Sudoku {

    private int id;
    private int n; // N
    private int type;
    private int[][] grid;// grid data

    private String isFinish;

    public Sudoku(int id, int n, int type, int[][] grid) {
        super();
        this.id = id;
        this.n = n;
        this.type = type;
        this.grid = grid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    @Override
    public String toString() {
        return "Sudoku [id=" + id + ", n=" + n + ", type=" + type + ", grid=" + Arrays.toString(grid) + "]";
    }

}
