package main;

import model.Monitor;
import model.Estudiante;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;


/*
Integrante: Juan Diego Rosero, David Abert Vergara
 */

public class Main {
    public static void main(String[] args) {
        final int MAX_ESTUDIANTES = 15;
        final int NUM_SILLAS = 3;

        // Semáforos
        Semaphore monitor = new Semaphore(1);
        Semaphore sillas = new Semaphore(NUM_SILLAS);

        // Generar un número aleatorio de estudiantes
        Random rnd = new Random();
        int numEstudiantes = rnd.nextInt(MAX_ESTUDIANTES) + 1;


        Monitor monitorThread = new Monitor(monitor, sillas);
        monitorThread.start();

        // Crear e iniciar los estudiantes
        for (int i = 1; i <= numEstudiantes; i++) {
            Estudiante estudianteThread = new Estudiante(i, monitor, sillas);
            estudianteThread.start();
        }
    }
}