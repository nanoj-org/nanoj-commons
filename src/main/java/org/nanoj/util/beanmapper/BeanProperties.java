/**
 *  Copyright (C) 2013-2016 Laurent GUERIN - NanoJ project org. ( http://www.nanoj.org/ )
 *
 *  Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.gnu.org/licenses/lgpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
