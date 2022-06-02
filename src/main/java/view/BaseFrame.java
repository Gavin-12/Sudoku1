package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import controller.SudokuController;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class BaseFrame extends JFrame {

    SudokuController controller;

    public BaseFrame(SudokuController controller) {
        super();
        this.controller = controller;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.freeCollection();
                System.out.println(".windowClosing()");
                System.exit(0);
            }
        });
    }

}
