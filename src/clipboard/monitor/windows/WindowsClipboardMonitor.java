package clipboard.monitor.windows;

import clipboard.monitor.ClipboardEvent;
import clipboard.monitor.ClipboardListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Conreate implementation of a Windows Clipboard Monitor with listener
 * management.
 * 
 * @noosgi This class can be used without OSGi.
 * @author <a href="mailto:phil.kursawe@gmail.com">Philipp Kursawe</a>
 */
public class WindowsClipboardMonitor extends AbstractWindowsClipboardMonitor {

	private Set<ClipboardListener> listeners;

	@Override
	public void onChange(final ClipboardEvent event) {
        System.out.println("onChange!!!!");
		if (listeners != null) {
			for (ClipboardListener listener : listeners) {
				try {
					listener.onEvent(event);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}

	/**
	 * Adds the given listener to this monitor.
	 * 
	 * @param listener
	 *            to add. Adding the same listener more than once has no effect.
	 */
	public void addListener(ClipboardListener listener) {
        System.out.println("Add listenerssss");
		getListeners().add(listener);
	}

	/**
	 * Removes the given listener from this monitor.
	 * 
	 * @param listener
	 *            to remove. Removing the same listener more than once has no
	 *            effect. If the listener was not added to this monitor before,
	 *            this method has no effect.
	 */
	public void removeListener(ClipboardListener listener) {
		getListeners().remove(listener);
	}

	protected Set<ClipboardListener> getListeners() {
		if (null == listeners) {
			listeners = new HashSet<ClipboardListener>();
		}
		return listeners;
	}
}
