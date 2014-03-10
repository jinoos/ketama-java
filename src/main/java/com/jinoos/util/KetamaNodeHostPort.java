package com.jinoos.util;

public class KetamaNodeHostPort implements KetamaNode
{
    public static int DEFAULT_WEIGHT = 1;
    
    private String ip;
    private int    port;
    private int    weight;
    
    private void _ketamaNode(String ip, int port, int weight)
    {
        this.ip = ip;
        this.port = port;
        this.weight = weight;
    }
    
    public KetamaNodeHostPort(KetamaNodeHostPort n)
    {
        _ketamaNode(n.getIp(), n.getPort(), n.getWeight());
    }

    public KetamaNodeHostPort(String ip, int port)
    {
        _ketamaNode(ip, port, DEFAULT_WEIGHT);
    }
    

    public KetamaNodeHostPort(String ip, int port, int weight)
    {
        _ketamaNode(ip, port, weight);
    }
    
    public String getNodeString()
    {
        return new String(ip + ":" + port);
    }
    
    public boolean equals(KetamaNode o)
    {
        if(o == this)
            return true;
        
        return this.getNodeString().equals(o.getNodeString());
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }
}
