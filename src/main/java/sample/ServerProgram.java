package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServerProgram {
    static ObjectInputStream is = null;
    static ObjectOutputStream os = null;
    static Handle handle;
    static List<Double> y;
    public static void main(String args[]) throws IOException {
        ServerSocket listener = null;
        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;
        try {
            listener = new ServerSocket(7777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                Socket socketOfServer = listener.accept();
                new ServiceThread(socketOfServer, clientNumber++).start();
                System.out.println(clientNumber);
            }
        } finally {
            listener.close();
        }
    }
//    private static void log(String message) {
//        System.out.println(message);
//    }

    private static class ServiceThread extends Thread {

        private int clientNumber;
        private Socket socketOfServer;

        public ServiceThread(Socket socketOfServer, int clientNumber) {
            this.clientNumber = clientNumber;
            this.socketOfServer = socketOfServer;
        }
        @Override
        public void run() {
            try {
                handle = new Handle();
                is = new ObjectInputStream(socketOfServer.getInputStream());
                os = new ObjectOutputStream(socketOfServer.getOutputStream());
                while (true) {
                    Map<String, Object> map = (Map<String, Object>) is.readObject();
                    String function = (String) map.get("function");
                    Double xMin,xMax;
                    try{
                        xMin = Double.valueOf((Double) map.get("xMin"));
                        xMax = Double.valueOf((Double) map.get("xMax"));
                    }catch (Exception e){
                        xMin = 0.0;
                        xMax = 0.0;
                    }
                    List<Double> y_value = new ArrayList<>();
                    try {
                        y_value = handle.cal_da_thuc(function,xMin,xMax);
                    }catch (Exception e){
                        System.out.print("");
                    }
                    os.writeObject(y_value);
                }
            } catch (IOException e) {
//                e.printStackTrace();
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
            }
        }
    }
}