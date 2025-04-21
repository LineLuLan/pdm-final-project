package backend.main.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import backend.main.config.DatabaseConfig;
import backend.main.model.Donor;

import org.springframework.stereotype.Repository;

@Repository
public class DonorRepositoryImpl implements DonorRepository {
    
    @Override
    public Optional<Donor> findById(Integer donorId) {
        String sql = "SELECT * FROM Donor WHERE donorId = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToDonor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Donor> findAll() {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM Donor";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                donors.add(mapRowToDonor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }

    @Override
    public int save(Donor donor) {
        String sql = "INSERT INTO Donor (name, gender, age, bloodType, weight, lastDonationDate, healthStatus, isEligible, registrationDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setDonorParameters(stmt, donor);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 1) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        System.out.println("[DEBUG] generatedKeys.getInt(1): " + id);
                        try {
                            String colName = generatedKeys.getMetaData().getColumnName(1);
                            System.out.println("[DEBUG] generatedKeys column name: " + colName);
                        } catch (Exception metaEx) {
                            System.err.println("[DEBUG] Could not get column name from generatedKeys: " + metaEx.getMessage());
                        }
                        donor.setDonorId(id);
                        System.out.println("[DEBUG] Inserted Donor with ID: " + id);
                    } else {
                        System.err.println("[ERROR] No donorId returned after insert! generatedKeys empty.");
                    }
                }
            } else {
                System.err.println("[ERROR] Insert donor failed, no rows affected!");
            }
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Donor donor) {
        String sql = "UPDATE Donor SET name = ?, gender = ?, age = ?, bloodType = ?, " +
                     "weight = ?, lastDonationDate = ?, healthStatus = ?, isEligible = ? " +
                     "WHERE donorId = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setDonorParameters(stmt, donor);
            stmt.setInt(9, donor.getDonorId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteById(Integer donorId) {
        String sql = "DELETE FROM Donor WHERE donorId = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donorId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Donor> findByBloodType(String bloodType) {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM Donor WHERE bloodType = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bloodType);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    donors.add(mapRowToDonor(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }

    @Override
    public List<Donor> findEligibleDonors() {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM Donor WHERE isEligible = TRUE";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                donors.add(mapRowToDonor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }

    // Helper method to map ResultSet to Donor object
    @Override
    public List<Donor> findByIdentity(String name, String bloodType, Integer age, String gender) {
        List<Donor> list = new ArrayList<>();
        String sql = "SELECT * FROM Donor WHERE name = ? AND bloodType = ? AND age = ? AND gender = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, bloodType);
            stmt.setInt(3, age);
            stmt.setString(4, gender);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToDonor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Donor mapRowToDonor(ResultSet rs) throws SQLException {
        Donor donor = new Donor();
        donor.setDonorId(rs.getInt("donorId"));
        donor.setName(rs.getString("name"));
        donor.setGender(rs.getString("gender"));
        donor.setAge(rs.getInt("age"));
        donor.setBloodType(rs.getString("bloodType"));
        donor.setWeight(rs.getDouble("weight"));
        
        Date date = rs.getDate("lastDonationDate");
        donor.setLastDonationDate(date != null ? date.toLocalDate() : null);
        
        donor.setHealthStatus(rs.getString("healthStatus"));
        donor.setIsEligible(rs.getBoolean("isEligible"));
        
        Timestamp timestamp = rs.getTimestamp("registrationDate");
        donor.setRegistrationDate(timestamp != null ? timestamp.toLocalDateTime() : null);
        
        return donor;
    }

    // Helper method to set PreparedStatement parameters
    private void setDonorParameters(PreparedStatement stmt, Donor donor) throws SQLException {
        stmt.setString(1, donor.getName());
        stmt.setString(2, donor.getGender());
        stmt.setInt(3, donor.getAge());
        stmt.setString(4, donor.getBloodType());
        stmt.setDouble(5, donor.getWeight());
        stmt.setDate(6, donor.getLastDonationDate() != null ? 
            Date.valueOf(donor.getLastDonationDate()) : null);
        stmt.setString(7, donor.getHealthStatus());
        stmt.setBoolean(8, donor.getIsEligible());
        // Xử lý registrationDate: nếu truyền lên là String có thể không đúng định dạng, cần kiểm tra kỹ
        try {
            if (donor.getRegistrationDate() != null) {
                stmt.setTimestamp(9, Timestamp.valueOf(donor.getRegistrationDate()));
            } else {
                stmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            }
        } catch (Exception e) {
            stmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
        }
    }
}