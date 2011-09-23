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
 * Abstract base class for a ClipboardMonitor that starts a daemon thread and
 * lets the monitoring happening in the {@link #run()} method.
 * 
 * <p>
 * Subclass must implement the logic of monitoring inside the the {@link #run()}
 * method.
 * 
 * @author <a href="mailto:phil.kursawe@gmail.com">Philipp Kursawe</a>
 * 
 */
public abstract class AbstractClipboardMonitor implements Runnable,
		ClipboardMonitor {

	private Thread thread;

	public void start() {
		if (null == thread) {
			thread = new Thread(this, "Clipboard Monitor"); //$NON-NLS-1$
			thread.setDaemon(true);
			thread.start();
		}
	}

	public void stop() {
		if (thread != null) {
			thread.interrupt();
			thread = null;
		}
	}

	/**
	 * Called upon changes in the clipboard.
	 * 
	 * @param event
	 *            describing the change
	 */
	abstract protected void onChange(ClipboardEvent event);
}
