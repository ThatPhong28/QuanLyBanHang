package view;

import dao.NhanVienDAO;
import model.NhanVien;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JCheckBox chkShow;
    private JButton btnLogin, btnExit;
    private NhanVienDAO nvDAO = new NhanVienDAO();
    public LoginView() {
        setTitle("ĐĂNG NHẬP");
        setSize(450, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        initUI();
    }

    private void initUI() {
   
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(255, 150, 150),
                        0, getHeight(), new Color(255, 220, 220)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(new BorderLayout());
        background.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("SALES SYSTEM", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.RED);
        background.add(lblTitle, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false); // trong suốt
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUser = new JLabel("Tên đăng nhập:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        txtUser = new JTextField(20);
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        txtPass = new JPasswordField(20);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPass.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        // Checkbox hiện pass
        chkShow = new JCheckBox("Hiện mật khẩu");
        chkShow.setOpaque(false);
        chkShow.addActionListener(e -> {
            txtPass.setEchoChar(chkShow.isSelected() ? (char) 0 : '•');
        });

        
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(lblUser, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        form.add(txtUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(lblPass, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        form.add(txtPass, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        form.add(chkShow, gbc);

        background.add(form, BorderLayout.CENTER);

        
        JPanel panelBtn = new JPanel(new GridLayout(1, 2, 15, 0));
        panelBtn.setOpaque(false);

        btnLogin = new JButton("Đăng nhập");
        btnLogin.setBackground(new Color(255, 70, 70));
        btnLogin.setForeground(Color.white);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setFocusPainted(false);

        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(new Color(250, 40, 40));
            }

            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(new Color(255, 70, 70));
            }
        });

        btnExit = new JButton("Thoát");
        btnExit.setBackground(Color.GRAY);
        btnExit.setForeground(Color.white);
        btnExit.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnExit.setFocusPainted(false);

        panelBtn.add(btnLogin);
        panelBtn.add(btnExit);

        background.add(panelBtn, BorderLayout.SOUTH);

        add(background);

       
        btnExit.addActionListener(e -> System.exit(0));
        btnLogin.addActionListener(e -> xuLyDangNhap());
     
        txtUser.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtPass.requestFocus();
             }
        }
    });
        txtPass.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            xuLyDangNhap();
             }
            }
        });

    }
    private void xuLyDangNhap() {
    String user = txtUser.getText().trim();
    String pass = new String(txtPass.getPassword());
    if (user.isEmpty() || pass.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
        return;
    }
    NhanVien nv = nvDAO.checkLogin(user, pass);
    if (nv == null) {
        JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!");
        return;
    }
    View ql = new View(nv);
    ql.setVisible(true);
    dispose(); 
}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
