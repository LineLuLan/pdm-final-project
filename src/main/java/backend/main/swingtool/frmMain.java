package backend.main.swingtool;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class frmMain extends JFrame {
    private static frmMain instance;
    private JPanel panel;
    private JLabel lblQuery;
    private JTextField txtQuery;
    private JTable tblResult;
    private JButton btnRun;

    private frmMain() {
        panel = new JPanel();
        lblQuery = new JLabel("Enter SQL Query:");
        txtQuery = new JTextField(30);
        tblResult = new JTable();
        btnRun = new JButton("Execute");

        panel.setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        inputPanel.add(lblQuery);
        inputPanel.add(txtQuery);
        inputPanel.add(btnRun);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblResult), BorderLayout.CENTER);

        setContentPane(panel);
        setTitle("Blood Donation Query Tool");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnRun.addActionListener((ActionEvent e) -> {
            btnRun.setEnabled(false);
            if (txtQuery.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Query cannot be empty", "Warning", JOptionPane.WARNING_MESSAGE);
                btnRun.setEnabled(true);
                return;
            }

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    ConnectSQL.showQuery(txtQuery.getText(), tblResult);
                    return null;
                }

                @Override
                protected void done() {
                    btnRun.setEnabled(true);
                }
            };
            worker.execute();
        });
    }

    public static synchronized frmMain getInstance() {
        if (instance == null) {
            instance = new frmMain();
        }
        return instance;
    }
}
