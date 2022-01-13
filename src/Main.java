
public class Main {
    private static final int FREQ_OF_SWITCH = 1500;
    private static final int FREQ_OF_CHECK = 100;
    private static final int SWITCH_AMOUNT = 3;

    private static volatile boolean switcher;

    public static void main(String[] args) throws InterruptedException {
        Thread user = new Thread(on());
        user.start();

        Thread toyBox = new Thread(off());
        toyBox.start();

        user.join();
        toyBox.interrupt();

    }

    private static Runnable on() {
        return () -> {
            for (int i = 0; i < SWITCH_AMOUNT; i++) {
                try {
                    switcher = true;
                    System.out.println("Включаю!");
                    Thread.sleep(FREQ_OF_SWITCH);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };
    }


    //BUSY WAITING
    private static Runnable off() {
        return () -> {
            try {
                while(true) {
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }
                    if (switcher) {
                        switcher = false;
                        System.out.println("Выключаю!");
                    }
                    Thread.sleep(FREQ_OF_CHECK);
                }
            } catch (InterruptedException e) {
                System.out.println("The end");
            }
        };
    }
}
