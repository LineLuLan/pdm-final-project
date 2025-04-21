package backend.main.repository;

import backend.main.config.DatabaseConfig;
import backend.main.model.DonorPhone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Repository;

@Repository
public class DonorPhoneRepositoryImpl implements DonorPhoneRepository {
    // Kiểm tra tồn tại phone + name
    public boolean existsByPhoneAndName(String phone, String name) {
        String sql = "SELECT COUNT(*) FROM DonorPhone dp JOIN Donor d ON dp.donorId = d.donorId WHERE dp.phone = ? AND d.name = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone);
            stmt.setString(2, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
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

    public List<DonorPhone> getAllDonorPhones() {
        List<DonorPhone> list = new ArrayList<>();
        String sql = "SELECT * FROM DonorPhone";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToDonorPhone(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private DonorPhone mapRowToDonorPhone(ResultSet rs) throws SQLException {
        DonorPhone dp = new DonorPhone();

        dp.setDonorId(rs.getInt("donorId"));
        dp.setPhone(rs.getString("phone"));
        return dp;
    }
}
