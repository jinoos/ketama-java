package com.jinoos.util;

public class KetamaNodeHostPort implements KetamaNode
{
    private static final long serialVersionUID = 3333676027962754325L;

    public static int DEFAULT_WEIGHT = 1;
    
    private String host;
    private int    port;
    private int    weight;
    
    private void _ketamaNode(String host, int port, int weight)
    {
        this.host = host;
        this.port = port;
        this.weight = weight;
    }
    
    public KetamaNodeHostPort(KetamaNodeHostPort n)
    {
        _ketamaNode(n.getHost(), n.getPort(), n.getWeight());
    }

    public KetamaNodeHostPort(String host, int port)
    {
        _ketamaNode(host, port, DEFAULT_WEIGHT);
    }
    

    public KetamaNodeHostPort(String host, int port, int weight)
    {
        _ketamaNode(host, port, weight);
    }
    
    public String getNodeString()
    {
        return new String(host + ":" + port);
    }
    
    public boolean equals(KetamaNode o)
    {
        if(o == this)
            return true;
        
        return this.getNodeString().equals(o.getNodeString());
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
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
