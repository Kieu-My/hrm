package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;
import model.Position;

public class PositionDAO {

    // Lấy Position theo ID
    public Position getPositionById(int position_id) {
        String sql = "SELECT * FROM Positions WHERE position_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, position_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Position(
                        rs.getInt("position_id"),
                        rs.getString("position_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả Positions
    public List<Position> getAllPositions() {
        List<Position> list = new ArrayList<>();
        String sql = "SELECT * FROM Positions";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Position pos = new Position(
                        rs.getInt("position_id"),
                        rs.getString("position_name")
                );
                list.add(pos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
