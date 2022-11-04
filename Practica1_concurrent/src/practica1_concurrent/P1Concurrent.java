/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1_concurrent;

import java.util.concurrent.Semaphore;

/**
 *
 * @author mique
 */
public class P1Concurrent {

    final static int MAX_ESTUDIANTS=4;
    final static int NUM_ESTUDIANTS=10;
    public static boolean directorPorta=false;
    public static boolean directorEntra=false;
    public static int contador=0;
    
    public static Semaphore mutexDirector = new Semaphore(0);
    public static Semaphore mutexIncrement = new Semaphore(1);
    public static Semaphore mutexDirectorDins = new Semaphore(1);
    public static Semaphore mutexComprovacio = new Semaphore(1);
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("SIMULACIÓ DE LA SALA D'ESTUDI");
        System.out.println("NOMBRE TOTAL D'ESTUDIANTS "+NUM_ESTUDIANTS);
        System.out.println("NOMBRE MÀXIM D'ESTUDIANTS "+MAX_ESTUDIANTS);
        Thread director=new Thread(new Director());
        Thread [] estudiants = new Thread [NUM_ESTUDIANTS];
        
        director.start();
        
        for(int i = 0; i < estudiants.length; i++){
            estudiants[i] = new Thread(new Estudiant(i));
            estudiants[i].start();
        }

        for(int i = 0; i < estudiants.length; i++){
            estudiants[i].join();
        }
        
        director.join();
        System.out.println("SIMULACIÓ ACABADA");
    }
    
}
