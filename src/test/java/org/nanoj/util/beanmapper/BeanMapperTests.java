package org.nanoj.util.beanmapper;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.nanoj.util.beanmapper.beans.AllTypes;
import org.nanoj.util.beanmapper.beans.Book;

public class BeanMapperTests {

	private static BeanMapper mapper =  new BeanMapper() ;
	
	@Test
	public void testBook1() 
	//public static void test1(Properties map) 
	{
		//Map<String,String> map = new HashMap<String,String>();
		Map<String,String> map = new HashMap<String,String>();
		Map<String,String> map2 = new HashMap<String,String>();
		
		
		map.put("isbn", "1-2222-5555-66");
		map.put("title", "The title");
		map.put("price", "123.45");
		map.put("quantity", "3");
		
		Book book = new Book();
		
		mapper.mapToBean(map, book);

		System.out.println("=====");
		System.out.println("Book : " + book );
		
		assertEquals("1-2222-5555-66", book.getIsbn());
		assertEquals("The title", book.getTitle());
		assertEquals(123.45, book.getPrice(), 0.0001);
		assertEquals(new Integer(3), book.getQuantity() );
		
		
		mapper.beanToMap(book, map2);
		assertEquals(book.getIsbn(), map2.get("isbn"));
		assertEquals(book.getTitle(), map2.get("title"));
		assertEquals(""+book.getQuantity(), map2.get("quantity"));
		assertEquals("123.45", map2.get("price"));
		
		//BeanMapper.mapToBean(null, book);
		//BeanMapper.mapToBean(map, null);
	}

	@Test
	public void test2() 
	//public static void test2(Properties map, Properties map2) 
	{
		Map<String,String> map = new HashMap<String,String>();
		Map<String,String> map2 = new HashMap<String,String>();
		
		map.put("bool1", "true");
		map.put("bool2", "true");
		
		map.put("name", "My bean name");
		
		map.put("byte1", "11");
		map.put("byte2", "22");
		
		map.put("short1", "111");
		map.put("short2", "222");

		//map.put("int1", "11111"); // Integer 
		map.put("int1", " 12 "); // Integer  : OK --> null
		
		//map.put("int2", null); // int : OK --> not set
		//map.put("int2", "aa"); // int : ERROR IllegalArgumentException / invokeSetter
		//map.put("int2", "22222"); // int : OK

		map.put("long1", "11111111");
		map.put("long2", "22222222");
		
		map.put("float1", "33333.333");
		map.put("float2", "44444.444");
		
		map.put("double1", "555555.5555");
		map.put("double2", "666666.6666");

		map.put("bigDecimal1", "77777777.7777");
		map.put("bigInteger1", "99999999");

		//map.put("utilDate", "2011-12-01");
		//map.put("utilDate", "23:55:43");
		map.put("utilDate", "2011-12-01 23:55:43");
		
		map.put("sqlDate",  "2011-12-31");
		map.put("sqlTime",  "23:55:43");
		map.put("sqlTimestamp",  "2011-12-31 23:55:43");

		map.put("inex", "2333");
		
		AllTypes bean = new AllTypes();
		
		BeanMapper mapper =  new BeanMapper() ;
		mapper.mapToBean(map, bean);

		System.out.println("=====");
		System.out.println("Bean : " + bean );
		
		//Map<String,String> map2 = new HashMap<String,String>();

		mapper.beanToMap(bean, map2);
		
		System.out.println("MAP : " + map );
		//BeanMapper.mapToBean(null, book);
		//BeanMapper.mapToBean(map, null);
	}
	
	@Test
	public void test3() 
	{
		Properties prop = new Properties();
		
		Book book = new Book();
		book.setId(1);
		book.setTitle("Germinal");
		book.setAuthorId(123);
		book.setBestSeller( (short)2 );
		book.setPrice(23.45);
		book.setQuantity(5);
		book.setAvailability((short)1);
		book.setIsbn("1-2222-5555-66");
		
		mapper.beanToMap(book, prop);
		
		try { 
			FileOutputStream fos = new FileOutputStream("D:/TMP/Tests/Book1.properties");
			prop.store(fos, "Book instance");
			fos.close();
			System.out.println("Book SAVED : " + book );
		} 
		catch(IOException ex ){
			System.out.println("ERROR : Cannot save properties.");
		}
		
		System.out.println("=====");
		
		Properties p2 = new Properties();
		try {
			FileInputStream fis = new FileInputStream("D:/TMP/Tests/Book1.properties");
			p2.load(fis);
			fis.close();
			System.out.println("Book properties LOADED." );
		}
		catch(IOException ex ){
			System.out.println("ERROR : Cannot load properties.");
		}
		
		Book book2 = new Book();
		mapper.mapToBean(p2, book2);
		System.out.println("Book 2 : " + book2 );
	}
	

}
