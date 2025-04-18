package backend.main.sqlclient;

import javax.swing.*;
import java.awt.*;

public class frmMain extends JFrame {
    private JTextField txtSQL;
    private JButton btnExecute;
    private JTable tblResult;
    private JScrollPane scrollPane;

    public frmMain() {
        setTitle("SQL Server JDBC Demo");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtSQL = new JTextField();
        btnExecute = new JButton("Execute");
        tblResult = new JTable();
        scrollPane = new JScrollPane(tblResult);

        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("SQL:"), BorderLayout.WEST);
        topPanel.add(txtSQL, BorderLayout.CENTER);
        topPanel.add(btnExecute, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnExecute.addActionListener(e -> executeSQL());
    }

    private void executeSQL() {
        String sql = txtSQL.getText().trim();
        if (sql.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập câu lệnh SQL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        btnExecute.setEnabled(false);
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    if (sql.toLowerCase().startsWith("select")) {
                        ConnectSQL.showQuery(sql, tblResult);
                    } else {
                        int affected = ConnectSQL.executeUpdate(sql);
                        JOptionPane.showMessageDialog(frmMain.this, "Thành công! Số dòng ảnh hưởng: " + affected);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmMain.this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                return null;
            }
            @Override
            protected void done() {
                btnExecute.setEnabled(true);
            }
        };
        worker.execute();
    }
}
