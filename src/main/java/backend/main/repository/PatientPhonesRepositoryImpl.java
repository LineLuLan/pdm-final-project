package backend.main.repository;

import backend.main.config.DatabaseConfig;
import backend.main.model.PatientPhones;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PatientPhonesRepositoryImpl implements PatientPhonesRepository {
    @Override
    public List<PatientPhones> getPatientPhonesByPatientId(Integer patientId) {
        List<PatientPhones> list = new ArrayList<>();
        String sql = "SELECT * FROM PatientPhone WHERE patientId = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PatientPhones pp = new PatientPhones();
                pp.setPatientId(rs.getInt("patientId"));
                pp.setPhone(rs.getString("phone"));
                list.add(pp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void addPatientPhone(PatientPhones patientPhones) {
        String sql = "INSERT INTO PatientPhone (patientId, phone) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientPhones.getPatientId());
            stmt.setString(2, patientPhones.getPhone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePatientPhone(PatientPhones patientPhones) {
        String sql = "UPDATE PatientPhone SET phone = ? WHERE patientId = ? AND phone = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientPhones.getPhone());
            stmt.setInt(2, patientPhones.getPatientId());
            stmt.setString(3, patientPhones.getPhone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePatientPhone(Integer patientId, String phone) {
        String sql = "DELETE FROM PatientPhone WHERE patientId = ? AND phone = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            stmt.setString(2, phone);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
