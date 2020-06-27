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
 * <h1>class <code>Administrator</code></h1>
 * <p><code>Administrator</code> is a controller class containing all the business
 * login of Infest Administrator application.</p>
 * <br>
 * <p><b><i>Coded, built, and packaged with passion by Danang Galuh Tegar P for Infest.</i></b></p>
 * 
 * @author Danang Galuh Tegar P
 * @version 2017.03.10.0001
 */
public final class Administrator {
    
    private static Administrator INSTANCE;
    
    private final com.jogjadamai.infest.administrator.SignInGUI signInFrame;
    private final com.jogjadamai.infest.administrator.MainGUI mainFrame;
    
    private ViewFrame activeFrame;
    private java.util.List<javax.swing.JCheckBox> featuresCheckBox;
    private java.util.List<com.jogjadamai.infest.entity.Features> features;
    private java.rmi.registry.Registry registry;
    private com.jogjadamai.infest.communication.IProtocolClient protocolClient;
    private com.jogjadamai.infest.communication.IProtocolServer protocolServer;
    
    private com.jogjadamai.infest.service.ProgramPropertiesManager programPropertiesManager;
    
    private enum ViewFrame {
        SIGN_IN, MAIN
    }
    
    private Administrator(com.jogjadamai.infest.administrator.SignInGUI signInFrame, com.jogjadamai.infest.administrator.MainGUI mainFrame) {
        this.signInFrame = signInFrame;
        this.mainFrame = mainFrame;
        this.activeFrame = ViewFrame.SIGN_IN;
    }
    
    protected static Administrator getInstance() {
        return INSTANCE;
    }
    
    protected static Administrator createInstance(com.jogjadamai.infest.administrator.SignInGUI signInFrame, com.jogjadamai.infest.administrator.MainGUI mainFrame) {
        if(INSTANCE == null) INSTANCE = new Administrator(signInFrame, mainFrame);
        return INSTANCE;
    }
    
    protected void onFirstRun() {
        java.io.File configFile = new java.io.File("infest.conf");
        java.io.File aCredFile = new java.io.File(com.jogjadamai.infest.communication.IProtocolClient.Type.ADMINISTRATOR.name() + ".CRD");
        java.io.File oCredFile = new java.io.File(com.jogjadamai.infest.communication.IProtocolClient.Type.OPERATOR.name() + ".CRD");
        java.io.File cCredFile = new java.io.File(com.jogjadamai.infest.communication.IProtocolClient.Type.CUSTOMER.name() + ".CRD");
        if (!configFile.exists() && !aCredFile.exists() && !oCredFile.exists() && !cCredFile.exists()) {
            javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame,
                    "Hi, welcome to Infest Program!\n"
                            + "\n"
                            + "We believe that it is your first time running this application.\n"
                            + "Before proceeding, please fill something for us :)", 
                    "INFEST: Program Configuration Manager", 
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            new com.jogjadamai.infest.administrator.FirstTimeConfiguration().setVisible(true);
        } else {
            programPropertiesManager = com.jogjadamai.infest.service.ProgramPropertiesManager.getInstance();
            initialiseConnection();
            signInFrame.setVisible(true);
        }
    }
    
    private void initialiseConnection() {
        String serverAddress = null;
        try {
            serverAddress = programPropertiesManager.getProperty("serveraddress");
        } catch (java.lang.NullPointerException ex) {
            System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
            javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, "Infest Configuration File is miss-configured!\n\n"
                    + "Please verify that the Infest Configuration File (infest.conf) is exist in the current\n"
                    + "working directory and is properly configured. Any wrong setting or modification of\n"
                    + "Infest Configuration File would cause this error.", "INFEST: Program Configuration Manager", javax.swing.JOptionPane.ERROR_MESSAGE);
            fatalExit(-1);
        }
        try {
            this.registry = java.rmi.registry.LocateRegistry.getRegistry(serverAddress, 42700);
            this.protocolClient = new com.jogjadamai.infest.communication.AdministratorClient();
            this.protocolServer = (com.jogjadamai.infest.communication.IProtocolServer) this.registry.lookup("InfestAPIServer");
            this.protocolServer.authenticate(this.protocolClient);
        } catch (java.rmi.NotBoundException | java.rmi.RemoteException ex) {
            System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
            javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, 
                    "There's is an error with Infest API Server! Please contact Infest Developer Team.\n\n"
                            + "Program error detected.", 
                    "INFEST: Remote Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            fatalExit(-1);
        }
    }
    
    private void fatalExit(int code) {
        System.err.println("[INFEST] " +  getNowTime() + ": System exited with code " + code + ".");
        javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame,
                "Fatal error occured! Please contact an Infest Adminisrator.\n\n"
                + "CODE [" + code + "]\n"
                + "Infest Program is now exiting.", "INFEST: System Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        System.exit(code);
    }

    private String getNowTime() {
        return java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(java.time.LocalDateTime.now());
    }
    
    protected String getCurrentAdministratorUsername(java.awt.Component parent) {
        com.jogjadamai.infest.security.Credentials savedCred = null;
        try {
            savedCred = this.protocolServer.getCredentials(protocolClient);
            if(savedCred == null) {
                savedCred = com.jogjadamai.infest.security.CredentialsManager.createCredentials("", "");
                System.err.println("[INFEST] " +  getNowTime() + ": " + "java.lang.NullPointerException");
                javax.swing.JOptionPane.showMessageDialog(parent,
                        "Infest Configuration File is miss-configured!\n\n"
                                + "Please verify that the Infest Configuration File (infest.conf) is exist in the current\n"
                                + "working directory and is properly configured. Any wrong setting or modification of\n"
                                + "Infest Configuration File would cause this error.",
                        "INFEST: Program Configuration Manager", javax.swing.JOptionPane.ERROR_MESSAGE);
                fatalExit(-1);
            }
            return savedCred.getUsername();
        } catch (java.rmi.RemoteException ex) {
            savedCred = com.jogjadamai.infest.security.CredentialsManager.createCredentials("", "");
            System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
            javax.swing.JOptionPane.showMessageDialog(parent, 
                    "There's is an error with Infest API Server! Please contact Infest Developer Team.\n\n"
                            + "Program error detected.", 
                    "INFEST: Remote Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            fatalExit(-1);
            return null;
        }
    }
    
    private Boolean isCredentialsCurrent(java.awt.Component parent, com.jogjadamai.infest.security.Credentials credentials) {
        com.jogjadamai.infest.security.Credentials savedCred = null;
        try {    
            savedCred = this.protocolServer.getCredentials(protocolClient);
            if(savedCred == null) {
                savedCred = com.jogjadamai.infest.security.CredentialsManager.createCredentials("", "");
                System.err.println("[INFEST] " +  getNowTime() + ": " + "java.lang.NullPointerException");
                javax.swing.JOptionPane.showMessageDialog(parent, 
                        "Infest Configuration File is miss-configured!\n\n"
                        + "Please verify that the Infest Configuration File (infest.conf) is exist in the current\n"
                        + "working directory and is properly configured. Any wrong setting or modification of\n"
                        + "Infest Configuration File would cause this error.", 
                        "INFEST: Program Configuration Manager", javax.swing.JOptionPane.ERROR_MESSAGE);
                fatalExit(-1);
            }
            try {
                String salt = getSalt();
                try {
                    com.jogjadamai.infest.security.CredentialsManager.encryptCredentials(credentials, salt);
                    return savedCred.equals(credentials);
                } catch (Exception ex) {
                    System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
                    javax.swing.JOptionPane.showMessageDialog(parent,
                            "Failed to encrypt credentials!\n\n"
                            + "Please contact an Infest Administrator for further help.", 
                            "INFEST: Encryption Service", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (NullPointerException ex) {
                System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
                javax.swing.JOptionPane.showMessageDialog(parent, 
                        "Infest Configuration File is miss-configured!\n\n"
                        + "Please verify that the Infest Configuration File (infest.conf) is exist in the current\n"
                        + "working directory and is properly configured. Any wrong setting or modification of\n"
                        + "Infest Configuration File would cause this error.", 
                        "INFEST: Program Configuration Manager", javax.swing.JOptionPane.ERROR_MESSAGE);
                fatalExit(-1);
                return false;
            }
        } catch (java.rmi.RemoteException ex) {
            savedCred = com.jogjadamai.infest.security.CredentialsManager.createCredentials("", "");
            System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
            javax.swing.JOptionPane.showMessageDialog(parent, 
                    "There's is an error with Infest API Server! Please contact Infest Developer Team.\n\n"
                            + "Program error detected.", 
                    "INFEST: Remote Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            fatalExit(-1);
            return false;
        }
    }
    
    protected void signIn() {
        if(isCredentialsCurrent((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, com.jogjadamai.infest.security.CredentialsManager.createCredentials(signInFrame.usernameField.getText(), signInFrame.passwordField.getPassword()))) {
            signInFrame.setVisible(false);
            mainFrame.setVisible(true);
            activeFrame = ViewFrame.MAIN;
        } else {
            javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, 
                    "Authentication Failed!\n\n"
                        + "Either username or password is wrong, or your\n"
                        + "Infest Configuration File is miss-configured.",
                    "INFEST: Authentication System", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    protected void signOut() {
        try {
            if(com.jogjadamai.infest.communication.ProtocolServer.getInstance().isServerActive())
                toggleServer();
        } catch (java.rmi.RemoteException ex) {
            System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
            javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, 
                    "There's is an error with Infest API Server! Please contact Infest Developer Team.\n\n"
                            + "Program error detected.", 
                    "INFEST: Remote Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            fatalExit(-1);
        }
        mainFrame.setVisible(false);
        signInFrame.usernameField.requestFocusInWindow();
        signInFrame.passwordField.setText("");
        signInFrame.setVisible(true);
        activeFrame = ViewFrame.SIGN_IN;
    }
    
    protected void shutdown(int code) {
        System.out.println("[INFEST] " +  getNowTime() + ": System exited with code " + code + ".");
        signInFrame.setVisible(false);
        mainFrame.setVisible(false);
        System.exit(code);
    }
    
    protected Boolean readAllFeatures() {
        Boolean isSuccess = true;
        try {
            this.features = this.protocolServer.readAllFeature(protocolClient);
        } catch (java.rmi.RemoteException ex) {
            System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
            javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, 
                    "There's is an error with Infest API Server! Please contact Infest Developer Team.\n\n"
                            + "Program error detected.", 
                    "INFEST: Remote Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            fatalExit(-1);
        }
        this.featuresCheckBox = new java.util.ArrayList<>();
        this.featuresCheckBox.add(mainFrame.maintenanceModeCheckBox);
        this.featuresCheckBox.add(mainFrame.showCurrencyCheckBox);
        this.featuresCheckBox.add(mainFrame.operatorGenerateReportCheckBox);
        this.featuresCheckBox.add(mainFrame.customerPrintBillCheckBox);
        this.featuresCheckBox.add(mainFrame.customerShowMenuDurationCheckBox);
        this.featuresCheckBox.add(mainFrame.customerShowMenuImageCheckBox);
        features.forEach((feature) -> {
            this.featuresCheckBox.get(feature.getId()-1).setSelected((feature.getStatus() == 1));
            if(feature.getName().equals("CURRENCY")) mainFrame.currencyTextField.setText(feature.getDescription());
        });
        return isSuccess;
    }
    
    protected Boolean writeAllFeatures() {
        Boolean isSuccess = true;
        try {
            this.features = this.protocolServer.readAllFeature(protocolClient);
        } catch (java.rmi.RemoteException ex) {
            System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
            javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, 
                    "There's is an error with Infest API Server! Please contact Infest Developer Team.\n\n"
                            + "Program error detected.", 
                    "INFEST: Remote Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            fatalExit(-1);
        }
        this.featuresCheckBox.add(mainFrame.maintenanceModeCheckBox);
        this.featuresCheckBox.add(mainFrame.showCurrencyCheckBox);
        this.featuresCheckBox.add(mainFrame.operatorGenerateReportCheckBox);
        this.featuresCheckBox.add(mainFrame.customerPrintBillCheckBox);
        this.featuresCheckBox.add(mainFrame.customerShowMenuDurationCheckBox);
        this.featuresCheckBox.add(mainFrame.customerShowMenuImageCheckBox);
        features.forEach((feature) -> {
            if(featuresCheckBox.get(feature.getId()-1).isSelected()) feature.setStatus(1);
            else feature.setStatus(0);
            if(feature.getName().equals("CURRENCY")) feature.setDescription(mainFrame.currencyTextField.getText());
            try {
                this.protocolServer.updateFeature(protocolClient, feature);
            } catch (java.rmi.RemoteException ex) {
                System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
                javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, 
                        "There's is an error with Infest API Server! Please contact Infest Developer Team.\n\n"
                                + "Program error detected.", 
                        "INFEST: Remote Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                fatalExit(-1);
            }
        });
        isSuccess = true;
        return isSuccess;
    }
    
    protected void refreshServerStatus() {
        try {
            if(com.jogjadamai.infest.communication.ProtocolServer.getInstance().isServerActive()) {
                mainFrame.statusLabel.setText("Started & Listening");
                mainFrame.statusLabel.setForeground(java.awt.Color.BLUE);
                mainFrame.serverToggleButton.setText("STOP SERVER");
                mainFrame.serverToggleButton.setForeground(java.awt.Color.RED);
                this.repaintPane(true);
                if (!this.readAllFeatures()) javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, "Failed to read Features Configuration!", "Read Configuration", javax.swing.JOptionPane.ERROR_MESSAGE);
            } else {
                mainFrame.statusLabel.setText("Idle");
                mainFrame.statusLabel.setForeground(java.awt.Color.RED);
                mainFrame.serverToggleButton.setText("START SERVER");
                mainFrame.serverToggleButton.setForeground(java.awt.Color.BLUE);
                this.repaintPane(false);
            }
        } catch (java.rmi.RemoteException ex) {
            System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
            javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, 
                    "There's is an error with Infest API Server! Please contact Infest Developer Team.\n\n"
                            + "Program error detected.", 
                    "INFEST: Remote Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            fatalExit(-1);
        }
    }
    
    protected void repaintPane(Boolean isEnabled) {
        if(!isEnabled) {
            mainFrame.maintenanceModeCheckBox.setSelected(isEnabled);
            mainFrame.showCurrencyCheckBox.setSelected(isEnabled);
            mainFrame.currencyTextField.setText("");
            mainFrame.operatorGenerateReportCheckBox.setSelected(isEnabled);
            mainFrame.customerPrintBillCheckBox.setSelected(isEnabled);
            mainFrame.customerShowMenuDurationCheckBox.setSelected(isEnabled);
            mainFrame.customerShowMenuImageCheckBox.setSelected(isEnabled);
        }
        mainFrame.maintenanceModeCheckBox.setEnabled(isEnabled);
        mainFrame.showCurrencyCheckBox.setEnabled(isEnabled);
        mainFrame.currencyTextField.setEnabled(isEnabled);
        mainFrame.operatorGenerateReportCheckBox.setEnabled(isEnabled);
        mainFrame.customerPrintBillCheckBox.setEnabled(isEnabled);
        mainFrame.customerShowMenuDurationCheckBox.setEnabled(isEnabled);
        mainFrame.customerShowMenuImageCheckBox.setEnabled(isEnabled);
        mainFrame.saveFeaturesConfiguration.setEnabled(isEnabled);
        mainFrame.featurePanel.setEnabled(isEnabled);
    }
        
    protected void toggleServer() {
        try {
            if(!com.jogjadamai.infest.communication.ProtocolServer.getInstance().isServerActive()) com.jogjadamai.infest.communication.ProtocolServer.getInstance().start();
            else com.jogjadamai.infest.communication.ProtocolServer.getInstance().stop();
        } catch (java.rmi.RemoteException ex) {
            System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
            javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, 
                    "There's is an error with Infest API Server! Please contact Infest Developer Team.\n\n"
                            + "Program error detected.", 
                    "INFEST: Remote Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            fatalExit(-1);
        }
        this.refreshServerStatus();
    }
    
    protected void openDocumentation() {
        java.io.File docFile = new java.io.File("Infest-Documentation.pdf");
        try {
            java.awt.Desktop.getDesktop().open(docFile);
        } catch (java.io.IOException ex) {
            System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
        }
    }
    
    protected void changePassword() {
        new ChangePasswordDialog().run();
    }
    
    protected void changePassword(com.jogjadamai.infest.administrator.ChangePasswordDialog changePasswordDialog) {
        if(isCredentialsCurrent(changePasswordDialog, com.jogjadamai.infest.security.CredentialsManager.createCredentials(getCurrentAdministratorUsername((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame), changePasswordDialog.currentPasswordField.getPassword()))) {
            try {
                String salt = getSalt();
                try {
                    com.jogjadamai.infest.security.Credentials newCredentials = com.jogjadamai.infest.security.CredentialsManager.createEncryptedCredentials(getCurrentAdministratorUsername((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame), changePasswordDialog.newPasswordField.getPassword(), salt);
                    java.io.File credFile = new java.io.File(com.jogjadamai.infest.communication.IProtocolClient.Type.ADMINISTRATOR.name().toLowerCase() + ".crd");
                    credFile.createNewFile();
                    java.io.FileOutputStream fos = new java.io.FileOutputStream(credFile, false);
                    try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos)) {
                        oos.writeObject(newCredentials);
                        javax.swing.JOptionPane.showMessageDialog(changePasswordDialog,
                                "New Administrator Credentials has been saved!",
                                "INFEST: Credentials Manager", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        changePasswordDialog.setVisible(false);
                    } catch (java.io.IOException ex) {
                        System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
                        javax.swing.JOptionPane.showMessageDialog(changePasswordDialog,
                                "Failed to set new credentials!\n\n"
                                        + "The program is unable to create new credentials file.\n"
                                        + "The file probably is under used by another proccess.",
                                "INFEST: Credentials Manager", javax.swing.JOptionPane.ERROR_MESSAGE);
                    } finally {
                        try {
                            fos.close();
                        } catch (java.io.IOException ex) {
                            System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
                        }
                    }
                } catch (Exception ex) {
                    System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
                    javax.swing.JOptionPane.showMessageDialog(changePasswordDialog,
                            "Failed to encrypt credentials!\n\n"
                                    + "Please contact Infest Developer Team for further help.",
                            "INFEST: Encryption System", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            } catch (NullPointerException ex) {
                System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
                javax.swing.JOptionPane.showMessageDialog(changePasswordDialog, 
                        "Infest Configuration File is miss-configured!\n\n"
                        + "Please verify that the Infest Configuration File (infest.conf) is exist in the current\n"
                        + "working directory and is properly configured. Any wrong setting or modification of\n"
                        + "Infest Configuration File would cause this error.", 
                        "INFEST: Program Configuration Manager", javax.swing.JOptionPane.ERROR_MESSAGE);
                fatalExit(-1);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(changePasswordDialog, 
                    "Authentication Failed!\n\n"
                        + "Either current password is wrong, or your\n"
                        + "Infest Configuration File is miss-configured.",
                    "INFEST: Authentication System", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    protected void setOperatorCredentials() {
        new OperatorCredentialsDialog().run();
    }
    
    protected void setOperatorCredentials(com.jogjadamai.infest.administrator.OperatorCredentialsDialog operatorCredentialsDialog) {
        if(createCredentials(com.jogjadamai.infest.communication.IProtocolClient.Type.OPERATOR,
                operatorCredentialsDialog.usernameField.getText(), 
                operatorCredentialsDialog.newPasswordField.getPassword())) {
            javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, 
                    "New Operator Credentials has been successfully set!",
                    "INFEST: Credentials Manager", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            operatorCredentialsDialog.setVisible(false);
        } else javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame,
                "Failed to set new Operator Credentials!",
                "INFEST: Credentials Manager", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    protected void resetOperatorCredentials() {
        if(javax.swing.JOptionPane.showConfirmDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame,
                "Are you sure to reset Operator credentials?", 
                "INFEST: Credentials Manager", 
                javax.swing.JOptionPane.YES_NO_OPTION, 
                javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION){
            if(createOperatorCredentials()) javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, 
                    "Operator Credentials has been successfully reset!",
                    "INFEST: Credentials Manager", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            else javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame, 
                    "Failed to reset Operator Credentials!",
                    "INFEST: Credentials Manager", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String getSalt() throws NullPointerException {
        programPropertiesManager = com.jogjadamai.infest.service.ProgramPropertiesManager.getInstance();
        String salt;
        try {
            salt = programPropertiesManager.getProperty("salt");
            if(salt.isEmpty()) throw new NullPointerException();
        } catch(NullPointerException ex) {
            System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
            salt = "";
            throw ex;
        }
        return salt;
    }
    
    protected Boolean createOperatorCredentials() {
        return createCredentials(com.jogjadamai.infest.communication.IProtocolClient.Type.OPERATOR, null, null);
    }
    
    protected Boolean createCredentials(com.jogjadamai.infest.communication.IProtocolClient.Type clientType, String username, char[] password) {
        Boolean isSuccess = false;
        try {
            String salt = getSalt();
            com.jogjadamai.infest.security.Credentials credentials;
            try {
                if(username == null || password == null) credentials = com.jogjadamai.infest.security.CredentialsManager.createDefaultEncryptedCredentials(clientType, salt);
                else credentials = com.jogjadamai.infest.security.CredentialsManager.createEncryptedCredentials(username, password, salt);
                java.io.File credFile = new java.io.File(clientType.name().toLowerCase() + ".crd");
                credFile.createNewFile();
                java.io.FileOutputStream fos = new java.io.FileOutputStream(credFile, false);
                try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos)) {
                    oos.writeObject(credentials);
                    isSuccess = true;
                } catch (java.io.IOException ex) {
                    System.err.println("[INFEST] " + ex);
                } finally {
                    try {
                        fos.close();
                    } catch (java.io.IOException ex) {
                        System.err.println("[INFEST] " + ex);
                    }
                }
            } catch (Exception ex) {
                System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
                javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame,
                        "Failed to encrypt credentials.",
                        "INFEST: Encryption System", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } catch (NullPointerException ex) {
            System.err.println("[INFEST] " +  getNowTime() + ": " + ex);
            javax.swing.JOptionPane.showMessageDialog((activeFrame == ViewFrame.MAIN) ? mainFrame : signInFrame,
                    "Infest Configuration File is miss-configured!\n\n"
                            + "Please verify that the Infest Configuration File (infest.conf) is exist in the current\n"
                            + "working directory and is properly configured. Any wrong setting or modification of\n"
                            + "Infest Configuration File would cause this error.", 
                    "INFEST: Program Configuration Manager", javax.swing.JOptionPane.ERROR_MESSAGE);
            fatalExit(-1);
        }
        return isSuccess;
    }
    
}
