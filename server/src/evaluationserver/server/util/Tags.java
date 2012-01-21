package evaluationserver.server.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Tags<Value, Tag> {
	
	private Map<Value, Set<Tag>> values = new HashMap<Value, Set<Tag>>();
	private Map<Tag, Set<Value>> tags = new TreeMap<Tag, Set<Value>>();
	
	/**
	 * Add new value and assign tag
	 * If value exists, only assign (new) tag
	 * @param value
	 * @param tag
	 * @return this
	 */
	public Tags<Value, Tag> add(Value value, Tag tag) {
		if(value == null || tag == null)
			throw new NullPointerException("Value and Tag can't be null !");
		
		if(!values.containsKey(value))
			values.put(value, new TreeSet<Tag>());
		if(!tags.containsKey(tag))
			tags.put(tag, new HashSet<Value>());
		
		if(!values.get(value).contains(tag))
			values.get(value).add(tag);
		if(!tags.get(tag).contains(value))
			tags.get(tag).add(value);
		
		return this;
	}
	
	public boolean containsValue(Value value) {
		return values.containsKey(value);
	}
	
	public boolean containsTag(Tag tag) {
		return tags.containsKey(tag);
	}
	
	public Set<Tag> getTagsByValue(Value value) {
		return values.get(value);
	}
	
	public Set<Value> getValuesByTag(Tag tag) {
		return tags.get(tag);
	}
	
	/**
	 * Remove all values, that have only tag 'tag'
	 * @param tag
	 * @return Set of removed values
	 */
	public Set<Value> removeByTag(Tag tag) {
		if(!tags.containsKey(tag))
			return new TreeSet<Value>();
		Set<Value> removed = new HashSet<Value>();
		for(Value value : tags.get(tag)) {
			if(values.get(value).size() == 1) {
				removed.add(value);
				values.remove(value);
			} else
				values.get(value).remove(tag);
		}
		tags.remove(tag);
		return removed;
	}
	
	/**
	 * Remove value and all assigned tags
	 * If tag does contains only this value, its removed
	 * @param value
	 * @return Set of removed tags
	 */
	public Set<Tag> removeByValue(Value value) {
		if(!values.containsKey(value))
			return new TreeSet<Tag>();
		
		Set<Tag> removed = new TreeSet<Tag>();
		for(Tag tag : values.get(value)) {
			if(tags.get(tag).size() == 1) {
				removed.add(tag);
				tags.remove(tag);
			} else
				tags.get(tag).remove(value);
		}
		values.remove(value);
		return removed;
	}
	
}
