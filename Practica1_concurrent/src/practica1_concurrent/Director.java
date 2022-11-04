/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1_concurrent;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mique
 */
public class Director implements Runnable{
    
    Random rn=new Random();
    
    public Director() {
    }
    
    @Override
    public void run(){
        
        try {
            System.out.println("    El Sr. Director comença la ronda");
            
            for (int i = 1; i <= 3; i++) {
                if(P1Concurrent.contador>P1Concurrent.MAX_ESTUDIANTS){
                    System.out.println("    El Director està dins la sala d'estudi: S'HA ACABAT LA FESTA!");
                    P1Concurrent.directorEntra=true;
                    P1Concurrent.mutexDirector.acquire();
                    //FER QUE SURTIN TOTS
                }else{
                    if(P1Concurrent.contador==0){
                        //Passa de llarg
                        System.out.println("    El Director veu que no hi ha ningú a la sala d'estudis");
                    }else{
                        //No entra
                        System.out.println("    El Director està esperant per entrar. No molesta als que estudien");
                        P1Concurrent.directorPorta=true;
                        if(P1Concurrent.contador>P1Concurrent.MAX_ESTUDIANTS){
                            System.out.println("    El Director està dins la sala d'estudi: S'HA ACABAT LA FESTA!");
                            P1Concurrent.directorEntra=true;
                        }
                        P1Concurrent.mutexDirector.acquire();
                    }
                }
                System.out.println("    El director acaba la ronda "+i+" de 3");
                P1Concurrent.directorPorta=false;
                P1Concurrent.directorEntra=false;
                //Thread.sleep(300) simulacio 2
                Thread.sleep(210);
            }
            //Mirar aquest catch
        } catch (InterruptedException ex) {
                Logger.getLogger(Director.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
