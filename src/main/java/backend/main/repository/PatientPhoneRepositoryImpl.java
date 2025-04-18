package backend.main.repository;

import backend.main.config.DatabaseConfig;
import backend.main.model.PatientPhone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientPhoneRepositoryImpl implements PatientPhoneRepository {
    @Override
    public List<PatientPhone> findByPatientId(Integer pid) {
        List<PatientPhone> list = new ArrayList<>();
        String sql = "SELECT * FROM PatientPhone WHERE pid = ?";
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
        String sql = "INSERT INTO PatientPhone (pid, phone, isPrimary) VALUES (?, ?, ?)";
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
        String sql = "DELETE FROM PatientPhone WHERE pid = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pid);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private PatientPhone mapRowToPatientPhone(ResultSet rs) throws SQLException {
        PatientPhone pp = new PatientPhone();
        
        pp.setPid(rs.getInt("pid"));
        pp.setPhone(rs.getString("phone"));
        pp.setIsPrimary(rs.getBoolean("isPrimary"));
        return pp;
    }
}
