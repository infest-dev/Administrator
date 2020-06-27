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
 * <h1>class <code>Program</code></h1>
 * <p><code>Program</code> is class defining <code>main()</code> to run the
 * application.</p>
 * <br>
 * <p><b><i>Coded, built, and packaged with passion by Danang Galuh Tegar P for Infest.</i></b></p>
 * 
 * @author Danang Galuh Tegar P
 * @version 2017.03.10.0001
 */
public final class Program {
    
    private static SignInGUI SignInGUI;
    private static MainGUI MainGUI;
    private static Thread SignInThread, MainThread;
    private static Administrator Controller;
    private static com.jogjadamai.infest.communication.IProtocolServer Server;
    
    public static void main(String[] args) throws java.rmi.RemoteException {
        Program.SignInGUI = new SignInGUI();
        Program.MainGUI = new MainGUI();
        try {
            Program.Server = com.jogjadamai.infest.communication.ProtocolServer.getInstance();
        } catch(java.rmi.server.ExportException ex) {
            javax.swing.JOptionPane.showMessageDialog(SignInGUI, 
                    "Program already running! Only one instance allowed at a time.", 
                    "INFEST: Administrator", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        Program.Controller = Administrator.createInstance(Program.SignInGUI, Program.MainGUI);
        Program.Controller.onFirstRun();
        Program.SignInThread = new Thread(Program.SignInGUI);
        Program.MainThread = new Thread(Program.MainGUI);
        Program.SignInThread.setName("SignInThread");
        Program.MainThread.setName("MainThread");
        java.awt.EventQueue.invokeLater(Program.SignInThread);
        java.awt.EventQueue.invokeLater(Program.MainThread);
    }
    
}
