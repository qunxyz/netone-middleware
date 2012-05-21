package  oe.netone.phone;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
public class UploadFile extends HttpServlet { 
    private static final long serialVersionUID = 5425836142860976977L;

    /** *//** 
      * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods. 
      * @param request servlet request 
      * @param response servlet response 
      */ 
     // 定义文件的上传路径 
    public String uploadPath; 
     // 限制文件的上传大小 
     private int maxPostSize = 100 * 1024 * 1024;  //最大100M
    public UploadFile() { 
        super(); 
     } 
     public void destroy() { 
         super.destroy(); 
     } 
     protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
             throws ServletException, IOException { 
         System.out.println("Access !"); 
         response.setContentType("text/html;charset=UTF-8"); 
         //保存文件到服务器中 
         DiskFileItemFactory factory = new DiskFileItemFactory(); 
        factory.setSizeThreshold(4096); 
        ServletFileUpload upload = new ServletFileUpload(factory); 
         upload.setHeaderEncoding("utf-8");
         upload.setSizeMax(maxPostSize); 
        try { 
           List fileItems = upload.parseRequest(request); 
            Iterator iter = fileItems.iterator(); 
            while (iter.hasNext()) { 
                FileItem item = (FileItem) iter.next(); 
                if (!item.isFormField()) { 
                	int i=(int) (Math.random()*1000);
                    String name = i+item.getName();
                    System.out.println(name); 
                   try { 
                	    uploadPath=System.getProperty("user.dir");
                	     String [] strings=uploadPath.split("bin");
                	    // strings[0]=strings[0].replace("\\","/");
                	     uploadPath=strings[0]+"webapps/netone/PhoneConfig/";
                	     System.out.println("---"+uploadPath);
                         item.write(new File(uploadPath + name)); 
                       response.getWriter().write("上传成功。");
                       response.setStatus(i);
                    } catch (Exception e) { 
                        e.printStackTrace(); 
                        response.getWriter().write(e.getMessage());
                        response.setStatus(700);
                    } 
                } 
            } 
        } catch (FileUploadException e) { 
            e.printStackTrace(); 
          response.getWriter().write(e.getMessage());
           System.out.println(e.getMessage() + "结束"); 
        } 
    } 

protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
        processRequest(request, response); 
    } 
    /** *//** 
     * Handles the HTTP <code>POST</code> method. 
     * @param request servlet request 
     * @param response servlet response 
     */ 
   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
             throws ServletException, IOException { 
        processRequest(request, response); 
     } 
    /** *//** 
      * Returns a short description of the servlet. 84      */ 
    public String getServletInfo() { 
    	return null;
    } 
} 