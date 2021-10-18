package com.app.blockchain.model;

import com.google.gson.GsonBuilder;
import lombok.extern.java.Log;

import java.util.*;
import java.util.logging.Level;

@Log
public class Blockchain {

    public static final String GENESIS_BLOCK = "0";

    private static final int difficulty = 2;
    private static final LinkedHashSet<Block> blockchain = new LinkedHashSet<>();

    public static synchronized void mineBlock(String minerId) throws Exception {
        var block = Block.mineBlock();

        if(block != null) {
            getBlockchain().add(block);
            log.log(Level.INFO, String.format("%s mined a new block and added to blockchain %s", minerId, block));
        } else {
            log.log(Level.SEVERE, "Block mined was invalid");
            throw new Exception("Block mined was invalid");
        }
    }

    public static synchronized Boolean isChainValid() {
        blockchain
                .stream()
                .filter(block -> !block.getPrevHash().equals("0"))
                .forEach(Block::isBlockValid);
        return true;
    }

    public static <E> E getLast(Collection<E> list) {
        E last = null;
        for(E item : list) {
            last = item;
        }
        return last;
    }

    public static String printBlockchain(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
    }

    public static synchronized LinkedHashSet<Block> getBlockchain() {
        return blockchain;
    }

    public static int getDifficulty() {
        return difficulty;
    }
}
