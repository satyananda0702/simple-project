package game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
// menampilkan 5 pemain dengan skor tertinggi.
public class TopScorersFrame extends JFrame {

    private JTable table;
    private PlayerService playerService;

    public TopScorersFrame() {
        playerService = new PlayerService();

        setTitle("Top 5 Pemain");
        setSize(480, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Top 5 Pemain Terbaik", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 17));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        String[] columns = {"#", "Username", "Menang", "Kalah", "Seri", "Skor"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
//get Top 5 players from playerService
        ArrayList<Player> topPlayers = playerService.getTopFiveScorers();
        //add each player data into the table model
        for (int i = 0; i < topPlayers.size(); i++) {
            Player p = topPlayers.get(i);
            model.addRow(new Object[]{
                    i + 1,
                    p.getUsername(),
                    p.getWins(),
                    p.getLosses(),
                    p.getDraws(),
                    p.getScore()
            });
        }

        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.setRowHeight(28);
        table.setEnabled(false);

        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(65);
        table.getColumnModel().getColumn(3).setPreferredWidth(55);
        table.getColumnModel().getColumn(4).setPreferredWidth(50);
        table.getColumnModel().getColumn(5).setPreferredWidth(55);

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton btnClose = new JButton("Tutup");
        btnClose.setFont(new Font("Arial", Font.PLAIN, 13));
        btnClose.addActionListener(e -> this.dispose());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btnClose);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}