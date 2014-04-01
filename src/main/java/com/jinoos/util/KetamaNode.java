package com.jinoos.util;

import java.io.Serializable;

public interface KetamaNode extends Serializable
{
    public String getNodeString();
    public String getHost();
    public int getPort();
    public int getWeight();
}
