package backend.main.repository;

import backend.main.config.DatabaseConfig;
import backend.main.model.Patient;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class PatientRepositoryImpl implements PatientRepository {

    @Override
    public Optional<Patient> findByPhone(String phone) {
        String sql = "SELECT p.* FROM Patient p JOIN PatientPhone pp ON p.Pid = pp.pid WHERE pp.phone = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToPatient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Patient> findByIdentity(String name, String bloodType, Integer age, String gender) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM Patient WHERE Name = ? AND BloodType = ? AND Age = ? AND Gender = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, bloodType);
            stmt.setInt(3, age);
            stmt.setString(4, gender);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToPatient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Patient> findById(Integer patientId) {
        String sql = "SELECT * FROM Patient WHERE Pid = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToPatient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM Patient";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapRowToPatient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int save(Patient patient) {
        String sql = "INSERT INTO Patient (Name, BloodType, Age, Gender, MedicalHistory) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getBloodType());
            stmt.setInt(3, patient.getAge());
            stmt.setString(4, patient.getGender());
            stmt.setString(5, patient.getMedicalHistory());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        patient.setPid(generatedKeys.getInt(1));
                    }
                }
            }
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Patient patient) {
        String sql = "UPDATE Patient SET Name=?, BloodType=?, Age=?, Gender=?, MedicalHistory=? WHERE Pid=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getBloodType());
            stmt.setInt(3, patient.getAge());
            stmt.setString(4, patient.getGender());
            stmt.setString(5, patient.getMedicalHistory());
            stmt.setInt(6, patient.getPid());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteById(Integer patientId) {
        String sql = "DELETE FROM Patient WHERE Pid=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // --- Special queries ---

    @Override
    public List<Patient> findByBloodType(String bloodType) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM Patient WHERE BloodType = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bloodType);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToPatient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Patient> findUrgentCases() {
        List<Patient> list = new ArrayList<>();
        String sql =
            "SELECT p.* FROM Patient p " +
            "JOIN RequestTimes r ON p.Pid = r.PatientId " +
            "WHERE r.Status = 'Critical'";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapRowToPatient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Tìm bệnh nhân có một từ khóa trong MedicalHistory
     */
    public List<Patient> findByMedicalHistoryKeyword(String keyword) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM Patient WHERE MedicalHistory LIKE ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToPatient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Đếm số bệnh nhân đăng ký trong tuần vừa qua
     */
    public int countRegistrationsLastWeek() {
        String sql =
            "SELECT COUNT(*) FROM Patient " +
            "WHERE RegistrationDate >= DATE_SUB(NOW(), INTERVAL 7 DAY)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // --- Helper ---
    private Patient mapRowToPatient(ResultSet rs) throws SQLException {
        Patient p = new Patient();
        p.setPid(rs.getInt("Pid"));
        p.setName(rs.getString("Name"));
        p.setBloodType(rs.getString("BloodType"));
        p.setAge(rs.getInt("Age"));
        p.setGender(rs.getString("Gender"));
        p.setMedicalHistory(rs.getString("MedicalHistory"));
        Timestamp reg = rs.getTimestamp("RegistrationDate");
        if (reg != null) p.setRegistrationDate(reg.toLocalDateTime());
        Timestamp upd = rs.getTimestamp("LastUpdated");
        if (upd != null) p.setLastUpdated(upd.toLocalDateTime());
        return p;
    }
}
