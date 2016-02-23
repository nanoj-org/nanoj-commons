package org.nanoj.util.tson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.nanoj.util.DateUtil;
import org.nanoj.util.tson.bean.BeanDate;
import org.nanoj.util.tson.bean.BeanPrimitive;
import org.nanoj.util.tson.bean.Color;
import org.nanoj.util.tson.bean.Data;
import org.nanoj.util.tson.bean.Equipement;
import org.nanoj.util.tson.bean.EquipementsAndColors;

public class TsonParserTest {

	static final String input1 = "{data: "
			+ " beanPrimitive = "
			+ " {prim: \n iii=123 sss=12 lll=45678 bbb=88 str = \"My String\" bool = true fff=0 ddd=232  } " 
			+ " beanDate = "
//			+ " {date: utilDate='2009-11-04' sqlDate='2005-05-25' sqlTime='2006-06-16' sqlTimestamp='2007-07-17' }" 
			+ " {date: utilDate='2009-11-04 12:30:20' sqlDate='2005-05-25 05:35:55' sqlTime='2006-06-16 06:46:56' sqlTimestamp='2007-07-17 17:37:57' }" 
			+ " } ";
	
	@Test
	public void test1() {
		System.out.println("-----");
		
		HashMap<String,Class<?>> map = new HashMap<>();
		map.put("data", Data.class );
//		map.put("alldata", AllData.class );
//		map.put("equipement", Equipement.class );
		map.put("prim", BeanPrimitive.class );
		map.put("date", BeanDate.class );
		
		
		Parser parser = new Parser( input1.getBytes(), map ) ;

		System.out.println("Input : ");
		System.out.println(input1);

		Object o = parser.parse();
		
		System.out.println("Object returned : ");
		System.out.println(o);

		assertTrue( o instanceof Data ) ;
		Data data = (Data) o ;
		assertNotNull( data.getBeanDate() );
		assertNull( data.getBeanDate().getStr() );
		assertEquals( "2009-11-04", DateUtil.dateISO(data.getBeanDate().getUtilDate()) );
		assertEquals( "12:30:20",   DateUtil.timeISO(data.getBeanDate().getUtilDate()) );

		assertNotNull( data.getBeanPrimitive() );
		assertEquals(0, data.getBeanPrimitive().getCcc());
		assertEquals("My String", data.getBeanPrimitive().getStr());
		assertEquals(123, data.getBeanPrimitive().getIii());
		assertTrue( data.getBeanPrimitive().isBool());
	}
	
	static final String input2 = "[ "
			+ "   {equipement: id=1 text=\"Equip AAA\\\"AA\" } "
			+ "   {equipement: id=2 text=\"Equip BBBB \" } "
			+ "]";
	
	@Test
	public void test2() {
		System.out.println("-----");
		
		HashMap<String,Class<?>> map = new HashMap<>();
		map.put("equipement", Equipement.class );
		
		Parser parser = new Parser( input2.getBytes(), map ) ;

		System.out.println("Input : ");
		System.out.println(input2);

		Object o = parser.parse();
		
		System.out.println("Object returned : " + o.getClass() );
		System.out.println(o);

		assertTrue( o instanceof List ) ;
		List<?> list = (List<?>) o ;
		assertEquals(2, list.size() );
		for ( Object item : list ) {
			System.out.println(" - item : " + item.getClass().getName() + " : " + item );
		}
	}

	static final String input3 = "{ equipements_and_colors: "
			+ " equipements = ["
			+ "   {equipement: id=1 text=\"Equip 1AAA\" } "
			+ "   {equipement: id=2 text=\"Equip 2BBB\" } "
			+ "   {equipement: id=3 text=\"Equip 3CCC\" } "
			+ "   ]"
			+ " colors = ["
			+ "   {color: id=\"c1\" available=true }  "
			+ "   {color: id=\"c2\" available=false }  "
			+ "   ]"
			+ " } ";

	// TODO : Typed list ?
	// [type: "list of type"
	// [$: "list of values"
	/*
	static final String input3 = "{ equipements_and_colors: "
			+ " equipements = [equipement:"
			+ "   {id=1 text=\"Equip 1AAA\" } "
			+ "   {id=2 text=\"Equip 2BBB\" } "
			+ "   {id=3 text=\"Equip 3CCC\" } "
			+ "   ]"
			+ " colors = [color:"
			+ "   {id=\"c1\" available=true }  "
			+ "   {id=\"c2\" available=false }  "
			+ "   ]"
			+ " } ";
	*/
	@Test
	public void test3() {
		System.out.println("-----");
		
		HashMap<String,Class<?>> map = new HashMap<>();
		map.put("equipements_and_colors", EquipementsAndColors.class );
		map.put("equipement", Equipement.class );
		map.put("color", Color.class );
		
		Parser parser = new Parser( input3.getBytes(), map ) ;

		System.out.println("Input : ");
		System.out.println(input3);

		Object o = parser.parse();
		
		System.out.println("Object returned : " + o.getClass() );
		System.out.println(o);

		assertTrue( o instanceof EquipementsAndColors ) ;
		EquipementsAndColors equipementsAndColors = (EquipementsAndColors) o;
		assertNotNull(equipementsAndColors.getEquipements());
		assertEquals(3, equipementsAndColors.getEquipements().size());
		
		assertNotNull(equipementsAndColors.getColors());
	}
	
//	// Typed list for "values" 
//	static final String input4 = "[$:"
//			+ " \"AAAA\"  "
//			+ " \"BBB\" "
//			+ "]";
//	
//	@Test
//	public void test4() {
//		System.out.println("-----");
//		
//		HashMap<String,Class<?>> map = new HashMap<>();
//		//map.put("equipement", Equipement.class );
//		
//		Parser parser = new Parser( input4.getBytes(), map ) ;
//
//		System.out.println("Input : ");
//		System.out.println(input4);
//
//		Object o = parser.parse();
//		
//		System.out.println("Object returned : " + o.getClass() );
//		System.out.println(o);
//
//		assertTrue( o instanceof List ) ;
//		List<?> list = (List<?>) o ;
//		assertEquals(2, list.size() );
//		for ( Object item : list ) {
//			System.out.println(" - item : " + item.getClass().getName() + " : " + item );
//		}
//	}
//

}
