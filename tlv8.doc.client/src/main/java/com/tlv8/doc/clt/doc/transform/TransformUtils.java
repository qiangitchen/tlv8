package com.tlv8.doc.clt.doc.transform;

import org.dom4j.Element;

import com.tlv8.doc.clt.doc.ModelException;
import com.tlv8.doc.clt.doc.Utils;

public class TransformUtils
{
  public static final String JSON_CONTENT_TYPE = "application/json";
  public static final String XML_CONTENT_TYPE = "application/xml";
  public static final String RESPONSE_TYPE_DATA_NAME = "@response_data_type";
  public static final String REQUEST_TYPE_DATA_NAME = "@request_data_type";

  public static TransformType getTranslateType(Element paramElement)
  {
    try {
		Utils.checkNotExist("转换层参数", paramElement);
	} catch (ModelException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    String str = paramElement.attributeValue("data-type");
    return getTranslateType(str);
  }

  public static TransformType getTranslateType(String paramString)
  {
    TransformType localTransformType = (TransformType) ("row-tree".equalsIgnoreCase(paramString) ? TransformType.ROW_TREE : TransformType.ROW_LIST);
    return localTransformType;
  }


  public static boolean isJson(String paramString)
  {
    return (null != paramString) && (-1 < paramString.indexOf("application/json"));
  }

  public static boolean isXml(String paramString)
  {
    return (null != paramString) && (-1 < paramString.indexOf("application/xml"));
  }

public static String transToType(String string) {
	return null;
}

}
