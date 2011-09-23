import clipboard.monitor.ClipboardEvent;
import clipboard.monitor.ClipboardListener;
import clipboard.monitor.windows.WindowsClipboardMonitor;

/**
 * Created by IntelliJ IDEA.
 * User: mateusz
 * Date: 18.09.11
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class WindowsClipboardMonitorApp implements ClipboardListener {
    public WindowsClipboardMonitorApp() throws InterruptedException {
        System.out.println("Starting.");
        WindowsClipboardMonitor wc = new WindowsClipboardMonitor();
        wc.addListener(this);
        wc.run();
        System.out.println("Test 2");
        Thread.sleep(1000000L);
        System.out.println("Test 3");

    }

    public static void main(String[] args) throws InterruptedException {
        WindowsClipboardMonitorApp wcApp = new WindowsClipboardMonitorApp();
    }

    public void onEvent(ClipboardEvent event) {
        System.out.println("Nowa zawartosc w schowku!");
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
