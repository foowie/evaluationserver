package evaluationserver.server.util;

import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TagsTest {
	
	public TagsTest() {
	}
	
	@Before
	public void setUp() {
	}

	@Test(expected=NullPointerException.class)
	public void testAddNullValue() {
		Tags<Integer, Integer> instance = new Tags<Integer, Integer>();
		instance.add(null, 5);
	}
	@Test(expected=NullPointerException.class)
	public void testAddNullTag() {
		Tags<Integer, Integer> instance = new Tags<Integer, Integer>();
		instance.add(5, null);
	}
	@Test
	public void testAdd() {
		Tags<Integer, Integer> instance = new Tags<Integer, Integer>();
		instance.add(1, 1);
		assertTrue(instance.containsValue(1));
		assertTrue(instance.containsTag(1));
		assertFalse(instance.containsValue(0));
		assertFalse(instance.containsTag(2));
	}
	@Test
	public void testAddMulti() {
		Tags<Integer, Integer> instance = new Tags<Integer, Integer>();
		instance.add(1, 1);
		instance.add(1, 1);
		assertTrue(instance.containsValue(1));
		assertTrue(instance.containsTag(1));
		assertFalse(instance.containsValue(0));
		assertFalse(instance.containsTag(2));
	}

	@Test
	public void testContainsValue() {
		Tags<Integer, Integer> instance = new Tags<Integer, Integer>();
		instance.add(1, 3);
		instance.add(6, 7);
		assertTrue(instance.containsValue(1));
		assertTrue(instance.containsValue(6));
		assertFalse(instance.containsValue(0));
		assertFalse(instance.containsValue(2));
		assertFalse(instance.containsValue(5));
		assertFalse(instance.containsValue(7));
	}

	@Test
	public void testContainsTag() {
		Tags<Integer, Integer> instance = new Tags<Integer, Integer>();
		instance.add(2, 1);
		instance.add(5, 6);
		assertTrue(instance.containsTag(1));
		assertTrue(instance.containsTag(6));
		assertFalse(instance.containsTag(0));
		assertFalse(instance.containsTag(2));
		assertFalse(instance.containsTag(5));
		assertFalse(instance.containsTag(7));
	}

	@Test
	public void testGetTagsByValue() {
		Tags<Integer, Integer> instance = new Tags<Integer, Integer>();
		instance.add(1, 2).add(3, 4).add(1, 6);
		Set<Integer> tags = instance.getTagsByValue(1);
		assertEquals(2, tags.size());
		assertTrue(tags.contains(2));
		assertTrue(tags.contains(6));
	}

	@Test
	public void testGetValuesByTag() {
		Tags<Integer, Integer> instance = new Tags<Integer, Integer>();
		instance.add(2, 1).add(4, 3).add(6, 1);
		Set<Integer> values = instance.getValuesByTag(1);
		assertEquals(2, values.size());
		assertTrue(values.contains(2));
		assertTrue(values.contains(6));
	}

	@Test
	public void testRemoveByTag() {
		Tags<Integer, Integer> instance = new Tags<Integer, Integer>();
		instance.add(2, 1).add(4, 3).add(6, 1).add(8, 1).add(8, 3);
		Set<Integer> values = instance.removeByTag(1);
		assertEquals(2, values.size());
		assertTrue(values.contains(2));
		assertTrue(values.contains(6));
		assertFalse(instance.containsValue(2));
		assertTrue(instance.containsValue(4));
		assertFalse(instance.containsValue(6));
		assertTrue(instance.containsValue(8));
		assertTrue(instance.containsTag(3));
		assertFalse(instance.containsTag(1));
	}

	@Test
	public void testRemoveByValue() {
		Tags<Integer, Integer> instance = new Tags<Integer, Integer>();
		instance.add(1, 2).add(3, 4).add(1, 6).add(2, 6);
		Set<Integer> tags = instance.removeByValue(1);
		assertEquals(1, tags.size());
		assertTrue(tags.contains(2));
		assertFalse(instance.containsTag(2));
		assertTrue(instance.containsTag(6));
		assertTrue(instance.containsTag(4));
		assertTrue(instance.containsValue(3));
		assertTrue(instance.containsValue(2));
		assertFalse(instance.containsValue(1));
	}
}
