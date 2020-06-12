package cse.maven_webmail.control;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHConnector {
    final String user = "root";
    final String passwd = "root@123";
    final int port = 20022;

    public void tunneling(){
        String host = "34.64.92.114";
        JSch jSch = new JSch();
        try {
            Session session = jSch.getSession(user, host, port);
            session.setPassword(passwd);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            session.setPortForwardingL(1234, "localhost", 3306);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
