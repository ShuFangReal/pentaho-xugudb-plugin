package org.pentaho.di.database;

import org.pentaho.di.core.exception.KettleException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class XuguDatabaseMetaTest {
	//���Ի�ȡ����
	@Test
	public void testDriverClass(){
		XuguDatabaseMeta dbMeta = new XuguDatabaseMeta();
		assertEquals( "com.xugu.cloudjdbc.Driver", dbMeta.getDriverClass() );
	}
	
	//������װUrl
	@Test
	public void testGetUrl() throws KettleException{
		final String prefix = "jdbc:xugu://192.168.2.77:5138/testdb";
		XuguDatabaseMeta dbMeta = new XuguDatabaseMeta();
		// Test building url
		assertEquals( prefix, dbMeta.getURL( "192.168.2.77", "5138", "testdb"));
	}
}
