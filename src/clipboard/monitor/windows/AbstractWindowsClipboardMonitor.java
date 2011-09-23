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
package clipboard.monitor.windows;

import clipboard.monitor.AbstractClipboardMonitor;
import clipboard.monitor.ClipboardEvent;
import clipboard.monitor.windows.internal.User32;
import clipboard.monitor.windows.internal.User32.MSG;
import clipboard.monitor.windows.internal.User32.WNDPROC;
import com.sun.jna.Pointer;
import com.sun.jna.examples.win32.Kernel32;
import com.sun.jna.examples.win32.W32API.HANDLE;
import com.sun.jna.examples.win32.W32API.HWND;
import com.sun.jna.examples.win32.W32API.LPARAM;
import com.sun.jna.examples.win32.W32API.WPARAM;

/**
 * A component that monitors the clipboard on Win32 systems using JNA.
 * 
 * <p>
 * Implementors must implement their own listener management.
 * 
 * @author <a href="mailto:phil.kursawe@gmail.com">Philipp Kursawe</a>
 * 
 */
public abstract class AbstractWindowsClipboardMonitor extends
		AbstractClipboardMonitor implements WNDPROC {

	private HWND viewer;
	private HWND nextViewer;
	private final HANDLE event = Kernel32.INSTANCE.CreateEvent(null, false,	false, null);

	@Override
	public void stop() {
		Kernel32.INSTANCE.SetEvent(event);
	}

	/**
	 * All user level methods must be called from the thread that will also poll
	 * the messages. Because Windows creates a message queue for each thread as
	 * soon as this thread creates a window or calls one of the message queue
	 * related functions.
	 * 
	 */
	public void run() {
		viewer = User32.INSTANCE.CreateWindowEx(0, "STATIC", "", 0, 0, 0, 0, 0, //$NON-NLS-1$ //$NON-NLS-2$
				null, 0, 0, null);
		nextViewer = User32.INSTANCE.SetClipboardViewer(viewer);
		User32.INSTANCE.SetWindowLong(viewer, User32.GWL_WNDPROC, this);

		MSG msg = new MSG();

		final HANDLE handles[] = { event };
		while (true) {
            System.out.println("Test 1");
			int result = User32.INSTANCE.MsgWaitForMultipleObjects(
					0, handles, false, Kernel32.INFINITE,
					User32.QS_ALLINPUT);
			if (result == Kernel32.WAIT_OBJECT_0) {
				User32.INSTANCE.DestroyWindow(viewer);
				return;
			}
			if (result != Kernel32.WAIT_OBJECT_0 + handles.length) {
				// Serious problem!
				//break;
			}

			while (User32.INSTANCE.PeekMessage(msg, null, 0, 0,
					User32.PM_REMOVE)) {
				User32.INSTANCE.TranslateMessage(msg);
				User32.INSTANCE.DispatchMessage(msg);
			}
		}
	}

	public int callback(HWND hWnd, int uMsg, WPARAM wParam, LPARAM lParam) {
		switch (uMsg) {
		case User32.WM_CHANGECBCHAIN:
			// If the next window is closing, repair the chain.
			if (nextViewer.toNative().equals(wParam.toNative())) {
				nextViewer = new HWND(Pointer
						.createConstant(lParam.longValue()));
			} // Otherwise, pass the message to the next link.
			else if (nextViewer != null) {
				User32.INSTANCE.SendMessage(nextViewer, uMsg, wParam, lParam);
			}
			return 0;
		case User32.WM_DRAWCLIPBOARD:
			try {
				onChange(new ClipboardEvent(this));
			} finally {
				User32.INSTANCE.SendMessage(nextViewer, uMsg, wParam, lParam);
			}
			return 0;
		case User32.WM_DESTROY:
			User32.INSTANCE.ChangeClipboardChain(viewer, nextViewer);
			break;
		}
		return User32.INSTANCE.DefWindowProc(hWnd, uMsg, wParam, lParam);
	}
}
