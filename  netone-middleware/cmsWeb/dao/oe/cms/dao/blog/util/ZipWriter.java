package oe.cms.dao.blog.util;
import java.io.*;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipWriter
{

    private ZipOutputStream out;

    private Hashtable names;

    public ZipWriter(String s)
    {
        out = null;
        names = new Hashtable();
        try
        {
            out = new ZipOutputStream(new FileOutputStream(s));
        }
        catch(Exception exception)
        {
            throw new RuntimeException("create file:" + s + " fail", exception);
        }
    }

    public ZipWriter(OutputStream outputstream)
    {
        out = null;
        names = new Hashtable();
        out = new ZipOutputStream(outputstream);
    }

    public void addDirectory(String s)
        throws IOException
    {
        if(s.length() == 0)
            return;
        if(!s.endsWith("/"))
            s = s + "/";
        if(names.put(s, "1") == null)
            out.putNextEntry(new ZipEntry(s));
    }

    public void addFile(File file)
        throws IOException
    {
        addFile(file.getName(), file);
    }

    public void addFile(String s, File file)
        throws IOException
    {
        if(file.isDirectory())
        {
            File afile[] = file.listFiles();
            if(!s.endsWith("/"))
                s = s + "/";
            addDirectory(s);
            for(int i = 0; i < afile.length; i++)
                addFile(s + afile[i].getName(), afile[i]);

        } else
        {
            FileInputStream fileinputstream = new FileInputStream(file);
            addFile(s, ((InputStream) (fileinputstream)));
            fileinputstream.close();
        }
    }

    public void addFile(String s, InputStream inputstream)
        throws IOException
    {
        if(names.put(s, "1") == null)
        {
            out.putNextEntry(new ZipEntry(s));
            getClass();
            byte abyte0[] = new byte[2048];
            for(int i = 0; (i = inputstream.read(abyte0, 0, 2048)) > 0;)
                out.write(abyte0, 0, i);

        }
    }

    public void close()
        throws IOException
    {
        out.close();
        names.clear();
    }
}
