package com.tlv8.doc.clt.doc.action;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.methods.multipart.PartSource;

public class InputStreamPartSource
  implements PartSource
{
  private InputStream in = null;
  private String fileName = null;

  public InputStreamPartSource(InputStream paramInputStream, String paramString)
  {
    this.in = paramInputStream;
    this.fileName = paramString;
  }

  public InputStream createInputStream()
    throws IOException
  {
    return this.in;
  }

  public String getFileName()
  {
    return this.fileName;
  }

  public long getLength()
  {
    try
    {
      return this.in.available();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return -1L;
  }
}
