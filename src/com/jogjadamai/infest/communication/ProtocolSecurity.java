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
package com.jogjadamai.infest.communication;

/**
 * <h1>class <code>ProtocolSecurity</code></h1>
 * <p><code>ProtocolSecurity</code> is final class with all static methods
 * in order to enhance the communication security between <code>ProtocolServer</code>
 * and <code>ProtocolClient</code>.</p>
 * <br>
 * <p><b><i>Coded, built, and packaged with passion by Danang Galuh Tegar P for Infest.</b></i></p>
 * 
 * @author Danang Galuh Tegar P
 * @version 2017.03.10.0001
 * @see ICPServer
 * @see com.jogjadamai.infest.communication.ProtocolServer
 * @see com.jogjadamai.infest.communication.ProtocolClient
 */
final class ProtocolSecurity {
    
    private static final Integer[] SECURITY_NUMBER = {
        -1874213221, -1062208929, 1159584749
    };
    /**
     * <h2>method <code>isVerfied()</code></h2>
     * <p>Method <code>isVerified</code> is used to verify an authenticity of a 
     * <code>ProtocolClient</code> object based on its type and security number. 
     * If <code>ProtocolClient</code> object's security number match of its type,
     * it will returning <code>true</code>.</p>
     * 
     * @param protocolClient <code>ProtocolClient</code> object to be verified.
     * @return Boolean indicating <code>true</code> when <code>ProtocolClient</code>
     *         is verified.
     */
    protected static Boolean isVerified(com.jogjadamai.infest.communication.IProtocolClient protocolClient) throws java.rmi.RemoteException {
        Boolean isVerified = false;
        switch(protocolClient.getType()) {
            case ADMINISTRATOR:
                isVerified = (java.util.Objects.equals(protocolClient.getSecurityNumber(), SECURITY_NUMBER[0]));
                break;
            case OPERATOR:
                isVerified = (java.util.Objects.equals(protocolClient.getSecurityNumber(), SECURITY_NUMBER[1]));
                break;
            case CUSTOMER:
                isVerified = (java.util.Objects.equals(protocolClient.getSecurityNumber(), SECURITY_NUMBER[2]));
                break;
            default:
                isVerified = false;
                break;
        }
        return isVerified;
    }
    
}
