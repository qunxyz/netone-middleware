
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 

package oe.cav.web.util;

import java.io.*;
import java.util.zip.*;

public class ZipUtil
{
	
	
	public static void main(String []arg) throws Exception{
//		try {
//			unzip("c:/a.zip","c:/");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//Ñ¹ËõÎÄ¼þ¼Ð
		zip("c:/abc.zip",new File("c:/xxxxx"),true);
	}

    public ZipUtil()
    {
    }

    public static void zip(String s, String s1)
        throws Exception
    {
        zip(s, s1, false);
    }

    public static void zip(String s, String s1, boolean flag)
        throws Exception
    {
        zip(s, new File(s1), flag);
    }

    public static void zip(String s, File file, boolean flag)
        throws Exception
    {
        ZipOutputStream zipoutputstream = new ZipOutputStream(new FileOutputStream(s));
        zip(zipoutputstream, file, "", flag);
        zipoutputstream.close();
    }

    public static void zip(String s, File file)
        throws Exception
    {
        zip(s, file, false);
    }

    public static void unzip(String s, String s1)
        throws Exception
    {
        ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream(s));
        ZipEntry zipentry;
        while((zipentry = zipinputstream.getNextEntry()) != null) 
        {
            String s2 = zipentry.getName();
            String s3 = s1 + "/" + s2.substring(0, s2.lastIndexOf(47) + 1);
            if(zipentry.isDirectory())
            {
                File file = new File(s3);
                file.mkdirs();
            } else
            {
                File file1 = new File(s3);
                if(!file1.exists())
                    file1.mkdirs();
                file1 = new File(s1 + "/" + zipentry.getName());
                file1.createNewFile();
                FileOutputStream fileoutputstream = new FileOutputStream(file1);
                int i;
                while((i = zipinputstream.read()) != -1) 
                    fileoutputstream.write(i);
                fileoutputstream.close();
            }
        }
        zipinputstream.close();
    }

    private static void zip(ZipOutputStream zipoutputstream, File file, String s, boolean flag)
        throws Exception
    {
        if(file.isDirectory())
        {
            File afile[] = file.listFiles();
            zipoutputstream.putNextEntry(new ZipEntry(s + "/"));
            s = s.length() != 0 ? s + "/" : "";
            for(int i = 0; i < afile.length; i++)
                zip(zipoutputstream, afile[i], s + afile[i].getName(), flag);

        } else
        {
            zipoutputstream.putNextEntry(new ZipEntry(s));
            FileInputStream fileinputstream = new FileInputStream(file);
            int j;
            while((j = fileinputstream.read()) != -1) 
                zipoutputstream.write(j);
            fileinputstream.close();
        }
        if(flag)
            file.delete();
    }
}
