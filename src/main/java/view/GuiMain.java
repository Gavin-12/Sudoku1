package view;

import controller.SudokuController;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class GuiMain {

    public static void main(String[] args) {
        SudokuController controller = new SudokuController();
        LoginFrame frame = new LoginFrame(controller);
        frame.setVisible(true);
    }
}
