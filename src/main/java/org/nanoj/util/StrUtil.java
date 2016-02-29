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
package org.nanoj.util;

public class StrUtil {
	
	//-------------------------------------------------------------------------------
    /**
     * Returns the given string starting by an Lower Case <br>
     * @param s
     * @return
     */
    public static String firstCharLowerCase(String s) {
    	if ( s != null ) {
    		if ( s.length() > 1 ) {
                return s.substring(0, 1).toLowerCase() + s.substring(1) ;
    		}
    		else if ( s.length() == 1 ) {
    			return s.substring(0, 1).toLowerCase() ;
    		}
    	}
   		return s ;
    }

	//-------------------------------------------------------------------------------
    /**
     * Returns the given string starting by an Upper Case <br>
     * @param s
     * @return
     */
    public static String firstCharUpperCase(String s) {
    	if ( s != null ) {
    		if ( s.length() > 1 ) {
                return s.substring(0, 1).toUpperCase() + s.substring(1) ;
    		}
    		else if ( s.length() == 1 ) {
    			return s.substring(0, 1).toUpperCase() ;
    		}
    	}
   		return s ;
    }

	//-------------------------------------------------------------------------------
}
