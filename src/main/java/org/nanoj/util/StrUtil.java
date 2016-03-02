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
	
    /**
     * Split a string using the given char as separator ( simple split without "reg exp" )
     * @param s : the string to split
     * @param c : the separator
     * @return : array of 'tokens' ( never null, size = 0 if the string is null, else 1 to N )
     */
    public static String[] split(String s, char c) {
        if (s != null) {
            char chars[] = s.toCharArray();
            
            // Count separators
            int count = 0 ;
            for ( int n = 0 ; n < chars.length ; n++ ) {
                if ( chars[n] == c ) count++ ;
            }
            
            if ( count > 0 ) {
	            String[] sTokens = new String[count+1] ;
	            int iToken = 0;
	            int iOffset = 0 ;
	            int iLength = 0 ;
	            for ( int i = 0 ; i < chars.length ; i++ ) {
	                if ( chars[i] == c ) {
	                    //--- Create new token 
	                    sTokens[iToken] = new String(chars, iOffset, iLength );
	                    iToken++;
	                    //--- Reset 
	                    iOffset = i + 1 ;
	                    iLength = 0 ;
	                }
	                else  {
	                    iLength++;
	                }
	            }
	            //--- Last Token ( current token ) 
                sTokens[iToken] = new String(chars, iOffset, iLength );
	            return sTokens ;
            }
            else {
                //--- No separator
                String[] ret = new String[1];
                ret[0] = s ;
                return ret ;
            }
        }
        return new String[0]; 
    }
    //-----------------------------------------------------------------------------------
    /**
     * Returns true if the given String is null or void ( "", " ", "  " )
     * @param s
     * @return
     */
    public static boolean nullOrVoid(final String s) {
        if (s == null) {
            return true ;
        }
        else {
            if ( s.trim().length() == 0 ) {
                return true ;
            }
        }
        return false ;
    }
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
