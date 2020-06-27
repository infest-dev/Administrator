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
 * <h1>class <code>MainGUI</code></h1>
 * <p><code>MainGUI</code> is <code>javax.swing.JFrame</code> class defining
 * the Main Graphical User Interface of the application.</p>
 * <br>
 * <p><b><i>Coded, built, and packaged with passion by Danang Galuh Tegar P for Infest.</i></b></p>
 * 
 * @author Danang Galuh Tegar P
 * @version 2017.03.10.0001
 */
public final class MainGUI extends javax.swing.JFrame implements Runnable {
    
    public MainGUI() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initialiseComponents();
    }
    
    private void initialiseComponents() {
        
        featurePanel = new javax.swing.JPanel();
        showCurrencyCheckBox = new javax.swing.JCheckBox();
        maintenanceModeCheckBox = new javax.swing.JCheckBox();
        operatorGenerateReportCheckBox = new javax.swing.JCheckBox();
        customerPrintBillCheckBox = new javax.swing.JCheckBox();
        customerShowMenuDurationCheckBox = new javax.swing.JCheckBox();
        customerShowMenuImageCheckBox = new javax.swing.JCheckBox();
        saveFeaturesConfiguration = new javax.swing.JButton();
        currencyTextField = new javax.swing.JTextField();
        serverToggleButton = new javax.swing.JToggleButton();
        logoPanel = new javax.swing.JPanel();
        infestLogoLabel = new javax.swing.JLabel();
        serverStatusLabel = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        programMenu = new javax.swing.JMenu();
        signOutMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        credentialsManagerMenu = new javax.swing.JMenu();
        changePasswordMenuItem = new javax.swing.JMenuItem();
        credentialsManagerMenuSeparator = new javax.swing.JPopupMenu.Separator();
        setCustomOperatorCredentialMenuItem = new javax.swing.JMenuItem();
        resetOperatorCredential = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        seeDocumentationMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("INFEST: Administrator Panel");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(655, 500));
        setMinimumSize(new java.awt.Dimension(655, 500));
        setName("mainGUI"); // NOI18N
        setResizable(false);
        setSize(new java.awt.Dimension(655, 500));
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/com/jogjadamai/infest/assets/InfestIcon.png")).getImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        featurePanel.setBackground(new java.awt.Color(0xffffff));
        featurePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Features Configuration", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12))); // NOI18N
        featurePanel.setToolTipText("Features configuration panel.");
        featurePanel.setMaximumSize(new java.awt.Dimension(625, 400));
        featurePanel.setMinimumSize(new java.awt.Dimension(625, 400));

        showCurrencyCheckBox.setBackground(new java.awt.Color(0xffffff));
        showCurrencyCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        showCurrencyCheckBox.setText("Show Currency");
        showCurrencyCheckBox.setToolTipText("Show/unshow currency in OPERATOR and CUSTOMER. The currency will be shown AFTER the nomimal.");
        showCurrencyCheckBox.setDoubleBuffered(true);
        showCurrencyCheckBox.addChangeListener(this::showCurrencyCheckBoxStateChanged);

        maintenanceModeCheckBox.setBackground(new java.awt.Color(0xffffff));
        maintenanceModeCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        maintenanceModeCheckBox.setText("Maintenance Mode");
        maintenanceModeCheckBox.setToolTipText("Turn on/off maintenenance mode (if active, CUSTOMER and OPERATOR will be at offline mode).");
        maintenanceModeCheckBox.setDoubleBuffered(true);

        operatorGenerateReportCheckBox.setBackground(new java.awt.Color(0xffffff));
        operatorGenerateReportCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        operatorGenerateReportCheckBox.setText("OPERATOR: Generate Report");
        operatorGenerateReportCheckBox.setToolTipText("Turn on/off report generator feature in OPERATOR.");
        operatorGenerateReportCheckBox.setDoubleBuffered(true);

        customerPrintBillCheckBox.setBackground(new java.awt.Color(0xffffff));
        customerPrintBillCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        customerPrintBillCheckBox.setText("CUSTOMER: Print Bill");
        customerPrintBillCheckBox.setToolTipText("Turn on/off print bill feature in CUSTOMER.");
        customerPrintBillCheckBox.setDoubleBuffered(true);

        customerShowMenuDurationCheckBox.setBackground(new java.awt.Color(0xffffff));
        customerShowMenuDurationCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        customerShowMenuDurationCheckBox.setText("CUSTOMER: Show Menu Duration");
        customerShowMenuDurationCheckBox.setToolTipText("Show/unshow menu duration in CUSTOMER.");
        customerShowMenuDurationCheckBox.setDoubleBuffered(true);

        customerShowMenuImageCheckBox.setBackground(new java.awt.Color(0xffffff));
        customerShowMenuImageCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        customerShowMenuImageCheckBox.setText("COSTUMER: Show Menu Image");
        customerShowMenuImageCheckBox.setToolTipText("Show/unshow menu image in CUSTOMER.");
        customerShowMenuImageCheckBox.setDoubleBuffered(true);

        saveFeaturesConfiguration.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        saveFeaturesConfiguration.setText("SAVE");
        saveFeaturesConfiguration.setToolTipText("Save features configuration.");
        saveFeaturesConfiguration.setDoubleBuffered(true);
        saveFeaturesConfiguration.setPreferredSize(new java.awt.Dimension(100, 40));
        saveFeaturesConfiguration.addActionListener(this::saveFeaturesConfigurationActionPerformed);

        currencyTextField.setBackground(new java.awt.Color(0xffffff));
        currencyTextField.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        currencyTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        currencyTextField.setText("CUR");
        currencyTextField.setToolTipText("Type currency to show. The currency will be shown AFTER the nomimal.");
        currencyTextField.setDoubleBuffered(true);

        javax.swing.GroupLayout featurePanelLayout = new javax.swing.GroupLayout(featurePanel);
        featurePanel.setLayout(featurePanelLayout);
        featurePanelLayout.setHorizontalGroup(
            featurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(featurePanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(featurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(maintenanceModeCheckBox)
                    .addGroup(featurePanelLayout.createSequentialGroup()
                        .addComponent(showCurrencyCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(currencyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(operatorGenerateReportCheckBox)
                    .addComponent(customerPrintBillCheckBox)
                    .addComponent(customerShowMenuDurationCheckBox)
                    .addComponent(customerShowMenuImageCheckBox))
                .addGap(381, 381, 381))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, featurePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveFeaturesConfiguration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        featurePanelLayout.setVerticalGroup(
            featurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(featurePanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(maintenanceModeCheckBox)
                .addGap(18, 18, 18)
                .addGroup(featurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showCurrencyCheckBox)
                    .addComponent(currencyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(operatorGenerateReportCheckBox)
                .addGap(18, 18, 18)
                .addComponent(customerPrintBillCheckBox)
                .addGap(18, 18, 18)
                .addComponent(customerShowMenuDurationCheckBox)
                .addGap(18, 18, 18)
                .addComponent(customerShowMenuImageCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(saveFeaturesConfiguration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        serverToggleButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        serverToggleButton.setToolTipText("Start/stop Infest API Server.");
        serverToggleButton.setDoubleBuffered(true);
        serverToggleButton.setPreferredSize(new java.awt.Dimension(150, 50));
        serverToggleButton.addActionListener(this::serverToggleButtonActionPerformed);

        logoPanel.setBackground(new java.awt.Color(0xffffff));
        logoPanel.setToolTipText("Infest: Sistem Informasi Restoran Hotel Jogja Damai");
        logoPanel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        logoPanel.setPreferredSize(new java.awt.Dimension(120, 60));
        logoPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        infestLogoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jogjadamai/infest/assets/MainGUILogo.png"))); // NOI18N
        infestLogoLabel.setDoubleBuffered(true);
        logoPanel.add(infestLogoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        serverStatusLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        serverStatusLabel.setText("Server Status:");
        serverStatusLabel.setToolTipText("");

        statusLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusLabel.setText("<STATUS>");
        statusLabel.setToolTipText("Current Infest API Server status.");

        programMenu.setText("Program");
        programMenu.setToolTipText("Ask the program...");
        programMenu.setName("programMenu"); // NOI18N

        signOutMenuItem.setText("Sign Out");
        signOutMenuItem.setToolTipText("Sign out from the application.");
        signOutMenuItem.setDoubleBuffered(true);
        signOutMenuItem.setName("signOutMenuItem"); // NOI18N
        signOutMenuItem.addActionListener(this::signOutMenuItemActionPerformed);
        programMenu.add(signOutMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.setToolTipText("Good bye!");
        exitMenuItem.setDoubleBuffered(true);
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(this::exitMenuItemActionPerformed);
        programMenu.add(exitMenuItem);

        menuBar.add(programMenu);

        credentialsManagerMenu.setText("Credentials Manager");
        credentialsManagerMenu.setToolTipText("Manage credentials.");
        credentialsManagerMenu.setName("credentialsManagerMenu"); // NOI18N

        changePasswordMenuItem.setText("Change Administrator Password");
        changePasswordMenuItem.setToolTipText("Change Administrator Password");
        changePasswordMenuItem.setName("changePasswordMenuItem"); // NOI18N
        changePasswordMenuItem.addActionListener(this::changePasswordMenuItemActionPerformed);
        credentialsManagerMenu.add(changePasswordMenuItem);

        credentialsManagerMenuSeparator.setDoubleBuffered(true);
        credentialsManagerMenuSeparator.setName("credentialsManagerMenuSeparator"); // NOI18N
        credentialsManagerMenu.add(credentialsManagerMenuSeparator);

        setCustomOperatorCredentialMenuItem.setText("Set Custom Operator Credential");
        setCustomOperatorCredentialMenuItem.setToolTipText("Set custom Operator credential.");
        setCustomOperatorCredentialMenuItem.setName("setCustomOperatorCredentialMenuItem"); // NOI18N
        setCustomOperatorCredentialMenuItem.addActionListener(this::setCustomOperatorCredentialMenuItemActionPerformed);
        credentialsManagerMenu.add(setCustomOperatorCredentialMenuItem);

        resetOperatorCredential.setText("Reset Operator Credential");
        resetOperatorCredential.setToolTipText("Reset Operator credential.");
        resetOperatorCredential.setName("resetOperatorCredential"); // NOI18N
        resetOperatorCredential.addActionListener(this::resetOperatorCredentialMenuItemActionPerformed);
        credentialsManagerMenu.add(resetOperatorCredential);

        menuBar.add(credentialsManagerMenu);

        helpMenu.setText("Help");
        helpMenu.setToolTipText("Do you need assistance?");
        helpMenu.setName("helpMenu"); // NOI18N

        seeDocumentationMenuItem.setText("See Documentation");
        seeDocumentationMenuItem.setToolTipText("Open the documentation file of the program.");
        seeDocumentationMenuItem.setDoubleBuffered(true);
        seeDocumentationMenuItem.setName("seeDocumentationMenuItem"); // NOI18N
        seeDocumentationMenuItem.addActionListener(this::seeDocumentationMenuItemActionPerformed);
        helpMenu.add(seeDocumentationMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(serverToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(serverStatusLabel)
                            .addComponent(statusLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(featurePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(serverStatusLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(serverToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(statusLabel))
                    .addComponent(logoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(featurePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        pack();
        
    }
    
    private void saveFeaturesConfigurationActionPerformed(java.awt.event.ActionEvent evt) { 
        if (administrator.writeAllFeatures()) javax.swing.JOptionPane.showMessageDialog(this, "Features Configuration saved successfully!", "Save Configuration", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        else javax.swing.JOptionPane.showMessageDialog(this, "Features Configuration failed to be save!", "Save Configuration", javax.swing.JOptionPane.ERROR_MESSAGE);
        if (!administrator.readAllFeatures()) javax.swing.JOptionPane.showMessageDialog(this, "Failed to read Features Configuration!", "Read Configuration", javax.swing.JOptionPane.ERROR_MESSAGE);
    }                                                         

    private void serverToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {
        administrator.toggleServer();
    }   
    
    private void showCurrencyCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {
        if(this.showCurrencyCheckBox.isSelected()) {
            this.currencyTextField.setEnabled(true);
        } else {
            this.currencyTextField.setText("");
            this.currencyTextField.setEnabled(false);
        }
    }
    
    private void formWindowClosed(java.awt.event.WindowEvent evt) {                                  
        administrator.signOut();
    } 
    
    private void signOutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                
        administrator.signOut();
    }                                               

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        administrator.shutdown(0);
    }                                            

    private void seeDocumentationMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                         
        administrator.openDocumentation();
    }  
    
    private void changePasswordMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                         
        administrator.changePassword();
    }  
    
    private void setCustomOperatorCredentialMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                         
        administrator.setOperatorCredentials();
    }  
    
    private void resetOperatorCredentialMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                                         
        administrator.resetOperatorCredentials();
    }   
    
    // Variables declaration - do not modify                     
    protected javax.swing.JMenuItem changePasswordMenuItem;
    protected javax.swing.JMenu credentialsManagerMenu;
    protected javax.swing.JPopupMenu.Separator credentialsManagerMenuSeparator;
    protected javax.swing.JTextField currencyTextField;
    protected javax.swing.JCheckBox customerPrintBillCheckBox;
    protected javax.swing.JCheckBox customerShowMenuDurationCheckBox;
    protected javax.swing.JCheckBox customerShowMenuImageCheckBox;
    protected javax.swing.JMenuItem exitMenuItem;
    protected javax.swing.JPanel featurePanel;
    protected javax.swing.JMenu helpMenu;
    protected javax.swing.JLabel infestLogoLabel;
    protected javax.swing.JPanel logoPanel;
    protected javax.swing.JCheckBox maintenanceModeCheckBox;
    protected javax.swing.JMenuBar menuBar;
    protected javax.swing.JCheckBox operatorGenerateReportCheckBox;
    protected javax.swing.JMenu programMenu;
    protected javax.swing.JMenuItem resetOperatorCredential;
    protected javax.swing.JButton saveFeaturesConfiguration;
    protected javax.swing.JMenuItem seeDocumentationMenuItem;
    protected javax.swing.JLabel serverStatusLabel;
    protected javax.swing.JToggleButton serverToggleButton;
    protected javax.swing.JMenuItem setCustomOperatorCredentialMenuItem;
    protected javax.swing.JCheckBox showCurrencyCheckBox;
    protected javax.swing.JMenuItem signOutMenuItem;
    protected javax.swing.JLabel statusLabel;
    protected com.jogjadamai.infest.administrator.Administrator administrator;
    // End of variables declaration  
    
    @Override
    public void run() {
        administrator = com.jogjadamai.infest.administrator.Administrator.getInstance();
        administrator.refreshServerStatus();
        getContentPane().setBackground(new java.awt.Color(0xffffff));
        setLocationRelativeTo(null);
    }

}