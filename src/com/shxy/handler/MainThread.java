package com.shxy.handler;

public class MainThread {

    public static void main(String[] args) {
        MainThread mainThread = new MainThread();
        Thread.currentThread().setName("main thread");
        mainThread.run();

    }

    private static final int MSG_TEST = 1;

    private Handler handler = null;
    
    public void run() {
        Looper.prepared();
        System.out.println("onCreate()");
        System.out.println("onStart()");
        System.out.println("onResume()");
        handler = initHandler();
        Thread workThread = new Thread(new Task());
        workThread.setName("working thread");
        workThread.start();
        Looper.loop();
    }

    /**
     * 在主线程 初始化handler
     * @return
     */
    private Handler initHandler() {
        return new Handler() {
            @Override
            protected void handlerMessage(Message message) {
                switch (message.what) {
                    case MSG_TEST:
                        System.out.println("Thread name = " + Thread.currentThread().getName() + " resolve message");
                        break;
                }
            }
        };
    }

    /**
     * 工作线程执行的任务
     */
    private class Task implements Runnable {

        @Override
        public void run() {
            int n = 10;
            while (n-- > 0) {
                try {
                    Thread.sleep(2000);
                    System.out.println("Thread name = " + Thread.currentThread().getName() + " send message!");
                    handler.sendEmptyMessage(MSG_TEST);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
