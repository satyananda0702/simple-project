package game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PlayerService {

    public Player login(String username, String password) {
        // query sql untuk mencari username & password di database
        String sql = "SELECT * FROM players WHERE username = ? AND password = ?";
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
//jika data ditemukan maka login berhasil
            if (rs.next()) {
                //mengambil data pemain dari database
                int id       = rs.getInt("id");
                String uname = rs.getString("username");
                int wins     = rs.getInt("wins");
                int losses   = rs.getInt("losses");
                int draws    = rs.getInt("draws");
                int score    = rs.getInt("score");

                return new Player(id, uname, wins, losses, draws, score);
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }
// mengupdate wins, losses, draws, dan score setelah game selesai
    public void updateStatistics(Player player, String result) {
        int additionalScore = 0;
        String sql = "";

        if (result.equalsIgnoreCase("WIN")) {
            additionalScore = 10;
            sql = "UPDATE players SET wins = wins + 1, score = score + ? WHERE id = ?";
        } else if (result.equalsIgnoreCase("LOSE")) {
            additionalScore = 0;
            sql = "UPDATE players SET losses = losses + 1, score = score + ? WHERE id = ?";
        } else if (result.equalsIgnoreCase("DRAW")) {
            additionalScore = 3;
            sql = "UPDATE players SET draws = draws + 1, score = score + ? WHERE id = ?";
        }
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, additionalScore);
            stmt.setInt(2, player.getId());

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Update statistics error: " + e.getMessage());
        }
    }
// method mengambil 5 pemain dengan skor tertinggi
    public ArrayList<Player> getTopFiveScorers() {
        ArrayList<Player> list = new ArrayList<>();
        String sql = "SELECT * FROM players ORDER BY score DESC, wins DESC LIMIT 5";
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id       = rs.getInt("id");
                String uname = rs.getString("username");
                int wins     = rs.getInt("wins");
                int losses   = rs.getInt("losses");
                int draws    = rs.getInt("draws");
                int score    = rs.getInt("score");
                list.add(new Player(id, uname, wins, losses, draws, score));
            }
        } catch (Exception e) {
            System.out.println("Get top scorers error: " + e.getMessage());
        }
        return list;
    }
// method untuk mengambil data pemain berdasarkan id
    public Player getPlayerById(int id) {
        String sql = "SELECT * FROM players WHERE id = ?";
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String uname = rs.getString("username");
                int wins     = rs.getInt("wins");
                int losses   = rs.getInt("losses");
                int draws    = rs.getInt("draws");
                int score    = rs.getInt("score");
                return new Player(id, uname, wins, losses, draws, score);
            }
        } catch (Exception e) {
            System.out.println("Get player error: " + e.getMessage());
        }
        return null;
    }
}