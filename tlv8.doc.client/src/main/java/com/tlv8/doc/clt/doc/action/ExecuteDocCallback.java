package com.tlv8.doc.clt.doc.action;

import java.io.InputStream;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class ExecuteDocCallback
  implements ExecuteCallback
{
  public Object execute(InputStream paramInputStream, String paramString)
  {
    Document localDocument = null;
    try
    {
      SAXReader localSAXReader = new SAXReader();
      InputStream localInputStream = paramInputStream;
      localDocument = localSAXReader.read(localInputStream);
      localInputStream.close();
    }
    catch (Exception localException)
    {
      new ActionExecuteException(localException.getMessage(), localException);
    }
    return localDocument;
  }
}
