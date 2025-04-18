package backend.main.repository;

import backend.main.config.DatabaseConfig;
import backend.main.model.BloodStock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class BloodStockRepositoryImpl implements BloodStockRepository {
    public Optional<BloodStock> findById(Integer id) {
        String sql = "SELECT * FROM BloodStock WHERE stockId = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToBloodStock(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<BloodStock> findAll() {
        List<BloodStock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM BloodStock";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                stocks.add(mapRowToBloodStock(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    @Override
    public int save(BloodStock stock) {
        String sql = "INSERT INTO BloodStock (stockId, bloodType, quantity, status, expirationDate, storageLocation) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setBloodStockParameters(stmt, stock, false);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(BloodStock stock) {
        String sql = "UPDATE BloodStock SET bloodType = ?, quantity = ?, status = ?, expirationDate = ?, storageLocation = ? WHERE stockId = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setBloodStockParameters(stmt, stock, true);
            stmt.setInt(6, stock.getStockId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteById(Integer id) {
        String sql = "DELETE FROM BloodStock WHERE stockId = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<BloodStock> findByBloodType(String bloodType) {
        List<BloodStock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM BloodStock WHERE bloodType = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bloodType);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stocks.add(mapRowToBloodStock(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    @Override
    public List<BloodStock> findLowStock(Integer threshold) {
        List<BloodStock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM BloodStock WHERE quantity < ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, threshold);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stocks.add(mapRowToBloodStock(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    private BloodStock mapRowToBloodStock(ResultSet rs) throws SQLException {
        BloodStock stock = new BloodStock();
        stock.setStockId(rs.getInt("stockId"));
        stock.setBloodType(rs.getString("bloodType"));
        stock.setQuantity(rs.getInt("quantity"));
        stock.setStatus(rs.getString("status"));
        Date date = rs.getDate("expirationDate");
        stock.setExpirationDate(date != null ? date.toLocalDate() : null);
        stock.setStorageLocation(rs.getString("storageLocation"));
        return stock;
    }

    private void setBloodStockParameters(PreparedStatement stmt, BloodStock stock, boolean isUpdate) throws SQLException {
        stmt.setInt(1, stock.getStockId());
        stmt.setString(2, stock.getBloodType());
        stmt.setInt(3, stock.getQuantity());
        stmt.setString(4, stock.getStatus());
        stmt.setDate(5, stock.getExpirationDate() != null ? Date.valueOf(stock.getExpirationDate()) : null);
        stmt.setString(6, stock.getStorageLocation());
    }
}
