

package oe.cav.web.util;

import java.util.zip.ZipInputStream;

public class ZipReader
{

    private ZipInputStream in;
    private String path;

    public ZipReader(String s)
    {
        in = null;
        path = null;
        path = s;
    }

    public boolean findEntry(String s)
    {
        return true;
    }
}
