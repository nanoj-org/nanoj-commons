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

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleLoggerProvider {

	private static Level GLOBAL_LEVEL = Level.ALL ;
	
	/**
	 * Set the global level threshold = 'do not log under this threshold'
	 * @param level
	 */
	public final static void setGlobalLevelThreshold(final Level level) {
		GLOBAL_LEVEL = level ;
	}
	
	public final static Logger getLogger(final Class<?> clazz, final Level level) {
		
		// Create a logger for the given class
		Logger logger = Logger.getLogger(clazz.getName()); 
		
//		// Create a ConsoleHandler with the expected log level
//		Handler consoleHandler = new ConsoleHandler();
//		consoleHandler.setLevel(level);
//		
//		// Set the ConsoleHandler
//		logger.addHandler(consoleHandler);
		
		// Set the log level 
		// Level intValue() :
		//		OFF     : 2147483647
		//		SEVERE  : 1000 
		//		WARNING :  900 
		//		INFO    :  800 
		//		CONFIG  :  700 
		//		FINE    :  500 
		//		FINER   :  400 
		//		FINEST  :  300 
		//		ALL     : -2147483648
		Level loggerLevel = level ;
		if ( level.intValue() < GLOBAL_LEVEL.intValue() ) {
			// Stay at the lowest level
			loggerLevel = GLOBAL_LEVEL;
		}
		
		// Add a new handler to be able to log CONFIG, FINE, FINER and FINEST levels
		if ( loggerLevel.intValue() < Level.INFO.intValue() ) {
			// Create a ConsoleHandler with the expected log level
			Handler consoleHandler = new ConsoleHandler();
			consoleHandler.setLevel(loggerLevel);			
			// Set the ConsoleHandler
			logger.addHandler(consoleHandler);
		}
		
		logger.setLevel(loggerLevel);
		
		return logger;
	}
}
