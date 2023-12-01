package com.tlv8.base.db.dynamic;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;

/**
 * 多数据源装饰器
 */
public class TLv8DataSourceProvider implements DynamicDataSourceProvider {
	final Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();

	public void addDataSource(String name, DataSource dataSource) {
		dataSourceMap.put(name, dataSource);
	}

	public void removeDataSource(String name, DataSource dataSource) {
		dataSourceMap.remove(name);
	}

	@Override
	public Map<String, DataSource> loadDataSources() {
		return dataSourceMap;
	}

}
