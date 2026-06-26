package game;

import javax.swing.*;
import java.awt.*;
//menampilkan statistik pemain seperti data menang, kalah, seri,dan skor dari database
public class StatisticsFrame extends JFrame {

    private PlayerService playerService;

    public StatisticsFrame(Player player) {
        playerService = new PlayerService();

        setTitle("Statistik Saya");
        setSize(320, 330);
        setLocationRelativeTo(null);
        setResizable(false);

        // Ambil data terbaru dari database
        Player freshPlayer = playerService.getPlayerById(player.getId());
        if (freshPlayer == null) {
            freshPlayer = player;
        }

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("Statistik Pemain", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 17));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);

        JLabel lblName = new JLabel(freshPlayer.getUsername(), SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.PLAIN, 14));
        lblName.setForeground(Color.GRAY);
        gbc.gridy = 1;
        mainPanel.add(lblName, gbc);

        JSeparator sep = new JSeparator();
        gbc.gridy = 2;
        mainPanel.add(sep, gbc);

        gbc.gridwidth = 1;
        addStatRow(mainPanel, gbc, "Menang :", String.valueOf(freshPlayer.getWins()), 3, new Color(0, 150, 0));
        addStatRow(mainPanel, gbc, "Kalah  :", String.valueOf(freshPlayer.getLosses()), 4, new Color(180, 0, 0));
        addStatRow(mainPanel, gbc, "Seri   :", String.valueOf(freshPlayer.getDraws()), 5, new Color(100, 100, 100));
        addStatRow(mainPanel, gbc, "Skor   :", String.valueOf(freshPlayer.getScore()), 6, new Color(180, 120, 0));

        JButton btnClose = new JButton("Tutup");
        btnClose.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        btnClose.addActionListener(e -> this.dispose());
        mainPanel.add(btnClose, gbc);

        add(mainPanel);
    }

    private void addStatRow(JPanel panel, GridBagConstraints gbc,
                            String label, String value, int row, Color valueColor) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(lbl, gbc);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Arial", Font.BOLD, 14));
        val.setForeground(valueColor);
        gbc.gridx = 1; gbc.gridy = row;
        panel.add(val, gbc);
    }
}