package com.tlv8.doc.clt.doc.action;

import java.io.ByteArrayInputStream;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class RequestAction
{
  private String action = null;
  private String contentType = null;
  private String accept = null;

  public RequestAction(String paramString1, String paramString2, String paramString3)
  {
    this.action = paramString1;
    this.contentType = paramString2;
    this.accept = paramString3;
  }

  public String getAction()
  {
    return this.action;
  }

  public void setAction(String paramString)
  {
    this.action = paramString;
  }

  public String getContentType()
  {
    return this.contentType;
  }

  public void setContentType(String paramString)
  {
    this.contentType = paramString;
  }

  public String getAccept()
  {
    return this.accept;
  }

  public void setAccept(String paramString)
  {
    this.accept = paramString;
  }

  public boolean hasAction()
  {
    return (null != this.action) && (!"".equals(this.action));
  }

  public Document getActionDoc()
    throws Exception
  {
    if ((null != this.action) && (!"".equals(this.action)))
    {
      SAXReader localSAXReader = new SAXReader();
      localSAXReader.setEncoding("UTF-8");
      return localSAXReader.read(new ByteArrayInputStream(this.action.getBytes("UTF-8")));
    }
    return null;
  }
}
