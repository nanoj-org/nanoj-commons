package org.nanoj.util.beanmapper;

import java.util.Map;
import java.util.Properties;

/* package */ class BeanProperties {

	private Map<String, String> map = null ;
	private Properties properties = null ;
	
	public BeanProperties(Map<String, String> map) {
		super();
		this.map = map;
	}

	public BeanProperties(Properties propterties) {
		super();
		this.properties = propterties;
	}

	public void set(String name, String value) {
		if  ( this.properties != null ) {
			properties.put(name, value);
		}
		else if  ( this.map != null ) {
			map.put(name, value);
		}
		else {
			throw new RuntimeException("Map and Properties are null");
		}
	}
	
	public String get(String name) {
		if  ( this.properties != null ) {
			return (String) properties.get(name);
		}
		else if  ( this.map != null ) {
			return map.get(name);
		}
		else {
			throw new RuntimeException("Map and Properties are null");
		}
	}
}
