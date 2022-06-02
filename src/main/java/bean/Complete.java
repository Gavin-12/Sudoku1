package bean;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class Complete {

    private int id;
    private int n; // N
    private int userId; // N
    private int sudokuId;

    private String date;

    public Complete() {
        super();
    }

    /**
     *
     * @param id
     * @param userId
     * @param sudokuId
     * @param date
     */
    public Complete(int id, int userId, int sudokuId, String date) {
        super();
        this.id = id;
        this.userId = userId;
        this.sudokuId = sudokuId;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSudokuId() {
        return sudokuId;
    }

    public void setSudokuId(int sudokuId) {
        this.sudokuId = sudokuId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

}
