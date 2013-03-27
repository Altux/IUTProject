package vocal;

/**
 *
 * @author GODEAU Quentin
 */
public class Test {

    public static void main(String args[]) throws InterruptedException{
        LecteurTexteThread lecteurTexteThread = new LecteurTexteThread();
        lecteurTexteThread.start();
        Thread.sleep(1000);
        lecteurTexteThread.setTexte("hello");
        System.out.println("read hello");
        Thread.sleep(1);
        lecteurTexteThread.setTexte("world");
        System.out.println("read word");
        Thread.sleep(1000);
        lecteurTexteThread.interrupt();
    }
}
