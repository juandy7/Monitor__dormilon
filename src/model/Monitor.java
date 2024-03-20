package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Monitor extends Thread {
    private final Semaphore monitor;
    private final Semaphore sillas;

    public Monitor(Semaphore monitor, Semaphore sillas) {
        this.monitor = monitor;
        this.sillas = sillas;
    }

    public void run() {
        Random rnd = new Random();

        while (true) {
            try {
                monitor.acquire(); // El monitor adquiere el semáforo para realizar su tarea

                // Si hay estudiantes esperando en el corredor, atenderlos
                if (sillas.hasQueuedThreads()) {
                    System.out.println("Monitor está atendiendo a un estudiante.");
                    Thread.sleep(rnd.nextInt(3000)); // Tiempo simulado de ayuda
                    sillas.release(); // Liberar una silla en el corredor
                } else {
                    // Si no hay estudiantes esperando, el monitor duerme
                    System.out.println("Monitor está durmiendo...");
                    monitor.release(); // Liberar el semáforo para que otros hilos lo adquieran
                    Thread.sleep(rnd.nextInt(3000)); // Tiempo de siesta
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}