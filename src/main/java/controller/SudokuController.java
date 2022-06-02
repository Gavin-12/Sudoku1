package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sudoku.FileHelper;
import sudoku.Grid;
import sudoku.Solver;
import bean.Progress;
import bean.Sudoku;
import bean.User;
import database.DataBaseHelper;
/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class SudokuController {


    private User loginUser;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public SudokuController() {
        super();
        DataBaseHelper.getConn();
    }


    public User login(String userName, String password) {
        loginUser = DataBaseHelper.getConn().getUser(
                new User(userName, password));
        return loginUser;
    }


    public boolean regist(String userName, String password) {
        boolean b = DataBaseHelper.getConn().addUser(new User(userName, password));
        return b;
    }


    public List<Sudoku> getSudokuList() {
        return DataBaseHelper.getConn().getAllSudoku(loginUser);
    }


    public void freeCollection() {
        DataBaseHelper.getConn().free();
    }


    public List<Progress> getRecordList() {
        return DataBaseHelper.getConn().getAllRecord(loginUser);
    }


    public void saveProgress(Progress progress) {
        progress.setUserId(loginUser.getId());
        progress.setDate(sdf.format(new Date()));
        DataBaseHelper.getConn().saveRecord(progress);
    }


    public int[][] getSolver(Progress progress) {
        int[][] grid = progress.getOriginGrid();
        
        Grid[][] grids = new Grid[9][9];
        for (int i = 0; i < grids.length; i++) {
            grids[i] = new Grid[9];
            for (int j = 0; j < grids.length; j++) {
                grids[i][j] = new Grid(grid.length, i, j, grid[i][j]);
            }
        }
    
        Solver solver = new Solver(grids);
        solver.solve();
 
        int[][] answer = new int[9][9]; 
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids.length; j++) {
                answer[i][j] = grids[i][j].getNum();
            }
        }
        return answer;
    }

    
    public void complete(Progress progress) {
        progress.setUserId(loginUser.getId());
        progress.setDate(sdf.format(new Date()));
        DataBaseHelper.getConn().saveComplete(progress);

    }

    
    public boolean addSudoku(String filePath) {
        int[][] readSudokuFile = FileHelper.readSudokuFile(filePath);
        int n = (int) Math.sqrt(readSudokuFile.length);
        Sudoku sudoku = new Sudoku(-1, n , 0, readSudokuFile);
        return DataBaseHelper.getConn().addSudoku(sudoku);

    }
}
