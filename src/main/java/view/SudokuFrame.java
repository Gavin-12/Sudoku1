package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import bean.Progress;
import bean.Sudoku;
import controller.SudokuController;

import javax.swing.border.LineBorder;


import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class SudokuFrame extends BaseFrame {

    private JPanel contentPane;
    private JTable sudokuTable;
    private JTable recordTable;

    private JTextField fields[][] = new JTextField[9][9]; 
    Progress progress;
    /**
     * Create the frame.
     */
    public SudokuFrame(SudokuController controller) {
        super(controller);
        setTitle("SUDOKU");
        setBounds(100, 100, 740, 490);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mFile = new JMenu("File");
        menuBar.add(mFile);
        
        JMenuItem mImport = new JMenuItem("Import Sudoku File to database");
        mFile.add(mImport);
        
        JMenuItem mExit = new JMenuItem("Exit");
        mFile.add(mExit);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        JPanel p1 = new JPanel();
        p1.setPreferredSize(new Dimension(280, 10));
        contentPane.add(p1, BorderLayout.WEST);
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        
        JScrollPane sp1 = new JScrollPane();
        sp1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp1.setBorder(new TitledBorder(null, "Sudoku", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        p1.add(sp1);
        
        sudokuTable = new JTable();
        sp1.setViewportView(sudokuTable);
        
        JScrollPane sp2 = new JScrollPane();
        sp2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp2.setBorder(new TitledBorder(null, "My Record", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        p1.add(sp2);
        
        recordTable = new JTable();
        sp2.setViewportView(recordTable);
        
        JPanel p2 = new JPanel();
        contentPane.add(p2, BorderLayout.CENTER);
        
        JPanel sudokuPane = new JPanel();
        sudokuPane.setPreferredSize(new Dimension(370, 370));
        sudokuPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        p2.add(sudokuPane);
        sudokuPane.setLayout(new GridLayout(9, 9, 0, 0));
        
        JPanel p4 = new JPanel();
        p2.add(p4);
        
        JButton btCheck = new JButton("Check");
 
        p4.add(btCheck);
        
        JButton btSave = new JButton("Save Record");

        p4.add(btSave);
        
        JButton btAnswer = new JButton("Show Answer");
 
        p4.add(btAnswer);
        for (int i = 0; i < 81; i++) {
            JTextField field = new JTextField("");
            field.setEnabled(false);
            fields[i/9][i%9] = field;
            field.setHorizontalAlignment(JTextField.CENTER);
            field.setPreferredSize(new Dimension(40, 40));
            sudokuPane.add(field);
        }
        loadSudoku();
        loadRecord();
        mImport.addActionListener(event->{
            JFileChooser chooser = new JFileChooser(new File("."));
            int type = chooser.showOpenDialog(contentPane);
            if (type == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                controller.addSudoku(selectedFile.getAbsolutePath());
                loadSudoku();
            }
        });
        mExit.addActionListener(event->{
            dispose();
            controller.freeCollection();
        });
        setbtListener(controller, btCheck, btSave, btAnswer);
    }

    private void setbtListener(SudokuController controller, JButton btCheck,
            JButton btSave, JButton btAnswer) {
        btSave.addActionListener((ActionEvent e) -> {
            try {
                if (progress != null) {
                    int[][] grid =new int[9][9];
                    for (int i = 0; i < grid.length; i++) {
                        for (int j = 0; j < grid[i].length; j++) {
                            String num = fields[i][j].getText();
                            if (num.length() > 0) {
                                grid[i][j] = Integer.parseInt(num);
                            }
                        }
                    }
                    progress.setGrid(grid);
                    controller.saveProgress(progress);
                    loadRecord();
                    JOptionPane.showMessageDialog(contentPane, "Save record success.");
                    
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(contentPane, e1.getMessage());
            }
        });
        btAnswer.addActionListener((ActionEvent e) -> {
            if (progress != null) {
                int[][] grids = controller.getSolver(progress);
                for (int i = 0; i < grids.length; i++) {
                    for (int j = 0; j < grids.length; j++) {
                        fields[i][j].setText(grids[i][j]+"");
                    }
                }
            }
        });
        btCheck.addActionListener((ActionEvent e) -> {
            try {
                if (progress != null) {
                    int[][] grids = controller.getSolver(progress);
                    boolean finish = true;
                    for (int i = 0; i < grids.length; i++) {
                        for (int j = 0; j < grids.length; j++) {
                            if (grids[i][j] != Integer.parseInt(fields[i][j].getText())) {
                                finish = false;
                            }
                        }
                    }
                    if (finish) {
                        controller.complete(progress);
                        loadSudoku();
                        JOptionPane.showMessageDialog(contentPane, "You finish this sudoku.");
                        
                    }else {
                        JOptionPane.showMessageDialog(contentPane, "Wrong answer");
                    }
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(contentPane, e1.getMessage());
                
            }
        });
    }

    /**
     * load all Sudoku from database
     */
    private void loadSudoku(){
        DefaultListSelectionModel listSelectionModel = (DefaultListSelectionModel) sudokuTable.getSelectionModel();
        
        DefaultTableModel sudokuModel = new DefaultTableModel(new Object[]{"ID","N","STATUS"}, 0);
        List<Sudoku> datas = controller.getSudokuList();
        for (Sudoku sudoku : datas) {
            sudokuModel.addRow(new Object[]{"Sudoku "+sudoku.getId(),sudoku.getN(),sudoku.getIsFinish()});
        }
        sudokuTable.setModel(sudokuModel);
        listSelectionModel.addListSelectionListener((ListSelectionEvent e) -> {
            try {
                if (e.getValueIsAdjusting()) {
                    recordTable.getSelectionModel().clearSelection();
                    int row = sudokuTable.getSelectedRow();
                    if (row < 0) {
                        return;
                    }
                    //read the select row data
                    Sudoku sudoku = datas.get(row);
                    
                    int[][] grid = sudoku.getGrid();
                    for (int i = 0; i < grid.length; i++) {
                        for (int j = 0; j < grid[i].length; j++) {
                            //set the data to text field
                            if (grid[i][j] != 0) {
                                fields[i][j].setEnabled(false);
                                fields[i][j].setText(grid[i][j]+"");
                            } else {
                                fields[i][j].setEnabled(true);
                                fields[i][j].setText("");
                            }
                        }
                    }
                    progress = new Progress();
                    progress.setOriginGrid(grid);
                    progress.setSudokuId(sudoku.getId());
                }
            } catch (Exception e1) {
            }
        });
    }
    private void loadRecord(){
        DefaultListSelectionModel listSelectionModel = (DefaultListSelectionModel) recordTable.getSelectionModel();
        DefaultTableModel recordModel = new DefaultTableModel(new Object[]{"ID","N","DATE"}, 0);
        List<Progress> datas = controller.getRecordList();
        for (Progress progress : datas) {
            recordModel.addRow(new Object[]{"Sudoku "+progress.getId(),progress.getN(),progress.getDate()});
        }
        recordTable.setModel(recordModel);
        listSelectionModel.addListSelectionListener((ListSelectionEvent e) -> {
            try {
                if (e.getValueIsAdjusting()) {
                    sudokuTable.getSelectionModel().clearSelection();
                    int row = recordTable.getSelectedRow();
                    //read the select row data
                    Progress pro = datas.get(row);
                    int[][] originGrid = pro.getOriginGrid();
                    for (int i = 0; i < originGrid.length; i++) {
                        for (int j = 0; j < originGrid[i].length; j++) {
                            //set the data to text field
                            if (originGrid[i][j] != 0) {
                                fields[i][j].setEnabled(false);
                                fields[i][j].setText(originGrid[i][j]+"");
                            } else {
                                fields[i][j].setEnabled(true);
                                fields[i][j].setText("");
                            }
                        }
                    }
                    int[][] grid = pro.getGrid();
                    for (int i = 0; i < grid.length; i++) {
                        for (int j = 0; j < grid[i].length; j++) {
                            if (grid[i][j] != 0) {
                                fields[i][j].setText(grid[i][j]+"");  
                            }
                        }
                    }
                    progress = pro;
                }
            } catch (Exception e1) {
            }
        });
    }
}
