package backend.main.sqlclient;

import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

public class ConnectSQL {
    static final String connectionUrl = "jdbc:mysql://localhost:3306/blood_donation?useSSL=false&serverTimezone=UTC";
    static final String user = "root";
    static final String password = "Anh03032005";

    public static Connection getConnect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(connectionUrl, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }

    public static void closeConnect(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void showQuery(String sql, JTable resultTable) throws SQLException {
        try (Connection con = getConnect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            TableModel model = DbUtils.resultSetToTableModel(rs);
            resultTable.setModel(model);
        }
    }

    public static int executeUpdate(String sql) throws SQLException {
        try (Connection con = getConnect();
             Statement stmt = con.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }
}
