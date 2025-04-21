package backend.main.repository;

import backend.main.config.DatabaseConfig;
import backend.main.model.RequestTimes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class RequestTimesRepositoryImpl implements RequestTimesRepository {
    @Override
    public List<RequestTimes> findAll() {
        List<RequestTimes> list = new ArrayList<>();
        String sql = "SELECT * FROM RequestTimes";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(mapRowToRequestTimes(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public List<RequestTimes> findByPatientId(Integer patientId) {
        List<RequestTimes> list = new ArrayList<>();
        String sql = "SELECT * FROM RequestTimes WHERE patientId = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToRequestTimes(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
public int save(RequestTimes request) {
    boolean hasRequestDate = request.getRequestDate() != null;
    String sql;
    if (hasRequestDate) {
        sql = "INSERT INTO RequestTimes (did, bid, patientId, requestDate, bloodType, quantity, urgency, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    } else {
        sql = "INSERT INTO RequestTimes (did, bid, patientId, bloodType, quantity, urgency, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }
    try (Connection conn = DatabaseConfig.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setInt(1, request.getDid());
        stmt.setInt(2, request.getBid());
        stmt.setInt(3, request.getPatientId());
        int idx = 4;
        if (hasRequestDate) {
            stmt.setTimestamp(idx++, java.sql.Timestamp.valueOf(request.getRequestDate()));
        }
        stmt.setString(idx++, request.getBloodType());
        stmt.setInt(idx++, request.getQuantity());
        stmt.setString(idx++, request.getUrgency());
        stmt.setString(idx++, request.getStatus());
        int affectedRows = stmt.executeUpdate();
        // Optionally fetch generated keys if needed
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    request.setRequestId(generatedKeys.getInt(1));
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
    public int deleteByPatientId(Integer patientId) {
        String sql = "DELETE FROM RequestTimes WHERE patientId = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private RequestTimes mapRowToRequestTimes(ResultSet rs) throws SQLException {
        RequestTimes rt = new RequestTimes();
        rt.setRequestId(rs.getInt("requestId"));
        rt.setDid(rs.getInt("did"));
        rt.setBid(rs.getInt("bid"));
        rt.setPatientId(rs.getInt("patientId"));
        Timestamp ts = rs.getTimestamp("requestDate");
        rt.setRequestDate(ts != null ? ts.toLocalDateTime() : null);
        rt.setBloodType(rs.getString("bloodType"));
        rt.setQuantity(rs.getInt("quantity"));
        rt.setUrgency(rs.getString("urgency"));
        rt.setStatus(rs.getString("status"));
        return rt;
    }
}
