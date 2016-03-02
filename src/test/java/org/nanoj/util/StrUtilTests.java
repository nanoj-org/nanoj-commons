package org.nanoj.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class StrUtilTests {
	
	@Test
	public void testRemoveEnd() {
		assertEquals("abc", StrUtil.removeEnd("abcd", "d"));
		assertEquals("abc", StrUtil.removeEnd("abc.foo", ".foo"));

		assertEquals("abc", StrUtil.removeEnd("abc", "zer"));
		assertEquals("abc", StrUtil.removeEnd("abc", "ab"));

		assertEquals("abc", StrUtil.removeEnd("abc", ""));
		assertEquals("abc", StrUtil.removeEnd("abc", null));

		assertEquals("", StrUtil.removeEnd("abc", "abc"));

		assertEquals("", StrUtil.removeEnd("", "abc"));
		assertEquals(null, StrUtil.removeEnd(null, "ee"));
		
		assertEquals("", StrUtil.removeEnd("", ""));
		assertEquals(null, StrUtil.removeEnd(null, null));
	}
	
	@Test
	public void testFirstCharLowerCase() {
		assertEquals("myClass", StrUtil.firstCharLowerCase("MyClass"));

		assertEquals("abcd", StrUtil.firstCharLowerCase("abcd"));
		assertEquals("aBCD", StrUtil.firstCharLowerCase("ABCD"));
		assertEquals("abCd", StrUtil.firstCharLowerCase("AbCd"));

		assertEquals("a", StrUtil.firstCharLowerCase("A"));
		assertEquals("a", StrUtil.firstCharLowerCase("a"));

		assertEquals(" ", StrUtil.firstCharLowerCase(" "));
		assertEquals(" ab", StrUtil.firstCharLowerCase(" ab"));

		assertEquals("a ", StrUtil.firstCharLowerCase("A "));
		assertEquals("a ", StrUtil.firstCharLowerCase("a "));
		
		assertEquals("", StrUtil.firstCharLowerCase(""));
		assertNull(StrUtil.firstCharLowerCase(null));
	}
	
	@Test
	public void testFirstCharUpperCase() {
		assertEquals("Abcd", StrUtil.firstCharUpperCase("abcd"));
		assertEquals("ABCD", StrUtil.firstCharUpperCase("ABCD"));
		assertEquals("AbCd", StrUtil.firstCharUpperCase("AbCd"));

		assertEquals("A", StrUtil.firstCharUpperCase("A"));
		assertEquals("A", StrUtil.firstCharUpperCase("a"));

		assertEquals(" ", StrUtil.firstCharUpperCase(" "));
		assertEquals(" ab", StrUtil.firstCharUpperCase(" ab"));

		assertEquals("A ", StrUtil.firstCharUpperCase("A "));
		assertEquals("A ", StrUtil.firstCharUpperCase("a "));
		
		assertEquals("", StrUtil.firstCharUpperCase(""));
		assertNull(StrUtil.firstCharUpperCase(null));
	}

	@Test
	public void test3() 
	{
	}
	

}
