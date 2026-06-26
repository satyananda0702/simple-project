package game;

import javax.swing.*;
import java.awt.*;
// menu utama saat user sudah berhasil login
public class MainMenuFrame extends JFrame {

    private Player currentPlayer;
    private JButton btnStartGame;
    private JButton btnStatistics;
    private JButton btnTopScorers;
    private JButton btnExit;

    public MainMenuFrame(Player player) {
        //menyimpan data pemain yang sedang login
        this.currentPlayer = player;

        setTitle("Tic-Tac-Toe - Menu Utama");
        setSize(360, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblWelcome = new JLabel("Selamat datang,", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(lblWelcome, gbc);

        JLabel lblUsername = new JLabel(currentPlayer.getUsername() + "!", SwingConstants.CENTER);
        lblUsername.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 1;
        mainPanel.add(lblUsername, gbc);

        btnStartGame = new JButton("Mulai Game");
        btnStartGame.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 2;
        mainPanel.add(btnStartGame, gbc);

        btnStatistics = new JButton("Statistik Saya");
        btnStatistics.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 3;
        mainPanel.add(btnStatistics, gbc);

        btnTopScorers = new JButton("Top 5 Pemain");
        btnTopScorers.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 4;
        mainPanel.add(btnTopScorers, gbc);

        btnExit = new JButton("Keluar");
        btnExit.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 5;
        mainPanel.add(btnExit, gbc);

        add(mainPanel);

        //membuka GameFrame
        btnStartGame.addActionListener(e -> {
            GameFrame gameFrame = new GameFrame(currentPlayer);
            gameFrame.setVisible(true);
            this.dispose();
        });

        btnStatistics.addActionListener(e -> {
            //membuka StatisticsFrame untuk menampilkan statistik pemain
            StatisticsFrame statsFrame = new StatisticsFrame(currentPlayer);
            statsFrame.setVisible(true);
        });

        btnTopScorers.addActionListener(e -> {
            //membuka TopScorersFrame untuk menampilkan lima pemain terbaik
            TopScorersFrame topFrame = new TopScorersFrame();
            topFrame.setVisible(true);
        });
// konfirmasi sebelum keluar game
        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Yakin mau keluar?", "Konfirmasi Keluar",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}