package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import bean.User;
import controller.SudokuController;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class LoginFrame extends BaseFrame {

    private JPanel contentPane;

    /**
     * Create the frame.
     *
     * @param controller
     */
    public LoginFrame(SudokuController controller) {
        super(controller);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        setTitle("LOGIN");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 500, 20));

        JLabel title = new JLabel("Login");
        title.setFont(new Font("SansSerif", Font.BOLD, 15));
        contentPane.add(title);

        JPanel panel = new JPanel();
        contentPane.add(panel);
        panel.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel lblNewLabel = new JLabel("UserName:");
        panel.add(lblNewLabel);

        JTextField tfUserName = new JTextField();
        tfUserName.setText("pdc");
        panel.add(tfUserName);
        tfUserName.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        panel.add(lblPassword);

        JTextField tfPassword = new JTextField();
        tfPassword.setText("pdc");
        tfPassword.setColumns(10);
        panel.add(tfPassword);

        JButton btOK = new JButton("Login");
        btOK.setPreferredSize(new Dimension(100, 30));
        contentPane.add(btOK);

        JLabel lbRegist = new JLabel("Regist");
        lbRegist.setForeground(Color.BLUE);
        lbRegist.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 10));
        contentPane.add(lbRegist);

        btOK.addActionListener((ActionEvent e) -> {
            String userName = tfUserName.getText();
            String password = tfPassword.getText();
            User user = controller.login(userName, password);
            if (user == null) {
                JOptionPane.showMessageDialog(contentPane, "Invalid username or password!");
            } else {
                dispose();
                new SudokuFrame(controller).setVisible(true);
            }
        });
        lbRegist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new RegistFrame(controller).setVisible(true);
            }
        });
    }

}
