package com.rongji.webframework.taglib.html;

import com.rongji.webframework.taglib.UITag;
import com.rongji.webframework.util.TagUtil;
import javax.servlet.jsp.JspException;

public class ContextPathTag extends UITag
{
  private String format;

  public String getFormat()
  {
    return this.format;
  }

  public void setFormat(String paramString)
  {
    this.format = paramString;
  }

  public int doBeginTag()
    throws JspException
  {
    String str = TagUtil.getContenxtPath(this.pageContext);
    System.out.println(str);
    if (this.format != null)
      str = this.format.replaceAll("\\{0\\}", str);
    TagUtil.write(this.pageContext, str);
    return super.doStartTag();
  }
}