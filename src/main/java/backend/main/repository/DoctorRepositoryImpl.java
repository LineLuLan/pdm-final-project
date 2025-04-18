package backend.main.repository;

import backend.main.config.DatabaseConfig;
import backend.main.model.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class DoctorRepositoryImpl implements DoctorRepository {
    @Override
    public Optional<Doctor> findById(Integer id) {
        String sql = "SELECT * FROM Doctor WHERE did = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToDoctor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM Doctor";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                doctors.add(mapRowToDoctor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    @Override
    public int save(Doctor doctor) {
        String sql = "INSERT INTO Doctor (name, licenseNumber, specialization, email, pid) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setDoctorParameters(stmt, doctor, false);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Doctor doctor) {
        String sql = "UPDATE Doctor SET name = ?, licenseNumber = ?, specialization = ?, email = ?, pid = ? WHERE did = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setDoctorParameters(stmt, doctor, true);
            stmt.setInt(6, doctor.getDid());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteById(Integer id) {
        String sql = "DELETE FROM Doctor WHERE did = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Doctor mapRowToDoctor(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setDid(rs.getInt("did"));
        doctor.setName(rs.getString("name"));
        doctor.setLicenseNumber(rs.getString("licenseNumber"));
        doctor.setSpecialization(rs.getString("specialization"));
        doctor.setEmail(rs.getString("email"));
        doctor.setPid(rs.getObject("pid") != null ? rs.getInt("pid") : null);
        return doctor;
    }

    private void setDoctorParameters(PreparedStatement stmt, Doctor doctor, boolean isUpdate) throws SQLException {
        stmt.setString(1, doctor.getName());
        stmt.setString(2, doctor.getLicenseNumber());
        stmt.setString(3, doctor.getSpecialization());
        stmt.setString(4, doctor.getEmail());
        if (doctor.getPid() != null) {
            stmt.setInt(5, doctor.getPid());
        } else {
            stmt.setNull(5, java.sql.Types.INTEGER);
        }
    }

    @Override
    public List<Doctor> findBySpecialization(String specialization) {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM Doctor WHERE specialization = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, specialization);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                doctors.add(mapRowToDoctor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }
}
