/**
 * Copyright © 2017 Infest Developer Team.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.jogjadamai.infest.administrator;

/**
 * <h1>class <code>ChangePasswordDialog</code></h1>
 * <p><code>ChangePasswordDialog</code> is a <code>javax.swing.JDialog</code> class
 * for querying new set of Administrator's password to user.</p>
 * <br>
 * <p><b><i>Coded, built, and packaged with passion by Danang Galuh Tegar P for Infest.</i></b></p>
 * 
 * @author Danang Galuh Tegar P
 * @version 2017.03.10.0001
 */
public final class ChangePasswordDialog extends javax.swing.JDialog {
    
    public ChangePasswordDialog() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initialiseComponents();
    }
                      
    private void initialiseComponents() {

        adminCurrentPasswordLabel = new javax.swing.JLabel();
        currentPasswordField = new javax.swing.JPasswordField();
        separator = new javax.swing.JSeparator();
        newPasswordField = new javax.swing.JPasswordField();
        adminNewPasswordLabel = new javax.swing.JLabel();
        adminConfirmPasswordLabel = new javax.swing.JLabel();
        confirmPasswordField = new javax.swing.JPasswordField();
        changePasswordButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        verifiedPasswordLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Change Administrator Password");
        setAlwaysOnTop(true);
        setName("changePasswordDialog");
        setResizable(false);
        setType(java.awt.Window.Type.NORMAL);
        setModal(true);

        adminCurrentPasswordLabel.setText("Current Password");
        adminCurrentPasswordLabel.setDoubleBuffered(true);
        adminCurrentPasswordLabel.setName("adminCurrentPasswordLabel"); // NOI18N

        currentPasswordField.setDoubleBuffered(true);
        currentPasswordField.setName("currentPasswordField"); // NOI18N
        currentPasswordField.addActionListener(this::changePasswordButtonActionPerformed);

        separator.setDoubleBuffered(true);

        newPasswordField.setDoubleBuffered(true);
        newPasswordField.setName("newPasswordField"); // NOI18N
        newPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                newPasswordKeyReleased(evt);
            }
        });
        newPasswordField.addActionListener(this::changePasswordButtonActionPerformed);

        adminNewPasswordLabel.setText("New Password");
        adminNewPasswordLabel.setDoubleBuffered(true);
        adminNewPasswordLabel.setName("adminNewPasswordLabel"); // NOI18N

        adminConfirmPasswordLabel.setText("Confirm Password");
        adminConfirmPasswordLabel.setDoubleBuffered(true);
        adminConfirmPasswordLabel.setName("adminConfirmPasswordLabel"); // NOI18N

        confirmPasswordField.setDoubleBuffered(true);
        confirmPasswordField.setName("confirmPasswordField"); // NOI18N
        confirmPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                confirmPasswordFieldKeyReleased(evt);
            }
        });
        confirmPasswordField.addActionListener(this::changePasswordButtonActionPerformed);

        changePasswordButton.setText("Change Password");
        changePasswordButton.setDoubleBuffered(true);
        changePasswordButton.setName("changePasswordButton"); // NOI18N
        changePasswordButton.addActionListener(this::changePasswordButtonActionPerformed);

        cancelButton.setText("Cancel");
        cancelButton.setDoubleBuffered(true);
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(this::cancelButtonActionPerformed);

        verifiedPasswordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        verifiedPasswordLabel.setText(" ");
        verifiedPasswordLabel.setDoubleBuffered(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(separator)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(adminCurrentPasswordLabel)
                        .addGap(48, 48, 48)
                        .addComponent(currentPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(confirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(adminConfirmPasswordLabel)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(adminNewPasswordLabel))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(48, 48, 48)
                                    .addComponent(newPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(changePasswordButton))))
                        .addComponent(verifiedPasswordLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adminCurrentPasswordLabel)
                    .addComponent(currentPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminNewPasswordLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminConfirmPasswordLabel)
                    .addComponent(confirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(verifiedPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(changePasswordButton)
                    .addComponent(cancelButton))
                .addGap(50, 50, 50))
        );

        currentPasswordField.getAccessibleContext().setAccessibleName("adminCurrentPasswordField");
        newPasswordField.getAccessibleContext().setAccessibleName("adminNewPasswordField");
        confirmPasswordField.getAccessibleContext().setAccessibleName("adminVerificationPasswordField");
        changePasswordButton.getAccessibleContext().setAccessibleName("changePasswordAdminButton");
        cancelButton.getAccessibleContext().setAccessibleName("cancelChangePasswordAdmin");
        verifiedPasswordLabel.getAccessibleContext().setAccessibleName("verifiedPasswordLabel");

        pack();
    }                    

    private void confirmPasswordFieldKeyReleased(java.awt.event.KeyEvent evt) {                                                 
        if(this.newPasswordField.getPassword().length != 0 && this.confirmPasswordField.getPassword().length != 0) {
            verifyPassword();
        } else {
            this.verifiedPasswordLabel.setText("");
        }
    }                                                

    private void newPasswordKeyReleased(java.awt.event.KeyEvent evt) {                                        
        if(this.newPasswordField.getPassword().length != 0 && this.confirmPasswordField.getPassword().length != 0) {
            verifyPassword();
        } else {
            this.verifiedPasswordLabel.setText("");
        }
    }                                       

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }                                            

    private void changePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        if(areAllFieldsNotBlank()) {
            if(verifyPassword()) administrator.changePassword(this);
            else javax.swing.JOptionPane.showMessageDialog(this, 
                    "New Password does not meet requirements!\n"
                            + "\n"
                            + "Neither your new password matches verification password,\n"
                            + "nor your new passwords have 8 or more characters.\n"
                            + "Please verify and try again!",
                    "INFEST: Credentials Manager", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }                                                    
    
    // Bussiness Logic starts here
    
    public void run() {
        administrator = com.jogjadamai.infest.administrator.Administrator.getInstance();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private Boolean areAllFieldsNotBlank() {
        Boolean b = true;
        String blankFields = "";
        if(currentPasswordField.getPassword().length == 0) {
            b = false;
            blankFields = "Current Password";
        }
        if(newPasswordField.getPassword().length == 0) {
            blankFields = blankFields + ((b) ? "New Password" : ", New Password");
            b = false;
        }
        if(confirmPasswordField.getPassword().length == 0) {
            blankFields = blankFields + ((b) ? "Confirm New Password" : ", Confirm New Password");
            b = false;
        }
        if(!b) 
            javax.swing.JOptionPane.showMessageDialog(this, 
                    "These required fields are left blank. Please fill all required fields.\n"
                            + "\n"
                            + "Blank Field(s): " + blankFields + ".",
                    "INFEST: Credentials Manager", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        return b;
    }
    
    protected Boolean verifyPassword(){
        String newPassword = "";
        for (char character : confirmPasswordField.getPassword()) {
            newPassword = newPassword + character;
        }
        String verificationPassword = "";
        for (char character : newPasswordField.getPassword()) {
            verificationPassword = verificationPassword + character;
        }
        if (newPassword.equals(verificationPassword)) {
            if(newPassword.length() >= 8) {
                verifiedPasswordLabel.setText("Passwords match and strong.");
                verifiedPasswordLabel.setForeground(java.awt.Color.BLUE);
                return true;
            } else {
                verifiedPasswordLabel.setText("Paswords at least have 8 characters.");
                verifiedPasswordLabel.setForeground(java.awt.Color.RED);
                return false;
            }
        } else {
            verifiedPasswordLabel.setText("Passwords does not match.");
            verifiedPasswordLabel.setForeground(java.awt.Color.RED);
            return false;
        }
    }
    
    
    // Variables declaration - do not modify                     
    private javax.swing.JLabel adminConfirmPasswordLabel;
    private javax.swing.JLabel adminCurrentPasswordLabel;
    private javax.swing.JLabel adminNewPasswordLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton changePasswordButton;
    protected javax.swing.JPasswordField confirmPasswordField;
    protected javax.swing.JPasswordField currentPasswordField;
    protected javax.swing.JPasswordField newPasswordField;
    private javax.swing.JSeparator separator;
    private javax.swing.JLabel verifiedPasswordLabel;
    private com.jogjadamai.infest.administrator.Administrator administrator;
    // End of variables declaration                   
    
}
