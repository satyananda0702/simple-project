package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class LoginFrame digunakan sebagai halaman login aplikasi.
public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private PlayerService playerService;

    public LoginFrame() {
        playerService = new PlayerService();

        setTitle("Tic-Tac-Toe - Login");
        setSize(380, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("Tic-Tac-Toe Game", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);

        JLabel lblSubtitle = new JLabel("Silakan login untuk memulai", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSubtitle.setForeground(Color.GRAY);
        gbc.gridy = 1;
        mainPanel.add(lblSubtitle, gbc);

        gbc.gridwidth = 1;
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(lblUsername, gbc);

        txtUsername = new JTextField(15);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 1; gbc.gridy = 2;
        mainPanel.add(txtUsername, gbc);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(lblPassword, gbc);

        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 1; gbc.gridy = 3;
        mainPanel.add(txtPassword, gbc);

        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        mainPanel.add(btnLogin, gbc);

        add(mainPanel);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        txtPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        //ambil username
        String username = txtUsername.getText().trim();
        //ambil password
        String password = new String(txtPassword.getPassword());
//memastikan username dan password tidak kosong
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username dan password tidak boleh kosong!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
//mengirim data login ke PlayerService
        Player player = playerService.login(username, password);
//data ditemukan maka login berhasil dan buka MainFrame
        if (player != null) {
            JOptionPane.showMessageDialog(this,
                    "Login berhasil! Selamat datang, " + player.getUsername() + "!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            MainMenuFrame menuFrame = new MainMenuFrame(player);
            menuFrame.setVisible(true);
            this.dispose();
// ika username / password salah maka menampilkan pesan error.
        } else {
            JOptionPane.showMessageDialog(this,
                    "Username atau password salah!",
                    "Login Gagal", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtUsername.requestFocus();
        }
    }
}