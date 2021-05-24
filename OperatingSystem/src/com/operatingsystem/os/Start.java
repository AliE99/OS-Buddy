package com.operatingsystem.os;

import com.operatingsystem.utils.Config;
import com.operatingsystem.entities.Process;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Start {

    private static Semaphore lock = new Semaphore(1);

    public static void run() {

        int initialMemory = Config.INITIAL_SIZE;

        ArrayList<Process> process_list = new ArrayList<>();
        MemoryManager memory_manager = new MemoryManager(initialMemory);

        process_list.add(new Process(0));
        process_list.add(new Process(1));
        process_list.add(new Process(2));
        process_list.add(new Process(4));
        process_list.add(new Process(5));


        Thread thread = new Thread(() -> {
            try {
                for (Process p : process_list
                ) {
                    lock.acquire();
                    p.request(memory_manager);
                    lock.release();
                }
            } catch (InterruptedException error) {
                error.printStackTrace();
            }
        });

        thread.run();

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try {
                reportInformation(process_list);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, 5, 5, TimeUnit.SECONDS);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        process_list.clear();
                        for (int i = 4; i < 8; i++) {
                            process_list.add(new Process(i));
                        }
                    }
                },
                15000
        );
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        for (Process p : process_list
                        ) {
                            try {
                                lock.acquire();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            try {
                                p.request(memory_manager);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            lock.release();
                        }
                    }
                },
                20000
        );


        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        process_list.clear();
                        for (int i = 8; i < 12; i++) {
                            process_list.add(new Process(i));
                        }
                    }
                },
                31000
        );
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        for (Process p : process_list
                        ) {
                            try {
                                lock.acquire();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            try {
                                p.request(memory_manager);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            lock.release();
                        }
                    }
                },
                32000
        );
    }

    private static void reportInformation(ArrayList<Process> processes) throws InterruptedException {
        lock.acquire();
        System.out.println("\uD83D\uDCCC Information Report : ");
        int usedMemory = 0;
        for (Process process : processes
        ) {
            process.getInfo();
            process.getAddress();
            if (process.isAllocate())
                usedMemory += process.getSize();
            System.out.println("↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔");
        }

        System.out.println("❌ Occupied memory: " + usedMemory + " Kilobyte");
        System.out.println("✔ Free memory: " + (Config.INITIAL_SIZE - usedMemory) + " Kilobyte");
        System.out.println("↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔↔");
        lock.release();
    }
}
