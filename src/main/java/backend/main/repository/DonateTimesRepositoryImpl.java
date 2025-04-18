package backend.main.repository;

import backend.main.config.DatabaseConfig;
import backend.main.model.DonateTimes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class DonateTimesRepositoryImpl implements DonateTimesRepository {
    @Override
    public List<DonateTimes> findByDonorId(Integer donorId) {
        List<DonateTimes> list = new ArrayList<>();
        String sql = "SELECT * FROM DonateTimes WHERE donorId = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToDonateTimes(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int save(DonateTimes donation) {
        String sql = "INSERT INTO DonateTimes (bid, donorId, donationDate, quantity, notes) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donation.getBid());
            stmt.setInt(2, donation.getDonorId());
            if (donation.getDonationDate() != null) {
                stmt.setTimestamp(3, Timestamp.valueOf(donation.getDonationDate()));
            } else {
                stmt.setNull(3, Types.TIMESTAMP);
            }
            stmt.setInt(4, donation.getQuantity());
            stmt.setString(5, donation.getNotes());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteByDonorId(Integer donorId) {
        String sql = "DELETE FROM DonateTimes WHERE donorId = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private DonateTimes mapRowToDonateTimes(ResultSet rs) throws SQLException {
        DonateTimes dt = new DonateTimes();
        dt.setDonationId(rs.getInt("id"));
        dt.setBid(rs.getInt("bid"));
        dt.setDonorId(rs.getInt("donorId"));
        Timestamp ts = rs.getTimestamp("donationDate");
        dt.setDonationDate(ts != null ? ts.toLocalDateTime() : null);
        dt.setQuantity(rs.getInt("quantity"));
        dt.setNotes(rs.getString("notes"));
        return dt;
    }

    @Override
    public Optional<DonateTimes> findById(Integer donationId) {
        String sql = "SELECT * FROM DonateTimes WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToDonateTimes(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<DonateTimes> findAll() {
        List<DonateTimes> list = new ArrayList<>();
        String sql = "SELECT * FROM DonateTimes";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(mapRowToDonateTimes(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int update(DonateTimes donation) {
        String sql = "UPDATE DonateTimes SET bid = ?, donorId = ?, donationDate = ?, quantity = ?, notes = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donation.getBid());
            stmt.setInt(2, donation.getDonorId());
            if (donation.getDonationDate() != null) {
                stmt.setTimestamp(3, Timestamp.valueOf(donation.getDonationDate()));
            } else {
                stmt.setNull(3, Types.TIMESTAMP);
            }
            stmt.setInt(4, donation.getQuantity());
            stmt.setString(5, donation.getNotes());
            stmt.setInt(6, donation.getDonationId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteById(Integer donationId) {
        String sql = "DELETE FROM DonateTimes WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donationId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
