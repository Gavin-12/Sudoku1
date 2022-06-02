package view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.SudokuController;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class RegistFrame extends BaseFrame {

    private JPanel contentPane;
    public RegistFrame(SudokuController controller) {
        super(controller);
        setTitle("REGIST");
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 500, 20));
        
        JLabel title = new JLabel("Regist");
        title.setFont(new Font("SansSerif", Font.BOLD, 15));
        contentPane.add(title);
        
        JPanel p1 = new JPanel();
        contentPane.add(p1);
        p1.setLayout(new GridLayout(0, 2, 0, 0));
        
        JLabel lbUserName = new JLabel("UserName:");
        p1.add(lbUserName);
        
        JTextField tfUserName = new JTextField();
        p1.add(tfUserName);
        tfUserName.setColumns(10);
        
        JLabel lbPassword = new JLabel("Password:");
        p1.add(lbPassword);
        
        JTextField tfPassword = new JTextField();
        tfPassword.setColumns(10);
        p1.add(tfPassword);
        
        JPanel p2 = new JPanel();
        contentPane.add(p2);
        
        JButton btOK = new JButton("Regist");

        p2.add(btOK);
        btOK.setPreferredSize(new Dimension(100, 30));
        
        JButton btBack = new JButton("Back");
        p2.add(btBack);
        btOK.addActionListener((ActionEvent e) -> {
            String userName = tfUserName.getText();
            String password = tfPassword.getText();
            boolean b = controller.regist(userName, password);
            if (b) {
                JOptionPane.showMessageDialog(contentPane, "Regist success,back to login!");
                dispose();
                new LoginFrame(controller).setVisible(true);
            }else {
                JOptionPane.showMessageDialog(contentPane, "User name is exist!");
            }
        });
        
        btBack.addActionListener((ActionEvent e) -> {
            dispose();
            new LoginFrame(controller).setVisible(true);
        });
    }

}
