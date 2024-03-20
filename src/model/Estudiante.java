package model;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Estudiante extends Thread {
    private final int id;
    private final Semaphore monitor;
    private final Semaphore sillas;

    public Estudiante(int id, Semaphore monitor, Semaphore sillas) {
        this.id = id;
        this.monitor = monitor;
        this.sillas = sillas;
    }

    public void run() {
        Random rnd = new Random();

        try {
            Thread.sleep(rnd.nextInt(5000)); // Tiempo simulado de programación

            while (true) {
                if (monitor.availablePermits() == 1) {
                    System.out.println("Estudiante " + id + " despierta al monitor...");
                    monitor.acquire(); // Obtener el semáforo para despertar al monitor
                    Thread.sleep(rnd.nextInt(1000)); // Tiempo simulado de interacción con el monitor
                    monitor.release(); // Liberar el semáforo después de despertar al monitor
                    break; // El estudiante desaparece después de recibir ayuda
                } else {
                    System.out.println("Estudiante " + id + " busca ayuda del monitor...");

                    // Si no hay sillas disponibles, ir a la sala de cómputo
                    if (!sillas.tryAcquire()) {
                        System.out.println("Estudiante " + id + " se va a la sala de cómputo y regresará más tarde.");
                        Thread.sleep(rnd.nextInt(5000)); // Tiempo simulado en la sala de cómputo
                        continue; // Volver a intentar obtener ayuda del monitor
                    }

                    // Sentarse en una silla en el corredor
                    System.out.println("Estudiante " + id + " espera en el corredor...");
                    Thread.sleep(rnd.nextInt(2000)); // Tiempo de espera
                    sillas.release(); // Levantarse de la silla al entrar a la oficina
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}