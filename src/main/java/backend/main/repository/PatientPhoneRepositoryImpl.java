package backend.main.repository;

import backend.main.config.DatabaseConfig;
import backend.main.model.PatientPhone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PatientPhoneRepositoryImpl implements PatientPhoneRepository {
    // Kiểm tra tồn tại phone + name
    public boolean existsByPhoneAndName(String phone, String name) {
        String sql = "SELECT COUNT(*) FROM PatientPhones pp JOIN Patient p ON pp.pid = p.Pid WHERE pp.phone = ? AND p.Name = ?";
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
    public List<PatientPhone> findByPatientId(Integer pid) {
        List<PatientPhone> list = new ArrayList<>();
        String sql = "SELECT * FROM PatientPhones WHERE pid = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToPatientPhone(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int save(PatientPhone patientPhone) {
        String sql = "INSERT INTO PatientPhones (pid, phone, isPrimary) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientPhone.getPid());
            stmt.setString(2, patientPhone.getPhone());
            stmt.setBoolean(3, patientPhone.getIsPrimary());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteByPatientId(Integer pid) {
        String sql = "DELETE FROM PatientPhones WHERE pid = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pid);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<PatientPhone> getAllPatientPhones() {
        List<PatientPhone> list = new ArrayList<>();
        String sql = "SELECT * FROM PatientPhones";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToPatientPhone(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private PatientPhone mapRowToPatientPhone(ResultSet rs) throws SQLException {
        PatientPhone pp = new PatientPhone();
        
        pp.setPid(rs.getInt("pid"));
        pp.setPhone(rs.getString("phone"));
        pp.setIsPrimary(rs.getBoolean("isPrimary"));
        return pp;
    }
}
