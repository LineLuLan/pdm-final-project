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
        String sql = "INSERT INTO RequestTimes (requestId, did, bid, patientId, requestDate, bloodType, quantity, urgency, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, request.getRequestId());
            stmt.setInt(2, request.getDid());
            stmt.setInt(3, request.getBid());
            stmt.setInt(4, request.getPatientId());
            if (request.getRequestDate() != null) {
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(request.getRequestDate()));
            } else {
                stmt.setNull(5, Types.TIMESTAMP);
            }
            stmt.setString(6, request.getBloodType());
            stmt.setInt(7, request.getQuantity());
            stmt.setString(8, request.getUrgency());
            stmt.setString(9, request.getStatus());
            return stmt.executeUpdate();
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
