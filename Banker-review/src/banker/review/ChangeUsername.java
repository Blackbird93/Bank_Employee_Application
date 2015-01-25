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
public class ChangeUsername extends javax.swing.JFrame {

    private ResultSet rs;

    /**
     * Creates new form ChangeUsername
     */
    public ChangeUsername() {
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

        jLabel1 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        newUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        changeUsername = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Смяна на потребителско име ");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                enableMenu(evt);
            }
        });

        jLabel1.setText("Потребителско име:");

        jLabel2.setText("Ново потребителско име: ");

        changeUsername.setText("Промени");
        changeUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeUsernameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(64, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(29, 29, 29)
                        .addComponent(newUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(76, 76, 76)
                            .addComponent(jLabel1)
                            .addGap(31, 31, 31)
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(changeUsername)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(12, 12, 12)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(changeUsername)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void changeUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeUsernameActionPerformed
        try {
            String user = username.getText().trim();

            String sql = "SELECT * FROM customer_username WHERE username_id = '" + user + "' ";
            // proverka dali ima takuv username
            rs = NewClass.Vrazka.st.executeQuery(sql);

            int count = 0;
            while (rs.next()) {
                count = count + 1;
            }
            if (count == 1) {
                JOptionPane.showMessageDialog(null, "Username found! Name changed. ");
                String newUser = newUsername.getText().trim();
                String update = "UPDATE customer_username SET username_id='" + newUser + "' WHERE username_id = '" + user + "' ";
                // smqna na potrebitelskoto ime v bazata danni
                NewClass.Vrazka.st.executeUpdate(update);
                // update na potrebitelskoto v glavniq panel
                Review_profile.UsernameLabel();

                // suobshtenie do server-a za izvurshenoto deistvie 
                try {
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(Messages.Svurzvane.connection.getOutputStream()));
                    out.println("employee: "+LogIn.getEmployeeID() + " has changed usarname " + user + " to " + newUser + ".");
                    out.flush();
                } catch (Exception ex) {
                }

                String history = "INSERT INTO employee_history (employee_username_id, action_description, "
                        + "action_time) VALUES ('" + LogIn.getEmployeeID() + "', "
                        + "'has changed usarname " + user + " to " + newUser + " ', SYSDATETIME()) ";
                // vuvejdane na danni za izvurshenoto deistvie ot bankera v bazata danni
                NewClass.Vrazka.st.execute(history);
                // zatvarqne na panela
                this.dispose();
                // aktivirane na butoni i menuta v glavniq panel
                Review_profile.jMenuBar1.setEnabled(true);
                Review_profile.closeAcc.setEnabled(true);
                Review_profile.Block.setEnabled(true);
                Review_profile.jButton2.setEnabled(true);
                Review_profile.jMenu2.setEnabled(true);
                Review_profile.jMenu1.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Username does not exist! ");
            }
        } catch (Exception ex) {
        }


    }//GEN-LAST:event_changeUsernameActionPerformed

    private void enableMenu(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_enableMenu
        // aktivirane na butoni i menuta v glavniq panel
        Review_profile.jMenuBar1.setEnabled(true);
        Review_profile.closeAcc.setEnabled(true);
        Review_profile.Block.setEnabled(true);
        Review_profile.jButton2.setEnabled(true);
        Review_profile.jMenu2.setEnabled(true);
        Review_profile.jMenu1.setEnabled(true);
    }//GEN-LAST:event_enableMenu


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changeUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField newUsername;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
