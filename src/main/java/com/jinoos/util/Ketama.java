package com.jinoos.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ketama
{
    private static Logger log = LoggerFactory.getLogger(Ketama.class);

    // Constants
    public static final int NATIVE_HASH = 0;                    // native String.hashCode();
    public static final int KETAMA_HASH = 1;                    // MD5 Based

    private int hashingAlg = KETAMA_HASH;
    
    private boolean initialized = false;
    private Integer totalWeight = 0;
    
    private TreeMap<Long, KetamaNode> buckets = new TreeMap<Long, KetamaNode>();  
    private List<KetamaNode> nodes = new ArrayList<KetamaNode>();
    
    public boolean isInitialized()
    {
        return initialized;
    }
    
    public int getNodeSize()
    {
        return this.nodes.size();
    }
    
    public void addNode(KetamaNode node) throws IllegalStateException
    {
        if(initialized)
            throw new IllegalStateException("This instance initialized already.");
        
        nodes.add(node);
    }
    
/*    public void setNodes(List<KetamaNode> nodes) throws IllegalStateException
    {
        if(initialized)
            throw new IllegalStateException("This instance initialized already.");
        
        if(nodes.size() == 0)
            return;
        
        this.nodes = nodes;
        initialized = false;
    }*/
    
    public List<KetamaNode> getNodes()
    {
        return this.nodes;
    }
    
    public boolean initialize() throws IllegalStateException
    {
        if(nodes.size() == 0)
            throw new IllegalStateException("Nothing in node list.");
            
        if(initialized)
            throw new IllegalStateException("This instance already initialized.");
        
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("No md5 algorythm found" );
            throw new IllegalStateException("No md5 algorythm found");
        }
        
        totalWeight = 0;
        for(KetamaNode n : nodes)
        {
            totalWeight += n.getWeight();
        }
        
        for(KetamaNode n : nodes)
        {
            String nodeString = n.getNodeString();
            
            double factor = Math.floor(((double)(40*nodes.size()*n.getWeight()))/(double)this.totalWeight);
            
            for(long j = 0; j < factor; j++) {
                byte[] d = md5.digest((nodeString + "-" + j).getBytes());
                for(int h=0;h<4;h++) {
                    Long k = 
                          ((long)(d[3+h*4]&0xFF) << 24)
                        | ((long)(d[2+h*4]&0xFF) << 16)
                        | ((long)(d[1+h*4]&0xFF) << 8)
                        | ((long)(d[0+h*4]&0xFF));
                    buckets.put(k, n);
                    log.debug("Added " + nodeString + " to server bucket." );
                }               
            }
        }
        
        initialized = true;
        log.info("Initialized Ketama.");
        return true;
    }
    
    public KetamaNode getNode(String key) throws IllegalStateException
    {
        if(!initialized)
            throw new IllegalStateException("Not initialized instance.");
        
        Long hv = this.calculateHash(key);
        KetamaNode node = buckets.get(this.findPointFor(hv));
//        String server = buckets.get(this.findPointFor(hv));
        log.debug("Node choose " + node.getNodeString() + " for " + key);
        return node;
    }

    private Long findPointFor(Long hashK) {
        // ceilingKey returns the least key greater than or equal to the given key, 
        // or null if no such key. 
        Long k = buckets.ceilingKey(hashK);
        // if none found, it must be at the end, return the lowest in the tree
        // (we go over the end the continuum to the first entry)
        if(k == null)
            k = buckets.firstKey();
        return k;
    }
    
    private Long calculateHash(String key) {
        
        switch ( hashingAlg ) {
            case NATIVE_HASH:
                return (long)key.hashCode();
            case KETAMA_HASH:
                return md5HashingAlg(key);
            default:
                // use the native hash as a default
                hashingAlg = NATIVE_HASH;
                return (long)key.hashCode();
        }       
    }   
    
    private static Long md5HashingAlg(String key) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("No md5 algorythm found");
            throw new IllegalStateException("No md5 algorythm found");
        }
        md5.reset();
        md5.update(key.getBytes());
        byte[] bKey = md5.digest();
        long res = ((long) (bKey[3] & 0xFF) << 24)
                | ((long) (bKey[2] & 0xFF) << 16)
                | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF);
        return res;
    }
}
