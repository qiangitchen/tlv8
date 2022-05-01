package com.tlv8.doc.clt.doc.transform;

import java.util.Map;
import org.dom4j.Element;

public class TransformConfig
{
  private RowsConfig jdField_a_of_type_ComJustepSystemTransformRowsConfig = new RowsConfig();
  private boolean jdField_a_of_type_Boolean = true;
  private boolean jdField_b_of_type_Boolean = true;
  private boolean c = false;
  private String jdField_a_of_type_JavaLangString = null;
  private String jdField_b_of_type_JavaLangString = null;

@SuppressWarnings("rawtypes")
public static TransformConfig createTransformConfig(Map<String, Object> paramMap, String paramString1, String paramString2)
  {
    TransformConfig localTransformConfig = new TransformConfig();
    localTransformConfig.jdField_a_of_type_JavaLangString = paramString1;
    localTransformConfig.jdField_b_of_type_JavaLangString = paramString2;
    if (null != paramMap)
    {
      localTransformConfig.jdField_a_of_type_Boolean = ((Boolean)paramMap.get("transformIdcolumn")).booleanValue();
      localTransformConfig.jdField_b_of_type_Boolean = ((Boolean)paramMap.get("useNamespace")).booleanValue();
      localTransformConfig.c = ((Boolean)paramMap.get("cellnameByRelation")).booleanValue();
      if (paramMap.containsKey("rowsConfig"))
      {
        Map localMap1 = (Map)paramMap.get("rowsConfig");
        RowsConfig localRowsConfig = localTransformConfig.jdField_a_of_type_ComJustepSystemTransformRowsConfig;
        localRowsConfig.setDataType(TransformUtils.getTranslateType((String)paramMap.get("dataType")));
        localRowsConfig.setConcept((String)localMap1.get("concept"));
        localRowsConfig.setSequence((String)localMap1.get("sequence"));
        if (localMap1.containsKey("treeOption"))
        {
          Map localMap2 = (Map)localMap1.get("treeOption");
          localRowsConfig.setTreeParent((String)localMap2.get("parent"));
          localRowsConfig.setTreeSpreadParentRelation((String)localMap2.get("treeParentRelation"));
          localRowsConfig.setTreeVRoot((String)localMap2.get("virtualRoot"));
          localRowsConfig.setTreeVRootLabel((String)localMap2.get("virtual-root-label"));
        }
        localRowsConfig.validate();
      }
    }
    return localTransformConfig;
  }

  public static TransformConfig createTransformConfig(Element paramElement, String paramString1, String paramString2)
  {
    TransformConfig localTransformConfig = new TransformConfig();
    localTransformConfig.jdField_a_of_type_JavaLangString = paramString1;
    localTransformConfig.jdField_b_of_type_JavaLangString = paramString2;
    if (null != paramElement)
    {
      localTransformConfig.jdField_a_of_type_Boolean = (!"false".equalsIgnoreCase(paramElement.attributeValue(TransformConstants.TRANSFORM_IDCOLUMN_QNAME)));
      localTransformConfig.jdField_b_of_type_Boolean = (!"false".equalsIgnoreCase(paramElement.attributeValue(TransformConstants.USE_NAMESPACE_QNAME)));
      localTransformConfig.c = "true".equalsIgnoreCase(paramElement.attributeValue(TransformConstants.TRANSFORM_CELLNAME_USE_RELATION_QNAME));
      Element localElement1 = paramElement.element(TransformConstants.ROWS_CONFIG_QNAME);
      if (localElement1 != null)
      {
        Element localElement2 = (Element)localElement1.clone();
        RowsConfig localRowsConfig = localTransformConfig.jdField_a_of_type_ComJustepSystemTransformRowsConfig;
        localRowsConfig.setDataType(TransformUtils.getTranslateType(paramElement));
        localRowsConfig.setConcept(localElement2.attributeValue("concept"));
        localRowsConfig.setSequence(localElement2.attributeValue("sequence"));
        Element localElement3 = localElement2.element("tree-option");
        if (localElement3 != null)
        {
          localRowsConfig.setTreeParent(localElement3.attributeValue("parent"));
          localRowsConfig.setTreeSpreadParentRelation(localElement3.attributeValue("tree-parent-relation"));
          localRowsConfig.setTreeVRoot(localElement3.attributeValue("virtual-root"));
          localRowsConfig.setTreeVRootLabel(localElement3.attributeValue("virtual-root-label"));
        }
        localRowsConfig.validate();
      }
    }
    return localTransformConfig;
  }

  public RowsConfig getRowsConfig()
  {
    return this.jdField_a_of_type_ComJustepSystemTransformRowsConfig;
  }

  public boolean isTransformIdColumn()
  {
    return this.jdField_a_of_type_Boolean;
  }

  public boolean isUseNamespace()
  {
    return this.jdField_b_of_type_Boolean;
  }

  public String getResponseDataType()
  {
    return this.jdField_a_of_type_JavaLangString;
  }

  public boolean isCellNameByRelation()
  {
    return this.c;
  }

  public String getActionFlag()
  {
    return this.jdField_b_of_type_JavaLangString;
  }

public String trim() {
	return this.toString().trim();
}
}
