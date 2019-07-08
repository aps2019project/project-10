package network.battle;

public class BattleThread {
    private static ClientForBattle[] battleThreads = new ClientForBattle[2];

    private ClientForBattle client1;
    private ClientForBattle client2;

    public static ClientForBattle[] getBattleThreads() {
        return battleThreads;
    }

    public static void setBattleThreads(ClientForBattle[] battleThreads) {
        BattleThread.battleThreads = battleThreads;
    }

    public ClientForBattle getClient1() {
        return client1;
    }

    public void setClient1(ClientForBattle client1) {
        this.client1 = client1;
    }

    public ClientForBattle getClient2() {
        return client2;
    }

    public void setClient2(ClientForBattle client2) {
        this.client2 = client2;
    }

    public BattleThread(ClientForBattle client1, ClientForBattle client2){
        this.client1 = client1;
        this.client2 = client2;

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }
}
