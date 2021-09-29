package com.app.blockchain.model;

import lombok.extern.flogger.Flogger;
import lombok.extern.java.Log;

import java.util.Objects;
import java.util.logging.Level;

@Log
public class Node {

    private static Blockchain blockchain;

    public static Blockchain connectToNode(){
        blockchain = Objects.requireNonNullElseGet(blockchain, Blockchain::new);
        log.log(Level.INFO, "Miner just connected to the Blockchain");
        return blockchain;
    }
}
