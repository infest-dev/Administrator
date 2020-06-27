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
 * <h1>class <code>OperatorCredentialsDialog</code></h1>
 * <p><code>OperatorCredentialsDialog</code> is a <code>javax.swing.JDialog</code> class
 * for querying new set of operator credentials to user.</p>
 * <br>
 * <p><b><i>Coded, built, and packaged with passion by Danang Galuh Tegar P for Infest.</i></b></p>
 * 
 * @author Danang Galuh Tegar P
 * @version 2017.03.10.0001
 */
public final class OperatorCredentialsDialog extends javax.swing.JDialog {
        /**
     * Creates new form AdministratorCredential
     */
    public OperatorCredentialsDialog() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initialiseComponents();
    }
                         
    private void initialiseComponents() {

        usernameLabel = new javax.swing.JLabel();
        newPasswordField = new javax.swing.JPasswordField();
        newPasswordLabel = new javax.swing.JLabel();
        confirmPasswordLabel = new javax.swing.JLabel();
        confirmPasswordField = new javax.swing.JPasswordField();
        setCredentialsButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        verifiedPasswordLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Set New Operator Credentials");
        setAlwaysOnTop(true);
        setName("operatorCredentialsDialog"); // NOI18N
        setResizable(false);
        setType(java.awt.Window.Type.NORMAL);
        setModal(true);

        usernameLabel.setText("Username");
        usernameLabel.setDoubleBuffered(true);
        usernameLabel.setName("operatorUsernameLabel"); // NOI18N

        newPasswordField.setDoubleBuffered(true);
        newPasswordField.setName("passwordField"); // NOI18N
        newPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                newPasswordFieldKeyReleased(evt);
            }
        });
        newPasswordField.addActionListener(this::setCredentialsButtonActionPerformed);

        newPasswordLabel.setText("Password");
        newPasswordLabel.setDoubleBuffered(true);
        newPasswordLabel.setName("operatorPasswordLabel"); // NOI18N

        confirmPasswordLabel.setText("Confirm Password");
        confirmPasswordLabel.setDoubleBuffered(true);
        confirmPasswordLabel.setName("operatorConfirmPasswordLabel"); // NOI18N

        confirmPasswordField.setDoubleBuffered(true);
        confirmPasswordField.setName("confirmPasswordField"); // NOI18N
        confirmPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                confirmPasswordFieldKeyReleased(evt);
            }
        });
        confirmPasswordField.addActionListener(this::setCredentialsButtonActionPerformed);

        setCredentialsButton.setText("Set Credentials");
        setCredentialsButton.setDoubleBuffered(true);
        setCredentialsButton.setName("setCredentialsButton"); // NOI18N
        setCredentialsButton.addActionListener(this::setCredentialsButtonActionPerformed);

        cancelButton.setText("Cancel");
        cancelButton.setDoubleBuffered(true);
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(this::cancelButtonActionPerformed);

        verifiedPasswordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        verifiedPasswordLabel.setText(" ");
        verifiedPasswordLabel.setDoubleBuffered(true);

        usernameField.setDoubleBuffered(true);
        usernameField.setName("usernameField"); // NOI18N
        usernameField.addActionListener(this::setCredentialsButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(usernameLabel)
                        .addGap(86, 86, 86)
                        .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(confirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(confirmPasswordLabel)
                                .addComponent(newPasswordLabel)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(48, 48, 48)
                                    .addComponent(newPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(setCredentialsButton))))
                        .addComponent(verifiedPasswordLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newPasswordLabel))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(confirmPasswordLabel)
                    .addComponent(confirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(verifiedPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setCredentialsButton)
                    .addComponent(cancelButton))
                .addGap(39, 39, 39))
        );

        newPasswordField.getAccessibleContext().setAccessibleName("adminNewPasswordField");
        confirmPasswordField.getAccessibleContext().setAccessibleName("adminVerificationPasswordField");
        setCredentialsButton.getAccessibleContext().setAccessibleName("changePasswordAdminButton");
        cancelButton.getAccessibleContext().setAccessibleName("cancelChangePasswordAdmin");
        verifiedPasswordLabel.getAccessibleContext().setAccessibleName("verifiedPasswordLabel");

        pack();
    }                                    

    private void newPasswordFieldKeyReleased(java.awt.event.KeyEvent evt) {                                          
        if(this.newPasswordField.getPassword().length != 0 && this.confirmPasswordField.getPassword().length != 0) {
            verifyPassword();
        } else {
            this.verifiedPasswordLabel.setText("");
        }
    }  
    
    private void confirmPasswordFieldKeyReleased(java.awt.event.KeyEvent evt) {                                                 
        if(this.newPasswordField.getPassword().length != 0 && this.confirmPasswordField.getPassword().length != 0) {
            verifyPassword();
        } else {
            this.verifiedPasswordLabel.setText("");
        }
    }                                                  

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        this.dispose();
    }
    
    private void setCredentialsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if(areAllFieldsNotBlank()) {
            if(verifyPassword()) administrator.setOperatorCredentials(this);
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
        if(usernameField.getText().isEmpty()) {
            b = false;
            blankFields = "New Username";
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
    private javax.swing.JButton cancelButton;
    protected javax.swing.JPasswordField confirmPasswordField;
    private javax.swing.JLabel confirmPasswordLabel;
    private javax.swing.JLabel newPasswordLabel;
    private javax.swing.JLabel usernameLabel;
    protected javax.swing.JPasswordField newPasswordField;
    private javax.swing.JButton setCredentialsButton;
    protected javax.swing.JTextField usernameField;
    private javax.swing.JLabel verifiedPasswordLabel;
    private com.jogjadamai.infest.administrator.Administrator administrator;
    // End of variables declaration  
}
