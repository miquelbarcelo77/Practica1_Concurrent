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
public class Estudiant implements Runnable {
    
    Random rn=new Random();
    int id;
    String [] noms = {"Pelayo", "Beltran", "Cayetano", "Borja", "Jacobo", "Juan", 
        "Carlos", "Miquel", "Alejandro", "Pedro", "Andreu", "Maria", "Laura", "Josefina"};
    String nom;

    public Estudiant(int i) {
        this.id=i;
        this.nom=this.noms[id];
    }
    
    @Override
    public void run(){
        
        try {
            Thread.sleep(200);
            P1Concurrent.mutexComprovacio.acquire();
            if(P1Concurrent.directorEntra==true){
                P1Concurrent.mutexDirectorDins.acquire();
            }
            P1Concurrent.mutexComprovacio.release();
            P1Concurrent.mutexIncrement.acquire();
            P1Concurrent.contador++;
            System.out.println(nom + " entra a la sala d'estudi, nombre d'estudiants " +P1Concurrent.contador);
            P1Concurrent.mutexIncrement.release();
            if(P1Concurrent.contador<P1Concurrent.MAX_ESTUDIANTS){
                System.out.println(nom + " estudia");
                //Sleep per simular que estudia
                Thread.sleep(300);
            }else{
                System.out.println(nom+": FESTA!!!!!");
                P1Concurrent.mutexComprovacio.acquire();
                if(P1Concurrent.directorPorta==true){
                    System.out.println("ALERTA que ve el director!!!!!!");
                    P1Concurrent.mutexDirector.release();
                }
                P1Concurrent.mutexComprovacio.release();
                //Sleep per simular la festa
                Thread.sleep(200);
            }
            P1Concurrent.mutexIncrement.acquire();
            P1Concurrent.contador--;
            System.out.println(nom + " surt de la sala d'estudi, nombre d'estudiants " +P1Concurrent.contador);
            if(P1Concurrent.contador==0){
                System.out.println(nom+": ADEU Senyor Director, pot entrar si vol, no hi ha ningú");
                P1Concurrent.mutexDirector.release();
            }
            P1Concurrent.mutexIncrement.release();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Estudiant.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
