package czat.irc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Inet6Address;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Protocol {

    public Socket soc = null;
    public InputStream input;
    public OutputStream output;
    public Scanner read;
    public PrintWriter write;
    public static Protocol instance = null;

    public Protocol(String ip, int port) {
        try {
            soc = new Socket(ip, port);

            input = soc.getInputStream();
            output = soc.getOutputStream();

            read = new Scanner(input);
            write = new PrintWriter(output, true);

            System.out.println("Klient podlaczyl sie do serwera");
        } catch (IOException ex) {
            System.out.println("Nie udalo sie podlaczyc do serwera: " + ex.getMessage());
        }
    }

    public static Protocol getInstance(String ip) {
        if (instance == null) {
            instance = new Protocol(ip, 9999);
            if (instance.soc == null) {
                instance = null;
            }
        }
        return instance;
    }

    public synchronized String odbierzWiadomosc(boolean thread) {
        System.out.println("Odbieranie wiadomosci");
        String mess = "";
        if (thread) {

            if (!read.hasNext("0") && !read.hasNext("1") && !read.hasNext("2") && !read.hasNext("3") && !read.hasNext("4") && !read.hasNext("5") && !read.hasNext("6") && !read.hasNext("7") && !read.hasNext("8")) {
                String odb = read.nextLine();
                if (odb.charAt(0) == '/') {
                    mess = odb.substring(1, odb.length());
                }
                //else mess = odb;
            }
            System.out.println("Wiadomosc odebrana przez thread");
        } else {
            if (read.hasNext()) {
                mess = read.nextLine();
            }
            System.out.println("Wiadomosc odebrana");
        }
        return mess;
    }

    public String rejestracja(String username, String password) {
        System.out.println("Rejestracja");
        write.println("REGISTER " + username + " " + password);
        String resp = odbierzWiadomosc(false);
        return resp;
    }

    public String logowanie(String username, String password) {
        System.out.println("Logowanie");
        write.println("LOGIN " + username + " " + password);
        String resp = odbierzWiadomosc(false);
        return resp;
    }

    public String wyslijWiadomosc(String mess) {
        System.out.println("Wysylanie wiadomosci");
        write.println("SEND " + mess);
        String resp = odbierzWiadomosc(false);
        return resp;
    }

    public Vector<String> listaPokojow() {
        Vector<String> lista = new Vector<>();
        System.out.println("Pobieranie listy pokojów");
        write.println("LIST");
        String resp = odbierzWiadomosc(false);
        for (String napis : resp.split(" ")) {
            if (!napis.equals("0") && !napis.equals("OK")) {
                lista.add(napis);
            }
        }
        return lista;
    }

    public String dolaczDoPokoju(String pokoj) {
        System.out.println("Dolaczanie do pokoju: " + pokoj);
        write.println("JOIN " + pokoj);
        String resp = odbierzWiadomosc(false);
        return resp;
    }

    public String opuscPokoj() {
        System.out.println("Opuszczanie pokoju");
        write.println("LEAVE");
        String resp = odbierzWiadomosc(false);
        return resp;
    }

    public String disconnect() {
        System.out.println("Disconnect");
        write.println("DISCONNECT");
        String resp = odbierzWiadomosc(false);
        return resp;
    }
}
