import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Employee {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtSalary;
    private JTextField txtMobileNumber;
    private JButton saveButton;
    private JTable table;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtid;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect()
    {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/abccompany", "root","Mani@11235813");
            System.out.println("Successs");
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();

        }
    }
    void table_load()
    {
        try
        {
            pst = con.prepareStatement("select * from employee");
            ResultSet rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Employee() {

        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String EmpName,Salary,Mobile;
                EmpName = txtName.getText();
                Salary = txtSalary.getText();
                Mobile = txtMobileNumber.getText();

                try {
                    if(EmpName.length()>0 && Salary.length()>0 && Mobile.length()>0){
                        pst = con.prepareStatement("insert into employee(EmpName,Salary,Mobile)values(?,?,?)");
                        pst.setString(1, EmpName);
                        pst.setString(2, Salary);
                        pst.setString(3, Mobile);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                        table_load();
                        txtName.setText("");
                        txtSalary.setText("");
                        txtMobileNumber.setText("");
                        txtName.requestFocus();
                    }else{
                        JOptionPane.showMessageDialog(null, "Invalid Data!!!!!");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String empid = txtid.getText();

                    pst = con.prepareStatement("select empname,salary,mobile from employee where id = ?");
                    pst.setString(1, empid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next())
                    {
                        String empname = rs.getString(1);
                        String emsalary = rs.getString(2);
                        String emmobile = rs.getString(3);

                        txtName.setText(empname);
                        txtSalary.setText(emsalary);
                        txtMobileNumber.setText(emmobile);

                    }
                    else
                    {
                        txtName.setText("");
                        txtSalary.setText("");
                        txtMobileNumber.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Employee No");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empid,empname,salary,mobile;
                empname = txtName.getText();
                salary = txtSalary.getText();
                mobile = txtMobileNumber.getText();
                empid = txtid.getText();

                try {
                    pst = con.prepareStatement("update employee set empname = ?,salary = ?,mobile = ? where id = ?");
                    pst.setString(1, empname);
                    pst.setString(2, salary);
                    pst.setString(3, mobile);
                    pst.setString(4, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobileNumber.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empid;
                empid = txtid.getText();

                try {
                    pst = con.prepareStatement("delete from employee  where id = ?");

                    pst.setString(1, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobileNumber.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }
        });
    }
}
