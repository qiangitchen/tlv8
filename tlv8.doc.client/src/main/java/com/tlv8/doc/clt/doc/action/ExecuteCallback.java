package com.tlv8.doc.clt.doc.action;

import java.io.InputStream;

public abstract interface ExecuteCallback
{
  public abstract Object execute(InputStream paramInputStream, String paramString);
}
