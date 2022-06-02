package controller;

import bean.Progress;
import bean.Sudoku;
import bean.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class SudokuControllerTest {

    public SudokuControllerTest() {
    }

    /**
     * Test of login method, of class SudokuController.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        String userName = "1";
        String password = "1";
        SudokuController instance = new SudokuController();
        User expResult = null;
        User result = instance.login(userName, password);
        assertEquals(expResult, result);
        instance.freeCollection();
    }

    /**
     * Test of regist method, of class SudokuController.
     */
    @Test
    public void testRegist() {
        System.out.println("regist");
        String userName = "111";
        String password = "111";
        SudokuController instance = new SudokuController();
        User user = instance.login(userName, password);
        if (user == null) {
            boolean expResult = true;
            boolean result = instance.regist(userName, password);
            assertEquals(expResult, result);
        } else {
            boolean expResult = false;
            boolean result = instance.regist(userName, password);
            assertEquals(expResult, result);
        }
        instance.freeCollection();
    }

    /**
     * Test of getSudokuList method, of class SudokuController.
     */
    @Test
    public void testGetSudokuList() {
        System.out.println("getSudokuList");
        SudokuController instance = new SudokuController();
        instance.login("111", "111");
        List<Sudoku> result = instance.getSudokuList();
        assertTrue(result.size() > 0);
        instance.freeCollection();
    }

    /**
     * Test of freeCollection method, of class SudokuController.
     */
    @Test
    public void testFreeCollection() {
        System.out.println("freeCollection");
        SudokuController instance = new SudokuController();
        instance.freeCollection();
        assertTrue(true);
        instance.freeCollection();
    }

    /**
     * Test of getRecordList method, of class SudokuController.
     */
    @Test
    public void testGetRecordList() {
        System.out.println("getRecordList");
        SudokuController instance = new SudokuController();
        instance.login("111", "111");
        List<Progress> result = instance.getRecordList();
        assertTrue(result.size() >= 0);
        instance.freeCollection();

    }

    /**
     * Test of saveProgress method, of class SudokuController.
     */
    @Test
    public void testSaveProgress() {
        System.out.println("saveProgress");
        Progress progress = new Progress(0, 0, 0, "2011-11-11 11:11:11");
        progress.setGrid(new int[][]{{}});
        SudokuController instance = new SudokuController();
        instance.login("111", "111");
        instance.saveProgress(progress);
        assertTrue(true);
        instance.freeCollection();
    }

    /**
     * Test of getSolver method, of class SudokuController.
     */
    @Test
    public void testGetSolver() {
        System.out.println("getSolver");
        Progress progress = new Progress(0, 0, 0, "");
        int[][] grid = {
            {0, 0, 5, 0, 0, 0, 0, 7, 0},
            {0, 0, 4, 0, 5, 6, 2, 3, 0},
            {0, 2, 8, 3, 1, 0, 0, 9, 5},
            {0, 4, 0, 0, 6, 0, 0, 8, 0},
            {8, 0, 0, 4, 9, 0, 0, 0, 6},
            {9, 0, 0, 0, 0, 2, 4, 0, 0},
            {5, 0, 0, 0, 0, 0, 7, 0, 0},
            {0, 0, 1, 0, 0, 5, 0, 4, 3},
            {0, 8, 0, 0, 7, 0, 0, 1, 2}
        };
        progress.setOriginGrid(grid);
        SudokuController instance = new SudokuController();
        int[][] expResult = {{3, 6, 5, 2, 8, 9, 1, 7, 4},
        {1, 9, 4, 7, 5, 6, 2, 3, 8},
        {7, 2, 8, 3, 1, 4, 6, 9, 5},
        {2, 4, 3, 5, 6, 7, 9, 8, 1},
        {8, 5, 7, 4, 9, 1, 3, 2, 6},
        {9, 1, 6, 8, 3, 2, 4, 5, 7},
        {5, 3, 2, 1, 4, 8, 7, 6, 9},
        {6, 7, 1, 9, 2, 5, 8, 4, 3},
        {4, 8, 9, 6, 7, 3, 5, 1, 2}};
        int[][] result = instance.getSolver(progress);
        assertArrayEquals(expResult, result);
        instance.freeCollection();
    }

    /**
     * Test of complete method, of class SudokuController.
     */
    @Test
    public void testComplete() {
        System.out.println("complete");
        Progress progress = new Progress(0, 0, 0, "2011-11-11 11:11:11");
        progress.setGrid(new int[][]{{}});
        SudokuController instance = new SudokuController();
        instance.login("111", "111");
        instance.complete(progress);
        instance.freeCollection();
    }

}
