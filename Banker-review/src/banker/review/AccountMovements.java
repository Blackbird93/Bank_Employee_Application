/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banker.review;

import java.awt.print.PrinterException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import net.proteanit.sql.DbUtils;

import ConnectionPool.NewClass;
/**
 *
 * @author victor
 */
public class AccountMovements extends javax.swing.JFrame {

    private ResultSet rs;

    /**
     * Creates new form AccountMovements
     */
    public AccountMovements() {
        initComponents();
        Update_Current_Table();
        Update_Saving_Table();
        Update_Deposit_Table();
        Update_WithDraw_Table();
    }

    // metod za tablicata current
    private void Update_Current_Table() {
        try {            
            String sql = "SELECT transaction_id as 'номер на транзакция', execution_time as 'дата на извършване', "
                    + "orderer_account_type_current_id as 'IBAN на поръчителя', orderer_customer_list_id as 'ЕГН на поръчител', "
                    + "basis as 'основание', amount as 'сума', exchange_rate_id as 'валута' ,"
                    + "recipient_account_type_current_id as 'IBAN на получателя' FROM transaction_list_current_current"
                    + " WHERE orderer_customer_list_id='" + LoadCustomer.getEgn() + "' ";
            // vzimane na dannite za tablicata current na zaredeniq klient
            NewClass.Vrazka.pst = NewClass.Vrazka.con.prepareStatement(sql);
            rs = NewClass.Vrazka.pst.executeQuery();
            currentAccMovementTbl.setModel(DbUtils.resultSetToTableModel(rs));
           
//                String income = "SELECT orderer_account_type_current_id as 'IBAN на поръчителя' "
//                        + ",basis as 'основание' "
//                        + ",amount as 'сума' ,exchange_rate_id as 'валута' "
//                        + ",recipient_account_type_current_id 'IBAN на получателя' FROM periodic_payment "
//                        + "WHERE recipient_account_type_current_id='BG56MHLF49956638432019' ";
//                NewClass.Vrazka.pst = NewClass.Vrazka.con.prepareStatement(income);
//                rs = NewClass.Vrazka.pst.executeQuery();
//                currentAccMovementTbl.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    // metod za tablicata saving
    private void Update_Saving_Table() {
        try {
            String sql = "SELECT transaction_id as 'номер на транзакция', execution_time as 'дата на извършване', "
                    + "orderer_account_type_current_id as 'Вид на сметката', orderer_customer_list_id as 'ЕГН на поръчител', "
                    + "basis as 'основание', amount as 'сума', exchange_rate_id as 'валута', recipient_account_type_saving_id as 'IBAN на получател'FROM transaction_list_current_saving "
                    + " WHERE orderer_customer_list_id='" + LoadCustomer.getEgn() + "' ";
            // vzimane na dannite za za tablicata saving na zaredeniq klient
            NewClass.Vrazka.pst = NewClass.Vrazka.con.prepareStatement(sql);
            rs = NewClass.Vrazka.pst.executeQuery();
            savingAccMovementTbl.setModel(DbUtils.resultSetToTableModel(rs));

            String history = "INSERT INTO employee_history (employee_username_id, action_description, "
                    + "action_time) VALUES ('" + LogIn.getEmployeeID() + "', "
                    + "'has opened account movements of: " + LoadCustomer.getEgn() + " ', SYSDATETIME()) ";
            NewClass.Vrazka.st.execute(history);
        } catch (Exception ex) {
        }
    }
    // metod za tablicata deposit
    private void Update_Deposit_Table() {
        try {
            String sql = "SELECT transaction_id as 'номер на транзакция', execution_time as 'дата на извършване',"
                    + "orderer_account_type_deposit_id as 'Вид на сметката', "
                    + "orderer_customer_list_id as 'ЕГН на поръчител', "
                    + "basis as 'основание', amount as 'сума', exchange_rate_id as 'валута', "
                    + "recipient_account_type_current_id as 'IBAN на получател' FROM transaction_list_deposit_current"
                    + " WHERE orderer_customer_list_id='" + LoadCustomer.getEgn() + "' ";
            // vzimane na dannite za za tablicata deposit na zaredeniq klient
            NewClass.Vrazka.pst = NewClass.Vrazka.con.prepareStatement(sql);
            rs = NewClass.Vrazka.pst.executeQuery();
            depositAccMovementTbl.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
        }
    }
    // metod za tablicata teglene i vnasqne ot kasa
    private void Update_WithDraw_Table() {
        try {
            String sql = "SELECT execution_time as 'дата на извършване', orderer_account_type_current_id as 'IBAN'"
                    + ",orderer_customer_list_id as 'ЕГН на поръчител',"
                    + " amount as 'сума' , exchange_rate_id as 'валута' ,basis as 'основание' FROM transaction_list_current"
                    + " WHERE orderer_customer_list_id='" + LoadCustomer.getEgn() + "' ";
            // vzimane na dannite za za tablicata kasa na zaredeniq klient
            NewClass.Vrazka.pst = NewClass.Vrazka.con.prepareStatement(sql);
            rs = NewClass.Vrazka.pst.executeQuery();
            withDrawTable.setModel(DbUtils.resultSetToTableModel(rs));
            rs.close();

            String sql2 = "SELECT execution_time as 'дата на извършване', orderer_account_type_current_id as 'IBAN'"
                    + ",orderer_customer_list_id as 'ЕГН на поръчител',"
                    + " amount as 'сума' , exchange_rate_id as 'валута' FROM transaction_list_saving"
                    + " WHERE orderer_customer_list_id='" + LoadCustomer.getEgn() + "' ";
            NewClass.Vrazka.pst = NewClass.Vrazka.con.prepareStatement(sql2);
            rs = NewClass.Vrazka.pst.executeQuery();
            withDrawTable.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception ex) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        currentAccMovementTbl = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        savingAccMovementTbl = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        depositAccMovementTbl = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        withDrawTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Движение по сметки");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                enableMenu(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Движения по сметки: "));

        currentAccMovementTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(currentAccMovementTbl);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Разплащателни", jPanel2);

        savingAccMovementTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(savingAccMovementTbl);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Спестовни", jPanel3);

        depositAccMovementTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(depositAccMovementTbl);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Депозитни", jPanel4);

        withDrawTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(withDrawTable);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Каса", jPanel5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, Short.MAX_VALUE)
        );

        jMenu1.setText("Файл");

        jMenuItem1.setText("Принтирай");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(889, 443));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            // print na tablicite
            currentAccMovementTbl.print();
            savingAccMovementTbl.print();
            depositAccMovementTbl.print();
            withDrawTable.print();
        } catch (PrinterException ex) {
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void enableMenu(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_enableMenu
        // aktivirane na butonite i menutata v glavniq panel sled zatvarqne na tozi prozorec
        Review_profile.jMenuBar1.setEnabled(true);
        Review_profile.closeAcc.setEnabled(true);
        Review_profile.Block.setEnabled(true);
        Review_profile.jButton2.setEnabled(true);
        Review_profile.jMenu2.setEnabled(true);
        Review_profile.jMenu1.setEnabled(true);
    }//GEN-LAST:event_enableMenu


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable currentAccMovementTbl;
    private javax.swing.JTable depositAccMovementTbl;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable savingAccMovementTbl;
    private javax.swing.JTable withDrawTable;
    // End of variables declaration//GEN-END:variables
}
