/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banker.review;

import java.awt.print.PrinterException;
import java.sql.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

import ConnectionPool.NewClass;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import ServerMessages.Messages;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author victor
 */
public class Review_profile extends javax.swing.JFrame {

    private static ResultSet rs;

    /**
     * Creates new form Review_profile
     */
    public Review_profile() {
        initComponents();
        NamesLabels();
        UsernameLabel();
        Update_Current_Table();
        Update_Saving_table();
        Update_Deposit_table();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    // zatvarqne na potoka sled zatvarqne na glavniq panel
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(Messages.Svurzvane.connection.getOutputStream()));
                    out.write("employee: "+LogIn.getEmployeeID()+" has logged out.");
                    out.flush();
                    out.close();
                } catch (Exception ex) {
                }
                System.exit(0);
            }
        });
    }
    
    // metod za preoverka dali klient e blokiran i ako e mu se zabranqvat tranzakcii i periodichni plashtaniq 
    public void isLocked(){
        try{
            String isLocked="SELECT is_active FROM customer_list WHERE pin_id='"+LoadCustomer.getEgn()+"' ";
            rs = NewClass.Vrazka.st.executeQuery(isLocked);
            rs.next();
            String active = rs.getString("is_active");
            rs.close();
            
            if(active.equals("0")){
                Review_profile.jMenu6.setEnabled(false);
                Review_profile.jMenuItem24.setEnabled(false);
                Review_profile.jMenuItem23.setEnabled(false);
            }
        }catch(Exception ex){}
    }


    public void NamesLabels() {
        try {
            String names = "SELECT name, surname, father_name FROM customer_list WHERE "
                    + "pin_id='" + LoadCustomer.getEgn() + "' ";
            // vzimane na danni
            NewClass.Vrazka.pst = NewClass.Vrazka.con.prepareStatement(names);
            rs = NewClass.Vrazka.pst.executeQuery();
            
            while (rs.next()) {
                // vizualizirane na danni za imenata 
                String add1 = rs.getString("name");
                customerLbl.setText(add1);
                String add2 = rs.getString("surname");
                customerLbl2.setText(add2);
                String add3 = rs.getString("father_name");
                customerLbl3.setText(add3);
            }
            rs.close();
            NewClass.Vrazka.pst.close();
        } catch (Exception ex) {
        }
    }
    
    public static void UsernameLabel() {
        try {
            String username = "SELECT username_id FROM customer_username WHERE"
                    + " customer_list_id='" + LoadCustomer.getEgn() + "' ";
            // vzimane na danni
            rs = NewClass.Vrazka.st.executeQuery(username);
            rs.next();
            // vizualizirane na username
            String customerUsername = rs.getString("username_id");
            usernameLbl.setText(customerUsername);
            rs.close();
        } catch (Exception ex) {
           
        }
    }

    public static void Update_Current_Table() {
        try {
            String tableCurrency = "SELECT iban_id as 'IBAN' , balance as 'Баланс', exchange_rate_id as 'Валута',"
                    + " creation_date as 'Дата на съзадаване' FROM "
                    + "account_type_current "
                    + "WHERE customer_list_id='" + LoadCustomer.getEgn() + "' and is_blocked='0' ";
            // vzimane na danni
            NewClass.Vrazka.pst = NewClass.Vrazka.con.prepareStatement(tableCurrency);
            rs.close();
            rs = NewClass.Vrazka.pst.executeQuery();
            // postavqne na dannite v tablica 
            currentTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {}
    }

    public static void Update_Saving_table() {
        try {
            String tableSavings = "SELECT iban_id as 'IBAN', balance as 'Баланс', exchange_rate_id as 'Валута',"
                    + " creation_date as 'Дата на съзадаване'  FROM account_type_saving "
                    + "WHERE customer_list_id='" + LoadCustomer.getEgn() + "' and is_blocked='0' ";
            // vzimane na danni
            NewClass.Vrazka.pst = NewClass.Vrazka.con.prepareStatement(tableSavings);
            rs = NewClass.Vrazka.pst.executeQuery();
            // postavqne na dannite v tablica 
            savingTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
        }
    }

    public static void Update_Deposit_table() {
        try {
            String tableDeposit = "SELECT iban_id as 'IBAN', deposit_amount as 'депозит', "
                    + "amount_after_deposit as 'след депозит',interest_rate as 'лихвен процент',"
                    + "next_accumulation_date as 'следваща дата',balance as 'баланс', exchange_rate_id as 'валута', "
                    + "unblock_date as 'дата на отблокиране' FROM account_type_deposit "
                    + "WHERE customer_list_id='" + LoadCustomer.getEgn() + "' and is_blocked='0' ";
            // vzimane na danni
            NewClass.Vrazka.pst = NewClass.Vrazka.con.prepareStatement(tableDeposit);
            rs = NewClass.Vrazka.pst.executeQuery();
            // postavqne na dannite v tablica 
            depositTable.setModel(DbUtils.resultSetToTableModel(rs));
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

        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        JLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Customer = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        usernameLbl = new javax.swing.JLabel();
        customerLbl = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Tabbes = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        currentTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        savingTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        depositTable = new javax.swing.JTable();
        Block = new javax.swing.JButton();
        customerLbl2 = new javax.swing.JLabel();
        customerLbl3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        changeUsername = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        closeAcc = new javax.swing.JMenu();
        closeCurrentAcc = new javax.swing.JMenuItem();
        closeSavingAcc = new javax.swing.JMenuItem();
        closeDepositAcc = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        jMenuItem6.setText("jMenuItem6");

        jMenuItem7.setText("jMenuItem7");

        jMenu3.setText("jMenu3");

        jMenuItem13.setText("jMenuItem13");

        jMenuItem19.setText("jMenuItem19");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Преглед на профил");
        setLocation(new java.awt.Point(250, 150));
        setResizable(false);
        setSize(new java.awt.Dimension(600, 600));

        jLabel2.setText("Потребителско Име: ");

        Customer.setText("Потребител: ");

        usernameLbl.setText("празно");

        customerLbl.setText("празно");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Сметки: "));

        currentTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(currentTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        Tabbes.addTab("Разплащателни", jPanel3);

        savingTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(savingTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        Tabbes.addTab("Спестовни", jPanel4);

        depositTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(depositTable);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        Tabbes.addTab("Депозитни", jPanel5);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Tabbes)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Tabbes)
                .addContainerGap())
        );

        Block.setText("Блокирай");
        Block.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Block))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Block)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        customerLbl2.setText("празно");

        customerLbl3.setText("празно");

        jButton2.setText("още..");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jMenu1.setText("Файл");

        jMenuItem14.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem14.setText("Зареждане на клиент");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem14);
        jMenu1.add(jSeparator1);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Отписване");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator2);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Принтирай");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);
        jMenu1.add(jSeparator6);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem8.setText("Изход");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Действия");

        jMenu4.setText("Клиенти");

        jMenuItem16.setText("Добавяне на клиент");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem16);

        jMenuItem4.setText("Създаване на потребителски профил");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuItem18.setText("Смяна на PIN");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem18);

        changeUsername.setText("Смяна на потребителско име");
        changeUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeUsernameActionPerformed(evt);
            }
        });
        jMenu4.add(changeUsername);

        jMenuItem20.setText("Действия на потребителя");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem20);

        jMenu2.add(jMenu4);

        jMenu5.setText("Сметки");

        jMenu6.setText("Създаване на сметка");

        jMenuItem9.setText("Разплащателна");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem9);

        jMenuItem11.setText("Спестовна");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem11);

        jMenuItem10.setText("Депозитна");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem10);

        jMenu5.add(jMenu6);

        jMenuItem22.setText("Движения по сметки");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem22);

        jMenuItem23.setText("Дейсвтия със сметки");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem23);

        jMenuItem24.setText("Периодични услуги");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem24);

        closeAcc.setText("Закриване на сметка");

        closeCurrentAcc.setText("Разплащателна");
        closeCurrentAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeCurrentAccActionPerformed(evt);
            }
        });
        closeAcc.add(closeCurrentAcc);

        closeSavingAcc.setText("Спестовна");
        closeSavingAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeSavingAccActionPerformed(evt);
            }
        });
        closeAcc.add(closeSavingAcc);

        closeDepositAcc.setText("Депозитна");
        closeDepositAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeDepositAccActionPerformed(evt);
            }
        });
        closeAcc.add(closeDepositAcc);

        jMenu5.add(closeAcc);

        jMenu2.add(jMenu5);

        jMenuItem1.setText("Валутна таблица");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem15.setText("История");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem15);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Customer, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(customerLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(customerLbl2)
                                .addGap(18, 18, 18)
                                .addComponent(customerLbl3)
                                .addGap(28, 28, 28))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(usernameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jButton2)
                        .addGap(102, 102, 102)
                        .addComponent(JLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(Customer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customerLbl)
                            .addComponent(customerLbl2)
                            .addComponent(customerLbl3)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(usernameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(JLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(700, 482));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        try {
            // zatvarqne na potoko sled zatvarqne na glavniq panel
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(Messages.Svurzvane.connection.getOutputStream()));
            out.write("employee: " + LogIn.getEmployeeID() + " has logged out.");
            out.flush();
            out.close();

            String history = "INSERT INTO employee_history (employee_username_id, action_description, "
                    + "action_time) VALUES ('" + LogIn.getEmployeeID() + "', "
                    + "'has logged out! ', SYSDATETIME()) ";
            // vuvejdane na danni za log out v bazata danni
            NewClass.Vrazka.st.execute(history);
            System.exit(0);
        } catch (SQLException ex) {
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        AccountMovements accountMovements = new AccountMovements();
        accountMovements.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        AddCustomer addCustomer = new AddCustomer();
        addCustomer.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        LoadCustomer loadCustomer = new LoadCustomer();
        loadCustomer.setVisible(true);
        LoadCustomer.setEgn(null);
        dispose();
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        ChangePIN changePIN = new ChangePIN();
        changePIN.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        CustomerActionsTest customerActions = new CustomerActionsTest();
        customerActions.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        try {
            PeriodicServices periodicServices = new PeriodicServices();
            periodicServices.setVisible(true);
            Review_profile.jMenuBar1.setEnabled(false);
            Review_profile.closeAcc.setEnabled(false);
            Review_profile.Block.setEnabled(false);
            Review_profile.jButton2.setEnabled(false);
            Review_profile.jMenu2.setEnabled(false);
            Review_profile.jMenu1.setEnabled(false);
        } catch (ClassNotFoundException ex) {
        }
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        HistoryTest history = new HistoryTest();
        history.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        CurrencyTable currencyTable = new CurrencyTable();
        currencyTable.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            LogIn logIn = new LogIn();
            logIn.setVisible(true);
            LoadCustomer.setEgn(null);

            String history = "INSERT INTO employee_history (employee_username_id, action_description, "
                    + "action_time) VALUES ('" + LogIn.getEmployeeID() + "', "
                    + "'has logged out! ', SYSDATETIME()) ";
            NewClass.Vrazka.st.execute(history);
            dispose();
        } catch (SQLException ex) {}
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        AddDepositAccount addDepositAccount = new AddDepositAccount();
        addDepositAccount.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        AddCurrentAccount addCurrentAccount = new AddCurrentAccount();
        addCurrentAccount.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        AddSavingAccount addSavingAccount = new AddSavingAccount();
        addSavingAccount.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        Transactions transactions = new Transactions();
        transactions.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void closeCurrentAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeCurrentAccActionPerformed
        CloseCurrentAccount closeCurrentAccount = new CloseCurrentAccount();
        closeCurrentAccount.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_closeCurrentAccActionPerformed

    private void closeSavingAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeSavingAccActionPerformed
        CloseSavingAccount closeSavingAccount = new CloseSavingAccount();
        closeSavingAccount.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_closeSavingAccActionPerformed

    private void closeDepositAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeDepositAccActionPerformed
        CloseDepositAccount closeDepositAccount = new CloseDepositAccount();
        closeDepositAccount.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_closeDepositAccActionPerformed

    private void changeUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeUsernameActionPerformed
        ChangeUsername changeUsername = new ChangeUsername();
        changeUsername.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_changeUsernameActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setVisible(true);
            Review_profile.jMenuBar1.setEnabled(false);
            Review_profile.closeAcc.setEnabled(false);
            Review_profile.Block.setEnabled(false);
            Review_profile.jButton2.setEnabled(false);
            Review_profile.jMenu2.setEnabled(false);
            Review_profile.jMenu1.setEnabled(false);

            String history = "INSERT INTO employee_history (employee_username_id, action_description, "
                    + "action_time) VALUES ('" + LogIn.getEmployeeID() + "', "
                    + "'has opened " + LoadCustomer.getEgn() + " personal information. ', SYSDATETIME()) ";
            NewClass.Vrazka.st.execute(history);
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        try {
            currentTable.print();
            savingTable.print();
            depositTable.print();
            
            String history = "INSERT INTO employee_history (employee_username_id, action_description, "
                    + "action_time) VALUES ('" + LogIn.getEmployeeID() + "', "
                    + "'has printed "+LoadCustomer.getEgn()+" account tables. ', SYSDATETIME()) ";
            NewClass.Vrazka.st.execute(history);
        } catch (PrinterException ex) {
        } catch (SQLException ex) {}
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void BlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BlockActionPerformed
        try {
            String check = "SELECT is_active FROM customer_list WHERE pin_id='" + LoadCustomer.getEgn() + "' ";
            rs = NewClass.Vrazka.st.executeQuery(check);
            rs.next();
            String active = rs.getString("is_active");
            rs.close();

            if (active.equals("1")) {
                String closeCurrentAcc = "UPDATE account_type_current SET is_blocked='1' WHERE customer_list_id='" + LoadCustomer.getEgn() + "' ";
                NewClass.Vrazka.st.executeUpdate(closeCurrentAcc);

                String closeSavingAcc = "UPDATE account_type_saving SET is_blocked='1' WHERE customer_list_id='" + LoadCustomer.getEgn() + "' ";
                NewClass.Vrazka.st.executeUpdate(closeSavingAcc);

                String closeDepositAcc = "UPDATE account_type_deposit SET is_blocked='1' WHERE customer_list_id='" + LoadCustomer.getEgn() + "' ";
                NewClass.Vrazka.st.executeUpdate(closeDepositAcc);

                String blockPeriodic = "";
                NewClass.Vrazka.st.executeUpdate(blockPeriodic);

                String blockUserAccount = "UPDATE customer_list SET is_active='0' WHERE pin_id='" + LoadCustomer.getEgn() + "' ";
                NewClass.Vrazka.st.executeUpdate(blockUserAccount);

                try {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(Messages.Svurzvane.connection.getOutputStream()));
                    out.write("employee: " + LogIn.getEmployeeID() + " has blocked the client: " + LoadCustomer.getEgn() + ".");
                    out.flush();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

                String history = "INSERT INTO employee_history (employee_username_id, action_description, "
                        + "action_time) VALUES ('" + LogIn.getEmployeeID() + "', "
                        + "'has blocked the client: " + LoadCustomer.getEgn() + ". ', SYSDATETIME()) ";
                NewClass.Vrazka.st.execute(history);

                JOptionPane.showMessageDialog(null, " All accounts were blocked.  ");
                JOptionPane.showMessageDialog(null, " Customer were deactivated.  ");
                this.dispose();
                LoadCustomer loadCustomer = new LoadCustomer();
                loadCustomer.setVisible(true);
            } else {
                Block.setText("Отблокирай");

                String closeCurrentAcc = "UPDATE account_type_current SET is_blocked='0' WHERE customer_list_id='" + LoadCustomer.getEgn() + "' ";
                NewClass.Vrazka.st.executeUpdate(closeCurrentAcc);

                String closeSavingAcc = "UPDATE account_type_saving SET is_blocked='0' WHERE customer_list_id='" + LoadCustomer.getEgn() + "' ";
                NewClass.Vrazka.st.executeUpdate(closeSavingAcc);

                String closeDepositAcc = "UPDATE account_type_deposit SET is_blocked='0' WHERE customer_list_id='" + LoadCustomer.getEgn() + "' ";
                NewClass.Vrazka.st.executeUpdate(closeDepositAcc);

                String blockUserAccount = "UPDATE customer_list SET is_active='1' WHERE pin_id='" + LoadCustomer.getEgn() + "' ";
                NewClass.Vrazka.st.executeUpdate(blockUserAccount);

                try {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(Messages.Svurzvane.connection.getOutputStream()));
                    out.write("employee: " + LogIn.getEmployeeID() + " has unblocked the client: " + LoadCustomer.getEgn() + ".");
                    out.flush();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                
                String history = "INSERT INTO employee_history (employee_username_id, action_description, "
                        + "action_time) VALUES ('" + LogIn.getEmployeeID() + "', "
                        + "'has unblocked the client: " + LoadCustomer.getEgn() + ". ', SYSDATETIME()) ";
                NewClass.Vrazka.st.execute(history);

                JOptionPane.showMessageDialog(null, " All accounts were blocked.  ");
                JOptionPane.showMessageDialog(null, " Customer were deactivated.  ");
                this.dispose();
            }
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_BlockActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        AddCustomerUserProfile profile = new AddCustomerUserProfile();
        profile.setVisible(true);
        Review_profile.jMenuBar1.setEnabled(false);
        Review_profile.closeAcc.setEnabled(false);
        Review_profile.Block.setEnabled(false);
        Review_profile.jButton2.setEnabled(false);
        Review_profile.jMenu2.setEnabled(false);
        Review_profile.jMenu1.setEnabled(false);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jLabel1ActionPerformed(java.awt.event.ActionEvent evt) {
       // LoadCustomer loadCustomer = new LoadCustomer();
        //loadCustomer.jTextField2ActionPerformed();

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton Block;
    private javax.swing.JLabel Customer;
    private javax.swing.JLabel JLabel1;
    private javax.swing.JTabbedPane Tabbes;
    public static javax.swing.JMenuItem changeUsername;
    public static javax.swing.JMenu closeAcc;
    public static javax.swing.JMenuItem closeCurrentAcc;
    public static javax.swing.JMenuItem closeDepositAcc;
    public static javax.swing.JMenuItem closeSavingAcc;
    public static javax.swing.JTable currentTable;
    private javax.swing.JLabel customerLbl;
    private javax.swing.JLabel customerLbl2;
    private javax.swing.JLabel customerLbl3;
    private static javax.swing.JTable depositTable;
    public static javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    public static javax.swing.JMenu jMenu1;
    public static javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    public static javax.swing.JMenu jMenu6;
    public static javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    public static javax.swing.JMenuItem jMenuItem10;
    public static javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    public static javax.swing.JMenuItem jMenuItem16;
    public static javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    public static javax.swing.JMenuItem jMenuItem20;
    public static javax.swing.JMenuItem jMenuItem22;
    public static javax.swing.JMenuItem jMenuItem23;
    public static javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    public static javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    public static javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private static javax.swing.JTable savingTable;
    private static javax.swing.JLabel usernameLbl;
    // End of variables declaration//GEN-END:variables
}
