package cse.maven_webmail.control;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHConnector {
    final String user = "root";
    final String passwd = "root@123";
    final int port = 20022;
    Session session;

    public void tunneling(){
        String host = "34.64.92.114";
        JSch jSch = new JSch();
        try {
            session = jSch.getSession(user, host, port);
            session.setPassword(passwd);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            session.setPortForwardingL(1234, "localhost", 3306);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void closeSession(){
        session.disconnect();
    }

    public void temp(){
        String host = "34.64.92.114";
        JSch jSch = new JSch();
        try {
            session = jSch.getSession(user, host, port);
            session.setPassword(passwd);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            session.setPortForwardingL(4555, "localhost", 4555);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
