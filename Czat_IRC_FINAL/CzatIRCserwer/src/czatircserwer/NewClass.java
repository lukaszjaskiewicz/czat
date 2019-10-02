/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package czatircserwer;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Lukasz
 */
public class NewClass {
    public static void main(String[] args){
        try{
            ServerSocket s=new ServerSocket(9999);
            Socket client=s.accept();
            while(true){}
        }catch(Exception e){

        }
    }
}
