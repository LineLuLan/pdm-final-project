package backend.main.repository;

import backend.main.config.DatabaseConfig;
import backend.main.model.BloodBankStock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Repository;

@Repository
public class BloodBankStockRepositoryImpl implements BloodBankStockRepository {
    @Override
    public List<BloodBankStock> findAll() {
        List<BloodBankStock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM BloodBankStock";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                stocks.add(mapRowToBloodBankStock(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    @Override
    public List<BloodBankStock> findByBloodBankId(Integer bankId) {
        List<BloodBankStock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM BloodBankStock WHERE bid = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bankId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stocks.add(mapRowToBloodBankStock(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    @Override
    public int save(BloodBankStock stock) {
        String sql = "INSERT INTO BloodBankStock (bid, stockId, assignmentDate) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setBloodBankStockParameters(stmt, stock);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteByBloodBankId(Integer bankId) {
        String sql = "DELETE FROM BloodBankStock WHERE bid = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bankId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }











    private BloodBankStock mapRowToBloodBankStock(ResultSet rs) throws SQLException {
        BloodBankStock stock = new BloodBankStock();
        stock.setBid(rs.getInt("bid"));
        stock.setStockId(rs.getInt("stockId"));
        Timestamp ts = rs.getTimestamp("assignmentDate");
        stock.setAssignmentDate(ts != null ? ts.toLocalDateTime() : null);
        return stock;
    }

    private void setBloodBankStockParameters(PreparedStatement stmt, BloodBankStock stock) throws SQLException {
        stmt.setInt(1, stock.getBid());
        stmt.setInt(2, stock.getStockId());
        if (stock.getAssignmentDate() != null) {
            stmt.setTimestamp(3, Timestamp.valueOf(stock.getAssignmentDate()));
        } else {
            stmt.setNull(3, java.sql.Types.TIMESTAMP);
        }
    }
}
