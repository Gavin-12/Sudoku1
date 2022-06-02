package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Progress;
import bean.Sudoku;
import bean.User;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class DataBaseHelper {

    private static final DataBaseHelper helper = new DataBaseHelper();
    private static Connection conn;

    private DataBaseHelper() {
        try {
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            Class.forName(driver);
            //create database
            conn = DriverManager.getConnection("jdbc:derby:sudoku;create=true");
            ResultSet tables = conn.getMetaData().getTables(null, "APP", "USERS", null);
            
            if (!tables.next()) {
                
                conn.createStatement().execute("create table USERS  (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),userName varchar(100),password varchar(100))");
                conn.createStatement().execute("create table SUDOKUS(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),n INTEGER,type INTEGER,grid varchar(500))");
                conn.createStatement().execute("create table COMPLETE(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),userId INTEGER,sudokuId INTEGER,dateTime varchar(100))");
                conn.createStatement().execute("create table PROGRESS(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),userId INTEGER,sudokuId INTEGER,grid varchar(500),dateTime varchar(100))");

                
                conn.createStatement().execute("insert into SUDOKUS(n,type,grid) values(3,0,'"
                        + "0 0 5  0 0 0  0 7 0 \n"
                        + "0 0 4  0 5 6  2 3 0 \n"
                        + "0 2 8  3 1 0  0 9 5 \n"
                        + "0 4 0  0 6 0  0 8 0 \n"
                        + "8 0 0  4 9 0  0 0 6 \n"
                        + "9 0 0  0 0 2  4 0 0 \n"
                        + "5 0 0  0 0 0  7 0 0 \n"
                        + "0 0 1  0 0 5  0 4 3 \n"
                        + "0 8 0  0 7 0  0 1 2 \n"
                        + "')");
                conn.createStatement().execute("insert into SUDOKUS(n,type,grid) values(3,0,'"
                        + "3 0 5  2 8 9  1 7 4  \n"
                        + "1 9 4  7 0 6  2 3 8  \n"
                        + "7 2 8  3 1 4  6 9 5  \n"
                        + "2 4 0  5 6 7  9 8 1  \n"
                        + "8 5 7  4 9 1  3 2 6  \n"
                        + "9 1 6  8 0 2  4 0 7  \n"
                        + "5 3 2  1 4 8  7 6 9  \n"
                        + "6 7 1  9 2 5  8 4 3  \n"
                        + "4 8 9  6 7 3  5 1 2  \n"
                        + "')");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
    public static DataBaseHelper getConn() {
        return helper;
    }

    
    public boolean addSudoku(Sudoku sudoku) {
        try {
            StringBuilder sb = new StringBuilder();
            int[][] grids = sudoku.getGrid();

            for (int i = 0; i < grids.length; i++) {
                for (int j = 0; j < grids[i].length; j++) {
                    sb.append(grids[i][j]).append(" ");
                }
                sb.append("\n");
            }

            conn.createStatement().execute("insert into SUDOKUS(n,type,grid) values(" + sudoku.getN() + "," + sudoku.getType() + ",'"
                    + sb.toString()
                    + "')");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean addUser(User user) {
        try {
            ResultSet resultSet = conn.createStatement()
                    .executeQuery("select * from USERS where userName='" + user.getUserName() + "'");
            
            if (resultSet.next()) {
                return false;
            }
            conn.createStatement().execute("insert into USERS(userName,password) values('" + user.getUserName() + "','" + user.getPassword() + "')");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public User getUser(User user) {
        try {
            ResultSet resultSet = conn.createStatement().executeQuery("select * from USERS where userName='" + user.getUserName() + "' and password = '" + user.getPassword() + "'");
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public List<Sudoku> getAllSudoku(User user) {
        List<Sudoku> lists = new ArrayList<Sudoku>();
        try {
            ResultSet resultSet = conn.createStatement().executeQuery("select id,n,type,grid,(select 'finish' from COMPLETE where sudokuId=s.id and userId=" + user.getId() + " fetch first 1 rows only) isFinish from SUDOKUS s");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int n = resultSet.getInt("n");
                int type = resultSet.getInt("type");
                String finish = resultSet.getString("isFinish");
                int[][] grid = new int[n * n][n * n];
                // parse the string to grid array
                String[] gridStr = resultSet.getString("grid").split("\\s+");
                for (int i = 0; i < gridStr.length; i++) {
                    grid[i / 9][i % 9] = Integer.parseInt(gridStr[i]);
                }

                Sudoku sudoku = new Sudoku(id, n, type, grid);
                sudoku.setIsFinish(finish);
                lists.add(sudoku);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lists;
    }

    public List<Progress> getAllRecord(User user) {
        List<Progress> lists = new ArrayList<Progress>();
        try {
            ResultSet resultSet = conn.createStatement().executeQuery("select p.id,sudokuId,userId,s.n,p.grid,s.grid originGrid,dateTime"
                    + " from PROGRESS p left join SUDOKUS s on p.sudokuId=s.id where userId=" + user.getId());

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int n = resultSet.getInt("n");
                String[] originGrid = resultSet.getString("originGrid").split("\\s+");
                int[][] ogrid = new int[n * n][n * n];

                for (int i = 0; i < originGrid.length; i++) {
                    ogrid[i / 9][i % 9] = Integer.parseInt(originGrid[i]);
                }

                int[][] grid = new int[n * n][n * n];
                // parse the string to grid array
                String[] gridStr = resultSet.getString("grid").split("\\s+");
                for (int i = 0; i < gridStr.length; i++) {
                    grid[i / 9][i % 9] = Integer.parseInt(gridStr[i]);
                }

                String date = resultSet.getString("dateTime");
                int sudokuId = resultSet.getInt("sudokuId");
                int userId = resultSet.getInt("userId");
                Progress progress = new Progress(id, userId, sudokuId, date);
                progress.setN(n);
                progress.setGrid(grid);
                progress.setOriginGrid(ogrid);
                lists.add(progress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lists;
    }

    
    public void saveRecord(Progress progress) {
        try {
            StringBuilder sb = new StringBuilder();
            int[][] grids = progress.getGrid();

            for (int i = 0; i < grids.length; i++) {
                for (int j = 0; j < grids[i].length; j++) {
                    sb.append(grids[i][j]).append(" ");
                }
                sb.append("\n");
            }
            if (progress.getId() > 0) {
                conn.createStatement().execute("update PROGRESS set grid='" + sb.toString() + "' "
                        + "where id=" + progress.getId());
            } else {
                conn.createStatement().execute("insert into PROGRESS(userId,sudokuId,grid,dateTime) "
                        + "values(" + progress.getUserId() + "," + progress.getSudokuId() + ",'" + sb.toString() + "','" + progress.getDate() + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    
    public void saveComplete(Progress progress) {
        try {
            conn.createStatement().execute("insert into COMPLETE(userId,sudokuId,dateTime) "
                    + "values(" + progress.getUserId() + "," + progress.getSudokuId() + ",'" + progress.getDate() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void free() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
