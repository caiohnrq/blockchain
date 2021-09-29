package com.app.blockchain.model;

import com.app.blockchain.util.StringUtils;
import lombok.extern.java.Log;

import java.util.Date;
import java.util.logging.Level;

@Log
public class Block {

    public String hashId;
    public String prevHash;
    private final String data;
    private final Long timestamp;
    private int nonce;

    public Block(String data, String prevHash){
        this.data = data;
        this.prevHash = prevHash;
        this.timestamp = new Date().getTime();
        this.hashId = calculateHash();
    }

    private String calculateHash() {
        var target = new String(new char[Blockchain.getDifficulty()]).replace('\0', '0');
        setHashId(StringUtils.applySha256(
                prevHash +
                        timestamp +
                        nonce +
                        data
        ));

        while (!getHashId().substring(0, Blockchain.getDifficulty()).equals(target)){
            nonce++;
            setHashId(calculateHash());
        }
        return hashId;
    }

    public static Block mineBlock(){
        var size = Blockchain.getBlockchain().size();

        Block newBlock = (size == 0) ?
                createBlock(String.format("Block number: %s", ++size), Blockchain.GENESIS_BLOCK) :
                createBlock(String.format("Block number: %s", ++size), Blockchain.getLast(Blockchain.getBlockchain()).getHashId());

        return isBlockValid(newBlock) ? newBlock : null;
    }

    private static Block createBlock(String data, String prevHash) {
        return new Block(data, prevHash);
    }

    public static Boolean isBlockValid(Block currentBlock) {
        //comparing registered hash on the block and calculated hash
        if(!currentBlock.hashId.equals(currentBlock.calculateHash()) ){
            log.log(Level.INFO, "Current Hashes not equal");
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return String.format("BLOCK - [HashId: %s, PrevHash: %s, Data: %s, Timestamp: %s]", hashId, prevHash, data, timestamp);
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getPrevHash() {
        return prevHash;
    }
}
