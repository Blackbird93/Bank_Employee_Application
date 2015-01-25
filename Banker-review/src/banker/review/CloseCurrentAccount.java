/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banker.review;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

import ConnectionPool.NewClass;
import ServerMessages.Messages;

/**
 *
 * @author victor
 */
public class CloseCurrentAccount extends javax.swing.JFrame {
    
    private ResultSet rs;
    /**
     * Creates new form CloseAccount
     */
    public CloseCurrentAccount() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        iban = new javax.swing.JLabel();
        ibanField = new javax.swing.JTextField();
        owner = new javax.swing.JLabel();
        ownerField = new javax.swing.JTextField();
        CloseBtn = new javax.swing.JButton();
        closeAccCancel = new javax.swing.JButton();

        setTitle("Закриване на разплащателна сметка");
        setLocation(new java.awt.Point(310, 170));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                enableMenu(evt);
            }
        });

        iban.setText("IBAN: ");

        owner.setText("Титуляр(ЕГН): ");

        CloseBtn.setText("Закриване");
        CloseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseBtnActionPerformed(evt);
            }
        });

        closeAccCancel.setText("Отказ");
        closeAccCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeAccCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CloseBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(closeAccCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(iban)
                            .addComponent(owner))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ownerField, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ibanField, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iban)
                    .addComponent(ibanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ownerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(owner))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CloseBtn)
                    .addComponent(closeAccCancel))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void CloseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseBtnActionPerformed
        try {
            String iban = ibanField.getText().trim();
            String egn = ownerField.getText().trim();

            String check = "SELECT * FROM account_type_current WHERE iban_id='" + iban + "' and customer_list_id='" + egn + "' ";
            // proverka dali smetkata sushtestvuva po IBAN i egn na pritejatelq
            rs = NewClass.Vrazka.st.executeQuery(check);
            int count = 0;
            while (rs.next()) {
                count = count + 1;
            }
            if (count == 1) {
                String close = "UPDATE account_type_current SET is_blocked='1' WHERE iban_id='" + iban + "' and "
                        + "customer_list_id='" + egn + "' ";
                // vuvejdane na danni i deaktivaciq na smetkata v bazata danni 
                NewClass.Vrazka.st.executeUpdate(close);
                JOptionPane.showMessageDialog(null, "Account was removed!!!");
                // update na tablicata current sled deaktivirane na smetka
                Review_profile.Update_Current_Table();
                
                String history = "INSERT INTO employee_history (employee_username_id, action_description, "
                    + "action_time) VALUES ('" + LogIn.getEmployeeID() + "', "
                        + "'has deactivated " + LoadCustomer.getEgn() + " current account with iban: " + iban + ". ', SYSDATETIME()) ";
                // vuvejdane na danni za izvurshenoto ot bankera deistvie
                NewClass.Vrazka.st.execute(history);

                // suobshtenie do server-a za izvurshenoto deistvie 
                try {
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(Messages.Svurzvane.connection.getOutputStream()));
                    out.println("employee: " + LogIn.getEmployeeID() + " has deactivated " + LoadCustomer.getEgn() + " current account with iban:" + iban + ".");
                    out.flush();
                } catch (Exception ex) {
                }

            } else {
                JOptionPane.showMessageDialog(null, "Account not found!!!");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_CloseBtnActionPerformed

    private void closeAccCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeAccCancelActionPerformed
        CloseCurrentAccount.this.dispose();
    }//GEN-LAST:event_closeAccCancelActionPerformed

    private void enableMenu(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_enableMenu
        // aktivaciq na butoni i meuta v glavniq panel sled zatvarqne na tozi 
        Review_profile.jMenuBar1.setEnabled(true);
        Review_profile.closeAcc.setEnabled(true);
        Review_profile.Block.setEnabled(true);
        Review_profile.jButton2.setEnabled(true);
        Review_profile.jMenu2.setEnabled(true);
        Review_profile.jMenu1.setEnabled(true);
    }//GEN-LAST:event_enableMenu


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseBtn;
    private javax.swing.JButton closeAccCancel;
    private javax.swing.JLabel iban;
    private javax.swing.JTextField ibanField;
    private javax.swing.JLabel owner;
    private javax.swing.JTextField ownerField;
    // End of variables declaration//GEN-END:variables
}
