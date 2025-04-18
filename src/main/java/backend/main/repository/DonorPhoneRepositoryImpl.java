package backend.main.repository;

import backend.main.config.DatabaseConfig;
import backend.main.model.DonorPhone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Repository;

@Repository
public class DonorPhoneRepositoryImpl implements DonorPhoneRepository {
    @Override
    public List<DonorPhone> findByDonorId(Integer donorId) {
        List<DonorPhone> list = new ArrayList<>();
        String sql = "SELECT * FROM DonorPhone WHERE donorId = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToDonorPhone(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int save(DonorPhone donorPhone) {
        String sql = "INSERT INTO DonorPhone (donorId, phone) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorPhone.getDonorId());
            stmt.setString(2, donorPhone.getPhone());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteByDonorId(Integer donorId) {
        String sql = "DELETE FROM DonorPhone WHERE donorId = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private DonorPhone mapRowToDonorPhone(ResultSet rs) throws SQLException {
        DonorPhone dp = new DonorPhone();

        dp.setDonorId(rs.getInt("donorId"));
        dp.setPhone(rs.getString("phone"));
        return dp;
    }
}
