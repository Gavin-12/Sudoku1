package bean;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class Progress extends Complete {

    private int[][] originGrid;// origin grid data
    private int[][] grid;// Progress grid data

    public Progress() {
    }

    /**
     *
     * @param id
     * @param userId
     * @param sudokuId
     * @param date
     */
    public Progress(int id, int userId, int sudokuId, String date) {
        super(id, userId, sudokuId, date);
    }

    public int[][] getOriginGrid() {
        return originGrid;

    }

    public void setOriginGrid(int[][] originGrid) {
        this.originGrid = originGrid;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

}
