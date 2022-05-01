package test.db;

import java.util.UUID;

import com.tlv8.base.db.DBUtils;

public class Test {
	public static void main(String[] args) {
		System.out.println(DBUtils.IsMSSQLDB("system"));
		System.out.println(DBUtils.IsOracleDB("system"));
		System.out.println(DBUtils.IsMySQLDB("system"));
		try {
			DBUtils.executeCommand("system", "create table test_1(id varchar(36) not null,name varchar(100),remark varchar(1000))");
			DBUtils.executeCommand("system", "alter table test_1 add constraint pk_test_1 primary key (id)");
			DBUtils.excuteInsert("system", "insert into test_1(id,name,remark)values('"+UUID.randomUUID()+"','xiaogao','哇哈哈哈')");
			System.out.println(DBUtils.selectStringList("system", "select * from test_1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
