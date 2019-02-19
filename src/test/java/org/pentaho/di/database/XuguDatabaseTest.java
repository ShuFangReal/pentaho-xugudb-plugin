package org.pentaho.di.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.di.core.KettleClientEnvironment;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LoggingObject;
import org.pentaho.di.core.plugins.DatabasePluginType;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.row.RowMetaInterface;

public class XuguDatabaseTest {
	@BeforeClass
	public static void setUpOnce() throws KettleException{
		DatabasePluginType dbPluginType = (DatabasePluginType)PluginRegistry.getInstance().getPluginType( DatabasePluginType.class );
	    dbPluginType.registerCustom( XuguDatabaseMeta.class, null, "Xugu", "Xugu", null, null );
	    KettleClientEnvironment.init();
	}
	
	//���Զ�ȡ����
	@Test
	public void testReadDataIT() throws KettleDatabaseException, SQLException{
		XuguDatabaseMeta xuguMeta = new XuguDatabaseMeta();
		xuguMeta.setPluginId("Xugu");
		DatabaseMeta dbMeta = new DatabaseMeta();
		//����Ԫ������Ϣ
		dbMeta.setDatabaseInterface(xuguMeta);
		dbMeta.setHostname("192.168.2.77");
		dbMeta.setUsername("SYSDBA");
		dbMeta.setPassword("SYSDBA");
		dbMeta.setDBPort("5138");
		dbMeta.setDBName("testDB");
		System.out.println("TTTTTTTTTest connect "+dbMeta.getAttributes()+" "+dbMeta.getURL());
	
		//�������ݿ�
		Database db = new Database(new LoggingObject(this), dbMeta);
		db.connect();
		
		//��ȡ���Խ����
		ResultSet result = db.openQuery("SELECT * FROM TESTTABLE4");
		
		//�������
		assertNotNull(result);
		Object[] row = db.getRow(result);
		RowMetaInterface meta = db.getMetaFromRow(row, result.getMetaData());
		assertNotNull(row);
		assertNotNull(meta);
		assertEquals(2, meta.size());
		assertEquals(0L, row[0]);
		assertEquals("TOM", row[1]);
		System.out.println("row1 "+row[0]+" "+row[1]);
		
		//����Сд
		row = db.getRow(result);
		assertNotNull(row);
		assertEquals(1L, row[0]);
		assertEquals("JAMEs", row[1]);
		System.out.println("row2 "+row[0]+" "+row[1]);
		
		row = db.getRow(result);
		assertNotNull(row);
		assertEquals(2L, row[0]);
		assertEquals("PAUL", row[1]);
		System.out.println("row3 "+row[0]+" "+row[1]);
		
		row = db.getRow(result);
		assertNotNull(row);
		System.out.println("row4 "+row[0]+" "+row[1]);
		
		row = db.getRow(result);
		assertNull(row);
		
		//�������Ա�
		//db.execStatement("Create table kettle_testTable(id ");
		
		//����ֶδ�Сд������ӳ��
		String[] namesAndTypes = meta.getFieldNamesAndTypes(20);
		for(int i=0; i<namesAndTypes.length; i++) {
			System.out.println("col"+i+" "+namesAndTypes[i]);
		}
		db.disconnect();
	}
}
