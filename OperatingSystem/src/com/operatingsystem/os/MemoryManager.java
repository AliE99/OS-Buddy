package com.operatingsystem.os;

import com.operatingsystem.entities.Address;
import com.operatingsystem.entities.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class MemoryManager {

    private List<List<Block>> blocks;
    private HashMap<Integer, Integer> addressList;


    public MemoryManager(int memorySize) {

        addressList = new HashMap<>();
        blocks = new ArrayList<>();
        int numberOfBlocks = (int) Math.ceil(Math.log(memorySize) / Math.log(2));

        for (int i = 0; i <= numberOfBlocks; i++) {
            blocks.add(i, new ArrayList<>());
        }
        blocks.get(numberOfBlocks).add(new Block(0, memorySize - 1));
    }

    public Address allocate(int size) {

        int exponent = (int) Math.ceil(Math.log(size) / Math.log(2));

        int index;

        Block temp_block;

        if (blocks.get(exponent).size() > 0) {

            temp_block = blocks.get(exponent).remove(0);

            System.out.println("\uD83D\uDCE5 Allocate Memory From " + temp_block.getStart() + "->" + temp_block.getFinish());

            addressList.put(temp_block.getStart(), temp_block.getFinish() - temp_block.getStart() + 1);

            return new Address(temp_block.getStart(), temp_block.getFinish() - temp_block.getStart() + 1);
        }


        for (index = exponent + 1; index < blocks.size(); index++) {

            if (blocks.get(index).size() == 0)
                continue;
            break;
        }

        if (index == blocks.size()) {
            System.out.println("\uD83D\uDEAB Memory Full !");
            return null;
        }

        temp_block = blocks.get(index).remove(0);

        index--;

        while (index >= exponent) {
            blocks.get(index).add(new Block(temp_block.getStart(), temp_block.getStart()
                    + (temp_block.getFinish() - temp_block.getStart()) / 2));
            blocks.get(index).add(new Block(temp_block.getStart()
                    + (temp_block.getFinish() - temp_block.getStart() + 1) / 2, temp_block.getFinish()));

            temp_block = blocks.get(index).remove(0);
            index--;
        }

        System.out.println("\uD83D\uDCE5 Allocate Memory From " + temp_block.getStart() + " -> " + temp_block.getFinish());

        addressList.put(temp_block.getStart(), temp_block.getFinish() - temp_block.getStart() + 1);
        return new Address(temp_block.getStart(), temp_block.getFinish() - temp_block.getStart() + 1);
    }

    public void deallocate(int address) {

        if (!addressList.containsKey(address)) {
            System.out.println("\uD83D\uDEA8 Address is not in Range !");
            return;
        }

        int size = (int) Math.ceil(Math.log(addressList.get(address))
                / Math.log(2));

        int index, buddy_level, buddy_address;

        blocks.get(size).add(new Block(address, address + (int) Math.pow(2, size) - 1));

        System.out.println("\uD83D\uDCE4 Memory is released from " + address + " to "
                + (address + (int) Math.pow(2, size) - 1));

        buddy_level = address / addressList.get(address);

        if (buddy_level % 2 != 0) {
            buddy_address = address - (int) Math.pow(2, size);
        } else {
            buddy_address = address + (int) Math.pow(2, size);
        }

        for (index = 0; index < blocks.get(size).size(); index++) {
            if (blocks.get(size).get(index).getStart() == buddy_address) {
                if (buddy_level % 2 == 0) {
                    blocks.get(size + 1).add(new Block(address, address
                            + 2 * ((int) Math.pow(2, size)) - 1));

                    System.out.println("\uD83D\uDEA7 Block intermix at "
                            + address + " and "
                            + buddy_address + " finished");
                } else {
                    blocks.get(size + 1).add(new Block(buddy_address,
                            buddy_address + 2 * ((int) Math.pow(2, size))
                                    - 1));

                    System.out.println("\uD83D\uDEA7 Block intermix at "
                            + buddy_address + " and "
                            + address + " finished");
                }
                blocks.get(size).remove(index);
                blocks.get(size).remove(blocks.get(size).size() - 1);
                break;
            }
        }
        addressList.remove(address);
    }
}
