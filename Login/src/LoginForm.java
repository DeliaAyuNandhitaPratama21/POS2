import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private koneksi koneksi;

    public LoginForm() {
        super("Login Form");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create username field
        txtUsername = new JTextField(20);
        add(new JLabel("Username:"));
        add(txtUsername);

        // Create password field
        txtPassword = new JPasswordField(20);
        add(new JLabel("Password:"));
        add(txtPassword);

        // Create login button
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginActionPerformed(e);
            }
        });
        add(btnLogin);

        // Create cancel button
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(btnCancel);

        koneksi = new koneksi(); // Initialize koneksi object
        setVisible(true);
    }

    private void loginActionPerformed(ActionEvent e) {
        String username = txtUsername.getText();
        char[] password = txtPassword.getPassword();

        // Validate username and password
        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password");
            return;
        }

        try {
            koneksi.connect(); // Connect to database
            Connection con = koneksi.getcon();

            // Prepare SQL query to verify username and password
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, new String(password)); // Convert char array to string

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Login successful, do something here
                System.out.println("Login successful!");
                // ...
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }

            // Close database connection
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + ex.getMessage());
        } finally {
            // Clear password field
            
        }
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}