package game;

import javax.swing.*;
import java.awt.*;
//class ini untuk menampilkan dan menjalankan permainan

//class ini menghubungkan tampilan GUI, logika permainan pada GameLogic, dan database pada PlayerService
public class GameFrame extends JFrame {

    private Player currentPlayer;
    private PlayerService playerService;
    private GameLogic gameLogic;
    private JButton[] buttons;
    private JLabel lblStatus;
    private boolean gameOver;

    public GameFrame(Player player) {
        //simpan data pemain
        this.currentPlayer = player;
        //untuk akses database
        this.playerService = new PlayerService();
        //objek GameLogic untuk mengatur aturan permainan
        this.gameLogic = new GameLogic();
        //permainan belum selesai
        this.gameOver = false;

        setTitle("Tic-Tac-Toe - Game");
        setSize(420, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblPlayer = new JLabel("Bermain sebagai: " + currentPlayer.getUsername() + " (X)", SwingConstants.CENTER);
        lblPlayer.setFont(new Font("Arial", Font.PLAIN, 13));
        mainPanel.add(lblPlayer, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 10));

        lblStatus = new JLabel("Giliran kamu! (X)", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(lblStatus, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 6, 6));
        boardPanel.setBackground(Color.DARK_GRAY);
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));

        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 42));
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            final int index = i;
            buttons[i].addActionListener(e -> handlePlayerMove(index));
            boardPanel.add(buttons[i]);
        }
        centerPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton btnNewGame = new JButton("Game Baru");
        JButton btnBack = new JButton("Kembali ke Menu");
        btnNewGame.setFont(new Font("Arial", Font.PLAIN, 13));
        btnBack.setFont(new Font("Arial", Font.PLAIN, 13));
        btnNewGame.addActionListener(e -> resetGame());
        btnBack.addActionListener(e -> {
            MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
            menuFrame.setVisible(true);
            this.dispose();
        });
        bottomPanel.add(btnNewGame);
        bottomPanel.add(btnBack);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void handlePlayerMove(int index) {
        if (gameOver) return;
//make player move using gameLogic.makeMove(index, 'X')
        boolean moved = gameLogic.makeMove(index, 'X');
        if (!moved) {
            JOptionPane.showMessageDialog(this,
                    "Sel sudah terisi! Pilih sel lain.",
                    "Tidak Valid", JOptionPane.WARNING_MESSAGE);
            return;
        }
//update button text to X
        //nampilkan x di papan
        buttons[index].setText("X");
        buttons[index].setForeground(new Color(0, 102, 204));
        buttons[index].setEnabled(false);
//check whether player wins
        // apakah sudah ada 3 X beruntun ?
        if (gameLogic.checkWinner('X')) {
            finishGame("WIN");
            return;
        }
//check draw
        //jika 9 kotak sudah terisi tapi gaada yang menang
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        lblStatus.setText("Giliran komputer...");
//generate computer move
        //untuk program gerak komputernya
        int computerIndex = gameLogic.computerMove();
        if (computerIndex != -1) {
            gameLogic.makeMove(computerIndex, 'O');
            //update computer button text to O
           // nampilkan o di kotak pilihan komputer
            buttons[computerIndex].setText("O");
            buttons[computerIndex].setForeground(new Color(204, 0, 0));
            buttons[computerIndex].setEnabled(false);
//check whether computer wins
            //apakah ada 3 o berurutan
            if (gameLogic.checkWinner('O')) {
                finishGame("LOSE");
                return;
            }

            if (gameLogic.isDraw()) {
                finishGame("DRAW");
                return;
            }
        }

        lblStatus.setText("Giliran kamu! (X)");
    }

    private void finishGame(String result) {
        gameOver = true;

        for (JButton btn : buttons) {
            btn.setEnabled(false);
        }
//update database statistics after game ends
        //simpan hasil ke database
        playerService.updateStatistics(currentPlayer, result);

        String message;
        if (result.equals("WIN")) {
            lblStatus.setText("Kamu Menang!");
            message = "Selamat! Kamu MENANG! (+10 poin)";
        } else if (result.equals("LOSE")) {
            lblStatus.setText("Kamu Kalah!");
            message = "Kamu KALAH! Lebih baik lagi lain kali.";
        } else {
            lblStatus.setText("Seri!");
            message = "SERI! Pertandingan yang ketat. (+3 poin)";
        }

        int option = JOptionPane.showOptionDialog(this,
                message + "\n\nMau main lagi?",
                "Hasil Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Main Lagi", "Kembali ke Menu"},
                "Main Lagi");

        if (option == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
            menuFrame.setVisible(true);
            this.dispose();
        }
    }

    private void resetGame() {
        gameLogic.resetBoard();
        gameOver = false;
        for (JButton btn : buttons) {
            btn.setText("");
            btn.setEnabled(true);
            btn.setForeground(Color.BLACK);
            btn.setBackground(Color.WHITE);
        }
        lblStatus.setText("Giliran kamu! (X)");
    }
}