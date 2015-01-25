package banker.review;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

import ConnectionPool.NewClass;
import ServerMessages.Messages;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author victor
 */

public class LoadCustomer extends javax.swing.JFrame {

    private ResultSet rs;
    private static String egn;
    /**
     * Creates new form LoadCustomer
     */
    public LoadCustomer() {
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
        loadEgn = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Зареждане на клиент");
        setLocation(new java.awt.Point(420, 300));
        setResizable(false);

        jLabel1.setText("ЕГН: ");

        loadEgn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                loadEgnKeyReleased(evt);
            }
        });

        jButton1.setText("Зареди");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Към приложението");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loadEgn, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(116, Short.MAX_VALUE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(123, 123, 123))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(loadEgn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // getters and setter za egn na vpisaniq klient
    public static void setEgn(String egn){
        LoadCustomer.egn=egn;
    }
    public static String getEgn(){
        return LoadCustomer.egn;
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            LoadCustomer.setEgn(loadEgn.getText().trim());
            
            String loadCustomer = "SELECT * FROM customer_list WHERE pin_id='" + LoadCustomer.getEgn() + "'";
            // vzemane na danni 
            rs = NewClass.Vrazka.st.executeQuery(loadCustomer);
            
            // proverka dali ima klient s tova egn
            int count = 0;
            while (rs.next()) {
                count = count + 1;
            }

            if (count == 1) {
                JOptionPane.showMessageDialog(null, "Customer found !!!");
                Review_profile reviewProfile = new Review_profile();
                reviewProfile.setVisible(true);
                
                String history = "INSERT INTO employee_history (employee_username_id, action_description, "
                        + "action_time) VALUES ('"+LogIn.getEmployeeID()+"', "
                        + "'has loaded customer: "+LoadCustomer.getEgn()+" ', SYSDATETIME()) ";
                // vuvejdane na informaciq za deistvieto izvursheno ot bankera v bazata danni
                NewClass.Vrazka.st.execute(history);

                // suobshtenie do server-a za izvurshenoto deistvie
                try {
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(Messages.Svurzvane.connection.getOutputStream()));
                    out.println("employee: "+LogIn.getEmployeeID() + " has loaded customer: " + LoadCustomer.getEgn() + ".");
                    out.flush();
                } catch (Exception ex) {
                }
            } else {
                JOptionPane.showMessageDialog(null, "Customer not found !!!");
                this.setVisible(false);
                new LoadCustomer().setVisible(true);
            }
            dispose();
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }    
    }//GEN-LAST:event_jButton1ActionPerformed

    private void loadEgnKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loadEgnKeyReleased

    }//GEN-LAST:event_loadEgnKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Review_profile reviewProfile = new Review_profile();
        reviewProfile.setVisible(true);
        Review_profile.jMenuItem18.setEnabled(false);
        Review_profile.changeUsername.setEnabled(false);
        Review_profile.jMenuItem20.setEnabled(false);
        Review_profile.jMenu6.setEnabled(false);
        Review_profile.jMenuItem9.setEnabled(false);
        Review_profile.jMenuItem5.setEnabled(false);
        Review_profile.jMenuItem10.setEnabled(false);
        Review_profile.jMenuItem11.setEnabled(false);
        Review_profile.jMenuItem22.setEnabled(false);
        Review_profile.jMenuItem24.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        LoadCustomer.setEgn("null");
        LoadCustomer.this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField loadEgn;
    // End of variables declaration//GEN-END:variables
}