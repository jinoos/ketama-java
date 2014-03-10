package com.jinoos.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KetamaTest extends Assert
{
    private Ketama ketama;

    @Before
    public void before()
    {
        ketama = new Ketama();
    }

    @Test
    public void isInitialized()
    {
        KetamaNode kn = new KetamaNodeHostPort("1.1.1.1", 80);
        ketama.addNode(kn);
        ketama.initialize();

        assertTrue(ketama.isInitialized());
    }

    @Test
    public void getNodeSizeAndAddNode()
    {
        KetamaNode kn = new KetamaNodeHostPort("1.1.1.1", 80);
        ketama.addNode(kn);

        assertTrue(ketama.getNodeSize() == 1);
    }

    @Test
    public void getNodesAndInitialize()
    {
        KetamaNode kn = null;
        kn = new KetamaNodeHostPort("1.1.1.1", 80);
        ketama.addNode(kn);
        kn = new KetamaNodeHostPort("2.2.2.2", 80);
        ketama.addNode(kn);

        assertTrue(ketama.getNodeSize() == 2);
        assertTrue(ketama.initialize());
    }

    @Test
    public void getNode()
    {
        KetamaNode kn = null;
        kn = new KetamaNodeHostPort("1.1.1.1", 80);
        ketama.addNode(kn);
        kn = new KetamaNodeHostPort("2.2.2.2", 80);
        ketama.addNode(kn);
        
        ketama.initialize();
        
        Map<String, KetamaNode> ketamaRes = new HashMap<String, KetamaNode>();
        for(int i = 0; i < 1000000; i++)
        {
            String key = "key-" + i;
            ketamaRes.put(key, ketama.getNode(key));
        }
        
        for(Map.Entry<String, KetamaNode> entry : ketamaRes.entrySet())
        {
            assertTrue(entry.getValue().equals(ketama.getNode(entry.getKey())));
        }
    }
    
    @Test
    public void getNode2()
    {
        KetamaNode kn = null;
        kn = new KetamaNodeHostPort("1.1.1.1", 80, 100);
        ketama.addNode(kn);
        kn = new KetamaNodeHostPort("2.2.2.2", 80, 50);
        ketama.addNode(kn);
        
        ketama.initialize();
        
        Map<String, KetamaNode> ketamaRes = new HashMap<String, KetamaNode>();
        Map<String, Integer> ketamaRes2 = new HashMap<String, Integer>();
        for(int i = 0; i < 1000; i++)
        {
            String key = "key-" + i;
            KetamaNode node = ketama.getNode(key);
            assertNotNull(node);
            ketamaRes.put(key, ketama.getNode(key));
            
            Integer cnt = ketamaRes2.get(node.getNodeString());
            if(cnt == null)
            {
                cnt = new Integer(1);
            }else
            {
                cnt += 1;
            }
            ketamaRes2.put(node.getNodeString(), cnt);
        }
        
        for(Map.Entry<String, Integer> entry : ketamaRes2.entrySet())
        {
            System.out.println("Key:" + entry.getKey() + ", Value:" + entry.getValue());
        }
    }
}
