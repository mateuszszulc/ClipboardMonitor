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

import java.util.EventObject;

/**
 * Sent when a change event in the system clipboard has occured.
 * 
 * @author <a href="mailto:phil.kursawe@gmail.com">Philipp Kursawe</a>
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class ClipboardEvent extends EventObject {
	private static final long serialVersionUID = 6354639749124932240L;

	/**
	 * Creates a new event.
	 * 
	 * @param source
	 *            of the event. That is most likely the ClipboardMonitor
	 *            component that detected the change in the clipboard, but user
	 *            of the event should not depend on that.
	 */
	public ClipboardEvent(Object source) {
		super(source);
	}
}
