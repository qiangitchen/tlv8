package com.tlv8.doc.clt.doc.transform;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Category;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;

import com.tlv8.doc.clt.doc.DocPermission;
import com.tlv8.doc.clt.doc.Docinfo;
import com.tlv8.doc.clt.doc.ModelException;
import com.tlv8.doc.clt.doc.Utils;

public class Table2Row {
	public Element transform(Docinfo docinfo,
			TransformConfig paramTransformConfig) throws ModelException {
		if (Utils.isNull(paramTransformConfig))
			paramTransformConfig = new TransformConfig();
		Element localElement1 = DocumentHelper
				.createElement(TransformConstants.ROWS_QNAME);
		String str1 = paramTransformConfig.getRowsConfig().getTreeVRoot();
		String str2 = paramTransformConfig.getRowsConfig().getTreeVRootLabel();
		Element localElement2 = localElement1;
		if ((Utils.isNotEmptyString(str1)) && (Utils.isNotEmptyString(str2))) {
			localElement2 = DocumentHelper
					.createElement(TransformConstants.ROW_QNAME);
			localElement2.addAttribute(TransformConstants.ID_QNAME, str1);
			localElement2.addElement(
					!paramTransformConfig.isCellNameByRelation() ? "cell"
							: getFirstColumnName(docinfo,
									paramTransformConfig)).setText(str2);
			localElement1.add(localElement2);
		}
		if (Utils.isNotNull(docinfo)) {
			if (Utils.isNotEmptyString(paramTransformConfig.getRowsConfig()
					.getTreeParent()))
				localElement1.addAttribute("parent", paramTransformConfig
						.getRowsConfig().getTreeParent());
			if (Utils.isNotEmptyString(paramTransformConfig.getRowsConfig()
					.getTreeSpreadParentRelation()))
				localElement1.addAttribute("tree-parent-relation",
						paramTransformConfig.getRowsConfig()
								.getTreeSpreadParentRelation());
			a(docinfo, paramTransformConfig);
			if (TransformType.ROW_LIST.equals(paramTransformConfig
					.getRowsConfig().getDataType()))
				b(docinfo, paramTransformConfig, localElement2);
			else if (TransformType.ROW_TREE.equals(paramTransformConfig
					.getRowsConfig().getDataType()))
				c(docinfo, paramTransformConfig, localElement2);
		}
		return localElement1;
	}

	@SuppressWarnings("unused")
	private void a(List<?> paramTable, TransformConfig paramTransformConfig) {
		// TODO Auto-generated method stub
		
	}

	private void c(Docinfo docinfo, TransformConfig paramTransformConfig,
			Element localElement2) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unused")
	private Element a(String paramTable, TransformConfig paramTransformConfig) {
		Utils.checkNotEmptyString("User data name", paramTable,
				ResponseTransform.logger);
		Element localElement = DocumentHelper.createElement("userdata");
		localElement.addAttribute(TransformConstants.NAME_QNAME, paramTable);
		return localElement;
	}

	@SuppressWarnings("rawtypes")
	public static String getFirstColumnName(Docinfo docinfo,
			TransformConfig paramTransformConfig) throws ModelException {
		if (Utils.isNotEmptyString(paramTransformConfig.getRowsConfig()
				.getSequence()))
			return paramTransformConfig.getRowsConfig().getSequence()
					.split(",")[0];
		TableMetaData localTableMetaData = ((Docinfo) docinfo).getMetaData();
		Object localObject = localTableMetaData.getColumnMetaDatas().iterator();
		if (((Iterator) localObject).hasNext()) {
			ColumnMetaData localColumnMetaData = (ColumnMetaData) ((Iterator) localObject)
					.next();
			return localColumnMetaData.getName();
		}
		localObject = "Table没有定义Column";
		throw new ModelException(localObject);
	}

	private Element a(Docinfo paramRow, TransformConfig paramTransformConfig) throws ModelException
  {
    Element localElement = DocumentHelper.createElement(TransformConstants.ROW_QNAME);
    TableMetaData localTableMetaData = paramRow.getTable().getMetaData();
    paramRow.getTable().getProperties();
	String str1 = QName.get("sys.rowid").toString();
    if ((paramTransformConfig.isTransformIdColumn()) && (Utils.isNotEmptyString(str1)) && (!localTableMetaData.containsColumn(str1)))
    {
      String localObject1 = "Table idColumn[" + str1 + "]不存在";
      ((Category) ResponseTransform.logger).error(localObject1);
      throw new ModelException((String)localObject1);
    }
    if ((paramTransformConfig.isTransformIdColumn()) && (Utils.isNotEmptyString(str1)))
    {
      localElement.addAttribute(TransformConstants.ID_QNAME, SimpleTransform.transToString(paramRow.getValue(str1)));
      if (paramRow.isModified(str1))
      {
        localElement.add(a("rowid-changed", "1"));
        localElement.addAttribute("original-id", SimpleTransform.transToString(paramRow.getOldValue(str1)));
      }
    }
    Object localObject1 = a("recordState", TransformUtils.transToType(paramRow.getState()));
    localElement.add((Element)localObject1);
    return (Element)(Element)localElement;
  }

	private Attribute a(String paramTable, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	private void b(Docinfo docinfo, TransformConfig paramTransformConfig,
			Element paramElement) throws ModelException {
		Iterator localIterator = docinfo.iterator();
		while (localIterator.hasNext()) {
			Docinfo localRow = (Docinfo) localIterator.next();
			Element localElement = a(localRow, paramTransformConfig);
			paramElement.add(localElement);
		}
	}

	public Attribute transform(Map<String, DocPermission> map,
			TransformConfig arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}