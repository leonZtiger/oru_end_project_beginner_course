/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ngo2024.pages;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import ngo2024.DataContainers.AccessLevel;
import ngo2024.Main;
import ngo2024.util.validationClass;
import oru.inf.InfException;

/**
 *
 * @author Fam van L
 */
public class MyProfilePanel extends TabPageBase {
    
    validationClass validator; 
    
    /**
     * Creates new form MyProfilePanel
     * @param aPane
     */
    public MyProfilePanel(JTabbedPane aPane) {
        super(aPane, "Ändra uppgifter");
        validator = new validationClass();
        
        addTab();
        
      
        try {
            lblFirstname.setText("Förnamn:   " + Main.infDB.fetchSingle("SELECT fornamn FROM anstalld WHERE aid = " + Main.user.getAid()));
            lblLastname.setText("Efternamn:   " + Main.infDB.fetchSingle("SELECT efternamn FROM anstalld WHERE aid = " + Main.user.getAid()));
            lblAddress.setText("Adress:   " + Main.infDB.fetchSingle("SELECT adress FROM anstalld WHERE aid = " + Main.user.getAid()));
            lblDepartment.setText("Avdelning:   " + Main.infDB.fetchSingle("SELECT avdelning FROM anstalld WHERE aid = " + Main.user.getAid()));
            lblEmail.setText("Epost:   " + Main.infDB.fetchSingle("SELECT epost FROM anstalld WHERE aid = " + Main.user.getAid()));
            lblPassword.setText("Lösenord:   " + Main.infDB.fetchSingle("SELECT losenord FROM anstalld WHERE aid = " + Main.user.getAid()));
            lblPhonenumber.setText("Telefonnummer:   " + Main.infDB.fetchSingle("SELECT telefon FROM anstalld WHERE aid = " + Main.user.getAid()));

       
        
        } catch (InfException ex) {
            Logger.getLogger(MyProfilePanel.class.getName()).log(Level.SEVERE, null, ex);
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

        lblPassword = new javax.swing.JLabel();
        lblFirstname = new javax.swing.JLabel();
        lblLastname = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblDepartment = new javax.swing.JLabel();
        BtnPassword = new javax.swing.JButton();
        btnFirstname = new javax.swing.JButton();
        btnLastname = new javax.swing.JButton();
        btnAddress = new javax.swing.JButton();
        btnPhonenumber = new javax.swing.JButton();
        btnDepartment = new javax.swing.JButton();
        btnEmail = new javax.swing.JButton();
        lblPhonenumber = new javax.swing.JLabel();
        userMessage = new javax.swing.JLabel();

        lblPassword.setText("Lösenord");

        lblFirstname.setText("Förnamn");

        lblLastname.setText("Efternamn");

        lblAddress.setText("Adress");

        lblEmail.setText("email");

        lblDepartment.setText("Avdelning");

        BtnPassword.setText("Ändra");
        BtnPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPasswordActionPerformed(evt);
            }
        });

        btnFirstname.setText("Ändra");
        btnFirstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstnameActionPerformed(evt);
            }
        });

        btnLastname.setText("Ändra");
        btnLastname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastnameActionPerformed(evt);
            }
        });

        btnAddress.setText("Ändra");
        btnAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddressActionPerformed(evt);
            }
        });

        btnPhonenumber.setText("Ändra");
        btnPhonenumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhonenumberActionPerformed(evt);
            }
        });

        btnDepartment.setText("Ändra");
        btnDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepartmentActionPerformed(evt);
            }
        });

        btnEmail.setText("Ändra");
        btnEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmailActionPerformed(evt);
            }
        });

        lblPhonenumber.setText("Telefonnummer");

        userMessage.setForeground(new java.awt.Color(0, 255, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmail)
                            .addComponent(lblAddress)
                            .addComponent(lblLastname)
                            .addComponent(lblFirstname)
                            .addComponent(lblPassword)
                            .addComponent(lblDepartment)
                            .addComponent(lblPhonenumber))
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(BtnPassword)
                                .addComponent(btnFirstname)
                                .addComponent(btnAddress)
                                .addComponent(btnLastname)
                                .addComponent(btnPhonenumber)
                                .addComponent(btnDepartment))
                            .addComponent(btnEmail))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(BtnPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstname)
                    .addComponent(btnFirstname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLastname)
                    .addComponent(lblLastname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddress)
                    .addComponent(btnAddress))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblEmail)
                    .addComponent(btnEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPhonenumber)
                    .addComponent(lblPhonenumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDepartment)
                    .addComponent(btnDepartment))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userMessage)
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private void BtnPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPasswordActionPerformed

      try{
            String pw = JOptionPane.showInputDialog(null, "Skriv in gamla lösenordet: ", "Input", JOptionPane.QUESTION_MESSAGE);
            String losenord = Main.infDB.fetchSingle("SELECT losenord FROM anstalld WHERE aid = '" + Main.user.getAid() + "';");
            if (pw.equals(losenord)) {
                String newpw = JOptionPane.showInputDialog(null, "Skriv in nytt lösenord: ", "Input", JOptionPane.QUESTION_MESSAGE); 
                String query = "UPDATE anstalld SET losenord = '" + newpw + "' WHERE aid = '" + Main.user.getAid() + "';";
                Main.infDB.update(query);
                 userMessage.setText("Starta om denna sida för att se ändringar!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Kunde inte ändra lösenordet!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (InfException ex)
        {

        }

    }//GEN-LAST:event_BtnPasswordActionPerformed
  
    @Override
    protected void initiate() {
        initComponents();
    }
    
    private void btnFirstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstnameActionPerformed
        
   try{
            String nyttFornamn = JOptionPane.showInputDialog(null, "Enter new firstname", "Input", JOptionPane.QUESTION_MESSAGE); 

            if (nyttFornamn.isEmpty()){
            JOptionPane.showMessageDialog(null, "Could not change firstname", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (validator.isLetters(nyttFornamn)) {
                String query = "UPDATE anstalld SET fornamn = '" + nyttFornamn + "' WHERE aid = '" + Main.user.getAid() + "';";
                Main.infDB.update(query);
                userMessage.setText("Starta om denna sida för att se ändringar!");
            }
            else{
                JOptionPane.showMessageDialog(null, null, "Could not change firstname", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (InfException ex)
        {

        } 
        
    }//GEN-LAST:event_btnFirstnameActionPerformed

    private void btnLastnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastnameActionPerformed
      
     try{

            String nyttEfternamn = JOptionPane.showInputDialog(null, "Skriv in efternamn", "Input", JOptionPane.QUESTION_MESSAGE); 

            if (nyttEfternamn.isEmpty()){
            JOptionPane.showMessageDialog(null, "Ange ett namn!", "Error", JOptionPane.ERROR_MESSAGE);
            } 
            else if (validator.isLetters(nyttEfternamn)) {
                String query = "UPDATE anstalld SET efternamn = '" + nyttEfternamn + "' WHERE aid = '" + Main.user.getAid() + "';";
                Main.infDB.update(query);
                userMessage.setText("Starta om denna sida för att se ändringar!");
            }
            else {
                JOptionPane.showMessageDialog(null, "Ange bokstäver!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (InfException ex)
        {

        }
        
    }//GEN-LAST:event_btnLastnameActionPerformed

    private void btnAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddressActionPerformed
    
        try{

            String nyAdress = JOptionPane.showInputDialog(null, "Skriv in ny adress", "Input", JOptionPane.QUESTION_MESSAGE); 

            if (nyAdress.isEmpty()){
            JOptionPane.showMessageDialog(null, "Kunde inte ändra adressen", "Error", JOptionPane.ERROR_MESSAGE);
            return;
            }

            if (nyAdress.length() >=3)
            {
                    //villkorsats som kollar om adressen börjar med tre siffror
                    if (validator.isNumbers(nyAdress.substring(0, 3))){
                        String query = "UPDATE anstalld SET adress = '" + nyAdress + "' WHERE aid = '" + Main.user.getAid() + "';";
                        Main.infDB.update(query);
                        userMessage.setText("Starta om denna sida för att se ändringar!");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Fel! Adressen måste börja med tre siffror", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
            else {
            JOptionPane.showMessageDialog(null, "Ange korrekt adressformat!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (InfException ex)
        {

        }
        
    }//GEN-LAST:event_btnAddressActionPerformed

    private void btnPhonenumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhonenumberActionPerformed
        
        try{

            String nyttTelefonnummer = JOptionPane.showInputDialog(null, "Skriv in telefonnummer", "Input", JOptionPane.QUESTION_MESSAGE); 
 
            if (nyttTelefonnummer.isEmpty()){
            JOptionPane.showMessageDialog(null, "Skrv in siffror!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
            }

            if ((nyttTelefonnummer.length()<10)|| (nyttTelefonnummer.length()>11)){
               JOptionPane.showMessageDialog(null, "Skrv in minst tio siffror!", "Error", JOptionPane.ERROR_MESSAGE); 
            }
            else if (validator.isNumbers(nyttTelefonnummer)) {
                String query = "UPDATE anstalld SET telefon = '" + nyttTelefonnummer + "' WHERE aid = '" + Main.user.getAid() + "';";
                Main.infDB.update(query);
                userMessage.setText("Starta om denna sida för att se ändringar!");
            }
        }
        catch (InfException ex)
        {

        }
        
    }//GEN-LAST:event_btnPhonenumberActionPerformed

    private void btnDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartmentActionPerformed
    
        try{

            String nyAvdelning = JOptionPane.showInputDialog(null, "Skriv in ny avdelning (siffra)", "Input", JOptionPane.QUESTION_MESSAGE); 

            if (nyAvdelning.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Skriv in en siffra!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (validator.isNumbers(nyAvdelning)) {
                String query = "UPDATE anstalld SET avdelning = '" + nyAvdelning + "' WHERE aid = '" + Main.user.getAid() + "';";
                Main.infDB.update(query);
                userMessage.setText("Starta om denna sida för att se ändringar!");
            }
            else {
                 JOptionPane.showMessageDialog(null, "Kunde inte ändra avdelning", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (InfException ex)
        {
            JOptionPane.showMessageDialog(null, "skriv in en siffra 1-3", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnDepartmentActionPerformed

    private void btnEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmailActionPerformed

       try{

            String nyEpost = JOptionPane.showInputDialog(null, "Skriv in ny epost", "Input", JOptionPane.QUESTION_MESSAGE);

            if (nyEpost.isEmpty()){
                 JOptionPane.showMessageDialog(null, "Skriv in en epost!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (!(nyEpost.endsWith("@example.com"))){
                 JOptionPane.showMessageDialog(null, "Epost måste sluta på '@example.com'", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                String query = "UPDATE anstalld SET epost = '" + nyEpost + "' WHERE aid = '" + Main.user.getAid() + "';";
                Main.infDB.update(query);
                userMessage.setText("Starta om denna sida för att se ändringar!");
            }
        }
        catch (InfException ex)
        {

        }


    }//GEN-LAST:event_btnEmailActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnPassword;
    private javax.swing.JButton btnAddress;
    private javax.swing.JButton btnDepartment;
    private javax.swing.JButton btnEmail;
    private javax.swing.JButton btnFirstname;
    private javax.swing.JButton btnLastname;
    private javax.swing.JButton btnPhonenumber;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFirstname;
    private javax.swing.JLabel lblLastname;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPhonenumber;
    private javax.swing.JLabel userMessage;
    // End of variables declaration//GEN-END:variables

    @Override
    protected boolean hasThePrivlege() {
        return Main.user != null && Main.user.getBehorig() != AccessLevel.GUEST;
    }

   
}
