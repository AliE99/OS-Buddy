package com.operatingsystem.entities;

import com.operatingsystem.utils.RandomValue;
import com.operatingsystem.os.MemoryManager;

import java.util.concurrent.TimeUnit;

public class Process {

    private int PID;
    private boolean allocate;
    private int size;
    private int address;
    private int duration;
    private int requests;
    private Address usedAddress;

    public Process(int PID) {
        RandomValue random = new RandomValue();
        this.PID = PID;
        allocate = random.bool();
        duration = random.time();
        requests = random.request();
        address = random.address();
        size = random.size();
    }

    public boolean isAllocate(){
        return allocate;
    }

    public int getSize() {
        return size;
    }

    public void request(MemoryManager memoryManager) throws InterruptedException {
        for (int i = 0; i < requests; i++) {
            if (allocate) {
                TimeUnit.SECONDS.sleep(duration);
                usedAddress = new Address();
                usedAddress = memoryManager.allocate(size);
            } else {
                TimeUnit.SECONDS.sleep(duration);
                memoryManager.deallocate(size);
            }
        }
    }

    public void getAddress() {
        if (usedAddress == null)
            System.out.println("\uD83D\uDCA4 Process with Number : " + PID + " don't use any space");
        else {
            System.out.println("\uD83D\uDCBB Process with Number : " + PID + " used address: " + usedAddress.getBase()
                    + "  with size: " + usedAddress.getSize());
        }
    }

    public void getInfo() {
        System.out.println("\uD83D\uDCCB Process Number " + PID + ":");

        if (allocate)
            System.out.println("\uD83D\uDCBE Allocate memory size: " + size + " Kilobytes");
        else
            System.out.println("\uD83D\uDCBE Deallocate memory address: " + address);
        System.out.println("⚙ Number of Requests: " + requests);
        System.out.println("⏱ Time: " + duration);

    }
}
