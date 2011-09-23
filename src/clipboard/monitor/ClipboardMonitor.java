/*******************************************************************************
 * Copyright (c) 2010 Philipp Kursawe.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Philipp Kursawe (phil.kursawe@gmail.com) - initial API and implementation
 ******************************************************************************/
package clipboard.monitor;

/**
 * A service that monitors the system clipboard for changes.
 * 
 * @author <a href="mailto:phil.kursawe@gmail.com">Philipp Kursawe</a>
 * 
 */
public interface ClipboardMonitor {

	/**
	 * Starts the monitoring of the clipboard. This method can be called
	 * multiple times. If the monitor was already started the call has no
	 * effect.
	 */
	void start();

	/**
	 * Stops the monitoring of the clipboard. This method can be called multiple
	 * times. If the monitor has already been stopped, then the call has no
	 * effect.
	 */
	void stop();
}
