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

import com.jogjadamai.infest.entity.Carts;
import com.jogjadamai.infest.entity.Features;
import com.jogjadamai.infest.entity.FinanceReport;
import com.jogjadamai.infest.entity.Menus;
import com.jogjadamai.infest.entity.Orders;
import com.jogjadamai.infest.entity.Tables;
import com.jogjadamai.infest.persistence.InfestEntityController;
import com.jogjadamai.infest.persistence.InfestPersistence;
import com.jogjadamai.infest.persistence.exceptions.NonexistentEntityException;
import com.jogjadamai.infest.security.Credentials;
import com.jogjadamai.infest.security.CredentialsManager;
import com.jogjadamai.infest.service.ProgramPropertiesManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>class <code>ProtocolServer</code></h1>
 * <p><code>ProtocolServer</code> is class implementing <code>Protocol</code>
 * interface (as <code>java.rmi.Remote</code> interface) and acting as the Remote
 * object.</p>
 * <br>
 * <p><b><i>Coded, built, and packaged with passion by Danang Galuh Tegar P for Infest.</i></b></p>
 * 
 * @author Danang Galuh Tegar P
 * @version 2017.03.10.0001
 * @see java.rmi.Remote
 * @see java.rmi.server.UnicastRemoteObject
 * @see com.jogjadamai.infest.communication.IProtocolServer
 * @see com.jogjadamai.infest.persistence.InfestPersistence
 */
public final class ProtocolServer extends UnicastRemoteObject implements IProtocolServer {
    
    private final byte[] SESSION_BYTE;
    private final int SERVICE_PORT;
    private final String REGISTRY_NAME;
    private final Registry REGISTRY;
    private final SecureRandom SECURE_RANDOM;
    private static ProtocolServer INSTANCE;
    private final List<IProtocolClient> CLIENT_LIST;
    
    private final File file;
    
    private InfestEntityController entityController;
    private Boolean isServerActive;
    
    /**
     * <h2>static method <code>getInstance()</code></h2>
     * <p>Class <code>ProtocolServer</code> is a singleton-ed class, so any instantiation
     * of this class will no be permitted. Instead, to get the object instance of
     * the class, use this static method <code>ProtocolServer.getInstance()</code>.</p>
     * 
     * @return Object instance of <code>ProtocolServer</code>.
     * @throws RemoteException A <code>RemoteException</code> is the common 
     *                         superclass for a number of communication-related
     *                         exceptions that may occur during the execution
     *                         of a remote method call.
     */
    public static ProtocolServer getInstance() throws RemoteException {
        if(INSTANCE == null) INSTANCE = new ProtocolServer();
        return INSTANCE;
    }
    
    private ProtocolServer() throws RemoteException {
        super();
        SESSION_BYTE = new byte[32];
        SERVICE_PORT = 42700;
        REGISTRY_NAME = "InfestAPIServer";
        REGISTRY = LocateRegistry.createRegistry(SERVICE_PORT);
        SECURE_RANDOM = new SecureRandom();
        CLIENT_LIST = new ArrayList<IProtocolClient>();
        isServerActive = false;
        File directory = new File("logs");
        if (!directory.exists()) {
            directory.mkdir();
        }
        file = new File("logs/ProtocolServer.log");
        try {
            file.createNewFile();
        } catch (IOException ex) {
            System.err.println("[INFEST] " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now()) + ": " + ex.getLocalizedMessage());
        }
        initiateServer();
    }
    
    private void initiateServer() throws RemoteException {
        REGISTRY.rebind(REGISTRY_NAME, this);
        SECURE_RANDOM.nextBytes(SESSION_BYTE);
        setStatus("Current INSTANCE_HASH: " + Arrays.toString(SESSION_BYTE));
        setStatus("Loading Eclipse Link persistence unit connection the Infest Database...");
        InfestPersistence.getControllerInstance(InfestPersistence.Entity.FEATURES).readAll();
        setStatus("Eclipse Link persistence unit loaded.");
        setStatus("Infest API Server has been reserved on " + SERVICE_PORT + ".");
    }

    private void setStatus(String status) {
        String toWrite = "[INFEST] " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now()) + ": " + status + "\n";
        System.out.print(toWrite);
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(toWrite);
            writer.flush();
        }catch (SecurityException | IOException ex) {
            System.err.println("[INFEST] " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now()) + ": " + ex.getLocalizedMessage());
        } 
    }
    
    /**
     * <h2>method <code>start()</code></h2>
     * <p>Method <code>start()</code> is used to start the <code>ProtocolServer</code> 
     * to listen to client request.</p>
     */
    public void start() {
        this.isServerActive = true;
        if(this.isServerActive) setStatus("Infest API Server is now listening to client requests.");
    }
    
    /**
     * <h2>method <code>stop()</code></h2>
     * <p>Method <code>stop()</code> is used to stop the <code>ProtocolServer</code> 
     * to listen to client request.</p>
     */
    public void stop() {
        this.isServerActive = false;
        if(!this.isServerActive) setStatus("Infest API Server has stoped listening to client requests.");
    }
    
    /**
     * <h2>method <code>isServerActive()</code></h2>
     * <p>Method <code>isServerActive()</code> is used to to get the 
     * <code>ProtocolServer</code> status, whether active or not active.</p>
     * 
     * @return <code>Boolean</code> of server status.
     */
    public Boolean isServerActive() {
        return this.isServerActive;
    }
    
    /**
     * <h2>method <code>authenticate()</code></h2>
     * <p>Method <code>authenticate</code> is used to authenticating a 
     * <code>IProtocolClient</code> object based on its type and security number. 
     * If <code>IProtocolClient</code> object's security number match of its type,
     * it will returning <code>true</code>.</p>
     * 
     * @param client <code>IProtocolClient</code> object to be authenticated.
     * @return Boolean indicating <code>true</code> when method execution is success.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Integer authenticate(IProtocolClient client) throws java.rmi.RemoteException {
        setStatus("authenticate(): A/An " + client.getType().name() + " client is requesting this method. Server is trying to authenticating this client.");
        Boolean isVerified = ProtocolSecurity.isVerified(client);
        client.setServerSession(isVerified ? SESSION_BYTE : new byte[0x0]);
        client.setClientSession(isVerified ? CLIENT_LIST.size() : -1);
        CLIENT_LIST.add(client);
        setStatus("authenticate(): Authentication process of " + client.getType().name() + " client is " + (isVerified ? "SUCCESSFUL" : "FAILED") + ".");
        return client.getClientSession();
    }
    
    private Boolean isClientAuthenticated(IProtocolClient client) throws java.rmi.RemoteException {
        return ( isServerActive() && client.equals(CLIENT_LIST.get(client.getClientSession())) );
    }
    
    /**
     * <h2>deprecated method <code>login()</code></h2>
     * <p>As of ICP 2017.04.12, method <code>login</code> will not be used anymore. 
     * Instead of using <code>login()</code> and <code>logout()</code>, 
     * <code>ProtocolServer</code> should verify the authenticity of each 
     * <code>IProtocolClient</code> object which trying to call 
     * <code>Protocol</code>s methods. Thus, <code>IProtocolClient</code> object
     * should authenticate themselves before calling any <code>Protocol</code>'s
     * methods.</p>
     * <br>
     * <p>To authenticate a <code>IProtocolClient</code> object, use
     * <code>ProtocolServer.authenticate()</code>.</p>
     * 
     * @param client <code>IProtocolClient</code> object to be authenticated.
     * @return IProtocolClient Authenticated <code>IProtocolClient</code> object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Deprecated
    @Override
    public IProtocolClient login(IProtocolClient client) throws RemoteException {
        return client;
    }

    /**
     * <h2>method <code>createCart()</code></h2>
     * <p>Method <code>createCart</code> is used to create a new 
     * <code>Carts</code> entity based on the <code>Carts</code> object.</p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @param cart   <code>Carts</code> object to be created.
     * @return The Infest entity with ID inside.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Carts createCart(IProtocolClient client, Carts cart) throws RemoteException {
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.CARTS);
            switch(client.getType()) {
                case CUSTOMER:
                    setStatus("createCart(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    entityController.create(cart);
                    break;
                default:
                    setStatus("createCart(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    cart = null;
                    break;
            }
        } else {
            setStatus("createCart(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            cart = null;
        }
        return cart;
    }

    /**
     * <h2>method <code>createOrder()</code></h2>
     * <p>Method <code>createOrder</code> is used to create a new 
     * <code>Orders</code> entity based on the <code>Orders</code> object.</p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @param order  <code>Orders</code> object to be created.
     * @return Infest entity with ID inside.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Orders createOrder(IProtocolClient client, Orders order) throws RemoteException {
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.ORDERS);
            switch(client.getType()) {
                case CUSTOMER:
                    setStatus("createOrder(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    entityController.create(order);
                    entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.MENUS);
                    Menus menu = (Menus) entityController.read(order.getIdmenu().getId());
                    int newStock = menu.getStock() - order.getTotal();
                    setStatus("createOrder(): Menu update trigered with new stock (old: " + menu.getStock() + ", new: " + newStock + ").");
                    menu.setStock(newStock);
                    try {
                        entityController.update(menu);
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    setStatus("createOrder(): UNAUTHORIZED client is requesting this method. Server denied the request.");
                    order = null;
                    break;
            }
        } else {
            setStatus("createOrder(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            order = null;
        }
        return order;
    }

    /**
     * <h2>method <code>createMenu()</code></h2>
     * <p>Method <code>createMenu</code> is used to create a new 
     * <code>Menus</code> entity based on the <code>Menus</code> object.</p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @param menu   <code>Menus</code> object to be created.
     * @return Infest entity with ID inside.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Menus createMenu(IProtocolClient client, Menus menu) throws RemoteException {
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.MENUS);
            switch(client.getType()) {
                case OPERATOR:
                    setStatus("createMenu(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    entityController.create(menu);
                    break;
                default:
                    setStatus("createMenu(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    menu = null;
                    break;
            }
        } else {
            setStatus("createMenu(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            menu = null;
        }
        return menu;
    }

    /**
     * <h2>method <code>createTable()</code></h2>
     * <p>Method <code>createTable</code> is used to create a new 
     * <code>Tables</code> entity based on the <code>Tables</code> object.</p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @param table  <code>Tables</code> object to be created.
     * @return Infest entity with ID inside.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Tables createTable(IProtocolClient client, Tables table) throws RemoteException {
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.TABLES);
            switch(client.getType()) {
                case OPERATOR:
                    setStatus("createTable(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    entityController.create(table);
                    break;
                default:
                    setStatus("createTable(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    table = null;
                    break;
            }
        } else {
            setStatus("createTable(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            table = null;
        }
        return table;
    }

    /**
     * <h2>method <code>readCart()</code></h2>
     * <p>Method <code>readCart</code> is used to read <code>Carts</code> entity
     * based on the ID of the <code>Carts</code> entity. This method will return
     * complete <code>Carts</code> entity object <b>if and only if</b> the 
     * <code>IProtocolClient.Type</code> in <code>IProtocolClient</code> object 
     * included herein is permitted.</p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @param id The ID of entity to be read.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Carts readCart(IProtocolClient client, Integer id) throws RemoteException {
        Carts cart = new Carts();
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.CARTS);
            switch(client.getType()) {
                case CUSTOMER:
                    setStatus("readCart(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    cart = (Carts) entityController.read(id);
                    break;
                default:
                    setStatus("readCart(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    cart = null;
                    break;
            }
        } else {
            setStatus("readCart(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            cart = null;
        }
        return cart;
    }

    /**
     * <h2>method <code>readFeature()</code></h2>
     * <p>Method <code>readFeature</code> is used to read <code>Features</code> 
     * entity based on the ID of the <code>Carts</code> entity. This method will
     * return complete <code>Features</code> entity object <b>if and only if</b>
     * the  <code>IProtocolClient.Type</code> in <code>IProtocolClient</code> object 
     * included herein is permitted.</p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @param id The ID of entity to be read.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Features readFeature(IProtocolClient client, Integer id) throws RemoteException {
        Features feature = new Features();
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.FEATURES);
            switch(client.getType()) {
                case ADMINISTRATOR:
                    setStatus("readFeatures(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    feature = (Features) entityController.read(id);
                    break;
                case OPERATOR:
                    setStatus("readFeatures(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    feature = (Features) entityController.read(id);
                    break;
                case CUSTOMER:
                    setStatus("readFeatures(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    feature = (Features) entityController.read(id);
                    break;
                default:
                    setStatus("readFeatures(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    feature = null;
                    break;
            }
        } else {
            setStatus("readFeatures(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            feature = null;
        }
        return feature;
    }
  
    /**
     * <h2>method <code>readMenu()</code></h2>
     * <p>Method <code>readMenu</code> is used to read <code>Menus</code> 
     * entity based on the ID of the <code>Menus</code> entity. This method will
     * return complete <code>Menus</code> entity object <b>if and only if</b>
     * the  <code>IProtocolClient.Type</code> in <code>IProtocolClient</code> object 
     * included herein is permitted.</p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @param id The ID of entity to be read.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Menus readMenu(IProtocolClient client, Integer id) throws RemoteException {
        Menus menu = new Menus();
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.MENUS);
            switch(client.getType()) {
                case OPERATOR:
                    setStatus("readMenu(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    menu = (Menus) entityController.read(id);
                    break;
                case CUSTOMER:
                    setStatus("readMenu(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    menu = (Menus) entityController.read(id);
                    break;
                default:
                    setStatus("readMenu(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    menu = null;
                    break;
            }
        } else {
            setStatus("readMenu(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            menu = null;
        }
        return menu;
    }

    /**
     * <h2>method <code>readOrder()</code></h2>
     * <p>Method <code>readOrder</code> is used to read <code>Orders</code> 
     * entity based on the ID of the <code>Orders</code> entity. This method will
     * return complete <code>Orders</code> entity object <b>if and only if</b>
     * the  <code>IProtocolClient.Type</code> in <code>IProtocolClient</code> object 
     * included herein is permitted.</p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @param id The ID of entity to be read.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Orders readOrder(IProtocolClient client, Integer id) throws RemoteException {
        Orders order = new Orders();
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.ORDERS);
            switch(client.getType()) {
                case OPERATOR:
                    setStatus("createOrder(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    order = (Orders) entityController.read(id);
                    break;
                default:
                    setStatus("readOrder(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    order = null;
                    break;
            }
        } else {
            setStatus("readOrder(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            order = null;
        }
        return order;
    }

    /**
     * <h2>method <code>readTable()</code></h2>
     * <p>Method <code>readTable</code> is used to read <code>Tables</code> 
     * entity based on the ID of the <code>Tables</code> entity. This method will
     * return complete <code>Tables</code> entity object <b>if and only if</b>
     * the  <code>IProtocolClient.Type</code> in <code>IProtocolClient</code> object 
     * included herein is permitted.</p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @param id The ID of entity to be read.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Tables readTable(IProtocolClient client, Integer id) throws RemoteException {
        Tables table = new Tables();
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.TABLES);
            switch(client.getType()) {
                case OPERATOR:
                    setStatus("readTable(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    table = (Tables) entityController.read(id);
                    break;
                case CUSTOMER:
                    setStatus("readTable(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    table = (Tables) entityController.read(id);
                    break;
                default:
                    setStatus("readTable(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    table = null;
                    break;
            }
        } else {
            setStatus("readTable(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            table = null;
        }
        return table;
    }

    /**
     * <h2>method <code>readAllCart()</code></h2>
     * <p>Method <code>readAllCart</code> is used to read all <code>Carts</code> 
     * entities in the database. This method will return
     * <code>List&lt;Carts&gt;</code>object <b>if and only if</b> the 
     * <code>IProtocolClient.Type</code> in <code>IProtocolClient</code> object 
     * included herein is permitted.<p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public List<Carts> readAllCart(IProtocolClient client) throws RemoteException {
        List<Carts> carts = new ArrayList<Carts>();
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.CARTS);
            switch(client.getType()) {
                case OPERATOR:
                    setStatus("readAllCart(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    carts = entityController.readAll();
                    break;
                case CUSTOMER:
                    setStatus("readAllCart(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    carts = entityController.readAll();
                    break;
                default:
                    setStatus("readAllCart(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    carts = null;
                    break;
            }
        } else {
            setStatus("readAllCart(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            carts = null;
        }
        return carts;
    }

    /**
     * <h2>method <code>readAllFeatures()</code></h2>
     * <p>Method <code>readAllFeatures</code> is used to read all 
     * <code>Features</code> entities in the database. This method will return
     * <code>List&lt;Features&gt;</code> object <b>if and only if</b> the 
     * <code>IProtocolClient.Type</code> in <code>IProtocolClient</code> object 
     * included herein is permitted.<p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public List<Features> readAllFeature(IProtocolClient client) throws RemoteException {
        List<Features> features = new ArrayList<Features>();
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.FEATURES);
            switch(client.getType()) {
                case ADMINISTRATOR:
                    setStatus("readAllFeatures(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    features = entityController.readAll();
                    break;
                case OPERATOR:
                    setStatus("readAllFeatures(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    features = entityController.readAll();
                    break;
                case CUSTOMER:
                    setStatus("readAllFeatures(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    features = entityController.readAll();
                    break;
                default:
                    setStatus("readAllFeatures(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    features = null;
                    break;
            }
        } else {
            setStatus("readAllFeatures(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            features = null;
        }
        return features;
    }

    /**
     * <h2>method <code>readAllMenu()</code></h2>
     * <p>Method <code>readAllMenu</code> is used to read all <code>Menus</code>
     * entities in the database. This method will return
     * <code>List&lt;Menus&gt;</code>object <b>if and only if</b> the 
     * <code>IProtocolClient.Type</code> in <code>IProtocolClient</code> object 
     * included herein is permitted.<p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public List<Menus> readAllMenu(IProtocolClient client) throws RemoteException {
        List<Menus> menus = new ArrayList<Menus>();
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.MENUS);
            switch(client.getType()) {
                case OPERATOR:
                    setStatus("readAllMenu(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    menus = entityController.readAll();
                    break;
                case CUSTOMER:
                    setStatus("readAllMenu(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    menus = entityController.readAll();
                    break;
                default:
                    setStatus("readAllMenu(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    menus = null;
                    break;
            }
        } else {
            setStatus("readAllMenu(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            menus = null;
        }
        return menus;
    }

    /**
     * <h2>method <code>readAllOrder()</code></h2>
     * <p>Method <code>readAllOrder</code> is used to read all <code>Orders</code>
     * entities in the database. This method will return
     * <code>List&lt;Orders&gt;</code>object <b>if and only if</b> the 
     * <code>IProtocolClient.Type</code> in <code>IProtocolClient</code> object 
     * included herein is permitted.<p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public List<Orders> readAllOrder(IProtocolClient client) throws RemoteException {
        List<Orders> orders = new ArrayList<Orders>();
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.ORDERS);
            switch(client.getType()) {
                case OPERATOR:
                    setStatus("readAllOrder(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    orders = entityController.readAll();
                    break;
                default:
                    setStatus("readAllOrder(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    orders = null;
                    break;
            }
        } else {
            setStatus("readAllOrder(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            orders = null;
        }
        return orders;
    }

    /**
     * <h2>method <code>readAllTable()</code></h2>
     * <p>Method <code>readAllTable</code> is used to read all <code>Tables</code>
     * entities in the database. This method will return
     * <code>List&lt;Tables&gt;</code>object <b>if and only if</b> the 
     * <code>IProtocolClient.Type</code> in <code>IProtocolClient</code> object 
     * included herein is permitted.<p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public List<Tables> readAllTable(IProtocolClient client) throws RemoteException {
        List<Tables> tables = new ArrayList<Tables>();
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.TABLES);
            switch(client.getType()) {
                case OPERATOR:
                    setStatus("readAllTable(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    tables = entityController.readAll();
                    break;
                case CUSTOMER:
                    setStatus("readAllTable(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    tables = entityController.readAll();
                    break;
                default:
                    setStatus("readAllTable(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    tables = null;
                    break;
            }
        } else {
            setStatus("readAllTable(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            tables = null;
        }
        return tables;
    }

    /**
     * <h2>method <code>readFinanceReport()</code></h2>
     * <p>NOTE: This method will generate financial report of all sales made.</p>
     * <br>
     * <p>Method <code>readFinanceReport</code> is used to generate financial 
     * report based on data in the database. This method will return 
     * <code>FinanceReport</code> entity objects <b>if and only if</b> the 
     * <code>IProtocolClient.Type</code> in <code>IProtocolClient</code> object 
     * included herein is permitted.</p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public List<FinanceReport> readFinanceReport(IProtocolClient client) throws RemoteException {
        return readFinanceReport(client, new Date(0, 0, 1), true);
    }

    /**
     * <h2>method <code>readFinanceReport()</code></h2>
     * <p>NOTE: This method will generate financial report of a specific date
     * based on given <code>java.time.LocalDate</code> object.</p>
     * <br>
     * <p>Method <code>readFinanceReport</code> is used to generate financial 
     * report based on data in the database. This method will return 
     * <code>FinanceReport</code> entity objects <b>if and only if</b> the 
     * <code>IProtocolClient.Type</code> in <code>IProtocolClient</code> object 
     * included herein is permitted.</p>
     * 
     * @param client <code>IProtocolClient</code> object to execute this method.
     * @param localDate <code>LocalDate</code> object specify report date.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public List<FinanceReport> readFinanceReport(IProtocolClient client, Date date) throws RemoteException {
        return readFinanceReport(client, date, false);
    }
    
    private List<FinanceReport> readFinanceReport(IProtocolClient client, Date date, Boolean allDate) throws RemoteException {
        List<FinanceReport> financeReport = new ArrayList<FinanceReport>();
        if(isClientAuthenticated(client)) {
            switch(client.getType()) {
                case OPERATOR:
                    setStatus("readFinanceReport(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    List<Menus> menus = this.readAllMenu(client);
                    for(Menus menu : menus) {
                        FinanceReport report = new FinanceReport();
                        report.setMenuId(menu.getId());
                        report.setMenuName(menu.getName());
                        report.setMenuPrice(menu.getPrice());
                        report.setMenuStatus(menu.getStatus());
                        report.setMenuStatusdate(menu.getStatusDate());
                        report.setOrderDate(allDate ? new Date(0, 0, 1) : new Date(date.getYear(), date.getMonth(), date.getDate()));
                        report.setOrderTotal(0);
                        List<Orders> orders = menu.getOrdersList();
                        for(Orders order : orders) {
                            if(allDate | date.equals(order.getIdcart().getDate())) {
                                report.setOrderTotal(report.getOrderTotal() + order.getTotal());
                            }
                        }
                        report.setIncome(report.getMenuPrice() * report.getOrderTotal());
                        financeReport.add(report);
                    }
                    break;
                default:
                    setStatus("readFinanceReport(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    financeReport = null;
                    break;
            }
        } else {
            setStatus("readFinanceReport(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            financeReport = null;
        }
        return financeReport;
    } 
    
    /**
     * <h2>method <code>updateFeature()</code></h2>
     * <p>Method <code>updateFeature</code> is used to update <code>Features</code> 
     * entity based on the <code>Features</code> object. This method will return 
     * <code>true</code> <b>if and only if</b> the <code>IProtocolClient.Type</code>
     * in <code>IProtocolClient</code> object included herein is permitted and 
     * the operation of update is completed successfully.</p>
     * 
     * @param client  <code>IProtocolClient</code> object to execute this method.
     * @param feature <code>Features</code> object to be updated.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Boolean updateFeature(IProtocolClient client, Features feature) throws RemoteException {
        Boolean isSuccess = false;
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.FEATURES);
            switch(client.getType()) {
                case ADMINISTRATOR:
                    setStatus("updateFeature(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    try {
                        entityController.update(feature);
                    } catch (NonexistentEntityException ex) {
                        setStatus("updateFeature(): " + client.getType().name() + " client on request: Caught non-existent entity exception.");
                        Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, "Caught non-existent entity exception.", ex);
                    } catch (Exception ex) {
                        setStatus("updateFeature(): " + client.getType().name() + " client on request: Caught an exception.");
                        Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, "Caught an exception.", ex);
                    }
                    isSuccess = true;
                    break;
                default:
                    setStatus("updateFeature(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    isSuccess = false;
                    break;
            }
        } else {
            setStatus("updateFeature(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            isSuccess = false;
        }
        return isSuccess;
    }
    
    /**
     * <h2>method <code>updateMenu()</code></h2>
     * <p>Method <code>updateMenu</code> is used to update <code>Menus</code> 
     * entity based on the <code>Menus</code> object. This method will return 
     * <code>true</code> <b>if and only if</b> the <code>IProtocolClient.Type</code>
     * in <code>IProtocolClient</code> object included herein is permitted and 
     * the operation of update is completed successfully.</p> 
     * 
     * @param client  <code>IProtocolClient</code> object to execute this method.
     * @param menu    <code>Menus</code> object to be updated.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Boolean updateMenu(IProtocolClient client, Menus menu) throws RemoteException {
        Boolean isSuccess = false;
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.MENUS);
            switch(client.getType()) {
                case OPERATOR:
                    setStatus("updateMenu(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    try {
                        entityController.update(menu);
                    } catch (NonexistentEntityException ex) {
                        setStatus("updateMenu(): " + client.getType().name() + " client on request: Caught non-existent entity exception.");
                        Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, "Caught non-existent entity exception.", ex);
                    } catch (Exception ex) {
                        setStatus("updateMenu(): " + client.getType().name() + " client on request: Caught an exception.");
                        Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, "Caught an exception.", ex);
                    }
                    isSuccess = true;
                    break;
                default:
                    setStatus("updateMenu(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    isSuccess = false;
                    break;
            }
        } else {
            setStatus("updateMenu(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * <h2>method <code>updateTable()</code></h2>
     * <p>Method <code>updateTable</code> is used to update <code>Tables</code> 
     * entity based on the <code>Tables</code> object. This method will return 
     * <code>true</code> <b>if and only if</b> the <code>IProtocolClient.Type</code>
     * in <code>IProtocolClient</code> object included herein is permitted and 
     * the operation of update is completed successfully.</p>
     * 
     * @param client  <code>IProtocolClient</code> object to execute this method.
     * @param table   <code>Tables</code> object to be updated.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Boolean updateTable(IProtocolClient client, Tables table) throws RemoteException {
        Boolean isSuccess = false;
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.TABLES);
            switch(client.getType()) {
                case OPERATOR:
                    setStatus("updateTable(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    try {
                        entityController.update(table);
                    } catch (NonexistentEntityException ex) {
                        setStatus("updateTable(): " + client.getType().name() + " client on request: Caught non-existent entity exception.");
                        Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, "Caught non-existent entity exception.", ex);
                    } catch (Exception ex) {
                        setStatus("updateTable(): " + client.getType().name() + " client on request: Caught an exception.");
                        Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, "Caught an exception.", ex);
                    }
                    isSuccess = true;
                    break;
                default:
                    setStatus("updateTable(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    isSuccess = false;
                    break;
            }
        } else {
            setStatus("updateTable(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            isSuccess = false;
        }
        return isSuccess;
    }
    
    /**
     * <h2>method <code>updateCart()</code></h2>
     * <p>Method <code>updateCart</code> is used to update <code>Carts</code> 
     * entity based on the <code>Carts</code> object. This method will return 
     * <code>true</code> <b>if and only if</b> the <code>IProtocolClient.Type</code>
     * in <code>IProtocolClient</code> object included herein is permitted and 
     * the operation of update is completed successfully.</p>
     * 
     * @param client  <code>IProtocolClient</code> object to execute this method.
     * @param cart   <code>Carts</code> object to be updated.
     * @return Result of client object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Boolean updateCart(IProtocolClient client, Carts cart) throws RemoteException{
        Boolean isSuccess = false;
        if(isClientAuthenticated(client)) {
            entityController = InfestPersistence.getControllerInstance(InfestPersistence.Entity.CARTS);
            switch(client.getType()) {
                case CUSTOMER:
                    setStatus("updateCart(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
                    try {
                        entityController.update(cart);
                    } catch (NonexistentEntityException ex) {
                        setStatus("updateCart(): " + client.getType().name() + " client on request: Caught non-existent entity exception.");
                        Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, "Caught non-existent entity exception.", ex);
                    } catch (Exception ex) {
                        setStatus("updateCart(): " + client.getType().name() + " client on request: Caught an exception.");
                        Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, "Caught an exception.", ex);
                    }
                    isSuccess = true;
                    break;
                default:
                    setStatus("updateCart(): Server denied request from a/an " + client.getType().name() + " client. This client type IS NOT PERMITTED to request this method.");
                    isSuccess = false;
                    break;
            }
        } else {
            setStatus("updateCart(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            isSuccess = false;
        }
        return isSuccess;
    }
    
    private String getSalt() {
        ProgramPropertiesManager programPropertiesManager = ProgramPropertiesManager.getInstance();
        String salt;
        try {
            salt = programPropertiesManager.getProperty("salt");
            if(salt.isEmpty()) throw new NullPointerException();
        } catch(NullPointerException ex) {
            System.err.println("[INFEST] " + ex);
            salt = "";
            throw ex;
        }
        return salt;
    }
    
    private Credentials createDefaultCredentials(IProtocolClient client) {
        Credentials credentials;
        String salt;
        try {
            salt = getSalt();
        } catch (NullPointerException ex) {
            return null;
        }
        try {
            credentials = CredentialsManager.createDefaultEncryptedCredentials(client.getType(), salt);
            File credFile = new java.io.File(client.getType().name().toLowerCase() + ".crd");
            credFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(credFile, false);
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(credentials);
            } catch (IOException ex) {
                System.err.println("[INFEST] " + ex);
            }
            try {
                fos.close();
            } catch (IOException ex) {
                System.err.println("[INFEST] " + ex);
            }
        } catch (Exception ex) {
            credentials = null;
            System.err.println("[INFEST] " + ex);
        }
        return credentials;
    }
    
    /**
     * <h2>method <code>getCredential()</code></h2>
     * <p>Method <code>getCredential</code> is used to read <code>Credential</code> 
     * of <code>IProtocolClient.Type</code>
     * 
     * @param client  <code>IProtocolClient</code> object to execute this method.
     * @return Credential information of <code>IProtocolClient.Type</code>.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Override
    public Credentials getCredentials(IProtocolClient client) throws java.rmi.RemoteException{
        Credentials credential;
        if(client.equals(CLIENT_LIST.get(client.getClientSession()))) {
            setStatus("getCredential(): A/An " + client.getType().name() + " client is requesting this method. Server is now serving the client.");
            File credFile = new File(client.getType().name().toLowerCase() + ".crd");
            if(credFile.exists() && !credFile.isDirectory()) {
                FileInputStream fis;
                try {
                    fis = new FileInputStream(credFile);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    try {
                        credential = (Credentials) ois.readObject();
                    } catch (ClassNotFoundException ex) {
                        System.err.println("[INFEST] " + ex);
                        credential = null;
                    } 
                    try {
                        ois.close();
                        fis.close();
                    } catch (IOException ex) {
                        System.err.println("[INFEST] " + ex);
                        credential = createDefaultCredentials(client);
                    }
                } catch (FileNotFoundException ex) {
                    System.err.println("[INFEST] " + ex);
                    credential = createDefaultCredentials(client);
                } catch (IOException ex) {
                    System.err.println("[INFEST] " + ex);
                    credential = createDefaultCredentials(client);
                }
            } else {
                credential = createDefaultCredentials(client);
            }
        } else {
            setStatus("getCredential(): Server denied request from an UN-AUTHENTICATED " + client.getType().name() + " client.");
            credential = null;
        }
        return credential;
    }

    /**
     * <h2>deprecated method <code>logout()</code></h2>
     * <p>As of ICP 2017.04.12, method <code>logout</code> will not be used anymore. 
     * Instead of using <code>login()</code> and <code>logout()</code>, 
     * <code>ProtocolServer</code> should verify the authenticity of each 
     * <code>IProtocolClient</code> object which trying to call 
     * <code>Protocol</code>s methods. Thus, <code>IProtocolClient</code> object
     * should authenticate themselves before calling any <code>Protocol</code>'s
     * methods.</p>
     * <br>
     * <p>To authenticate a <code>IProtocolClient</code> object, use
     * <code>ProtocolServer.authenticate()</code>.</p>
     * 
     * @param client <code>IProtocolClient</code> object to be de-authenticated.
     * @return IProtocolClient De-authenticated <code>IProtocolClient</code> object.
     * @throws java.rmi.RemoteException A <code>RemoteException</code> is the
     *                                  common superclass for a number of
     *                                  communication-related exceptions that
     *                                  may occur during the execution of a
     *                                  remote method call.
     */
    @Deprecated
    @Override
    public IProtocolClient logout(IProtocolClient client) throws RemoteException {
        return client;
    }

}
