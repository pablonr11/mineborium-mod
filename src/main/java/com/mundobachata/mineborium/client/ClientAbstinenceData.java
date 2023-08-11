package com.mundobachata.mineborium.client;

public class ClientAbstinenceData {
    private static int playerAbstinence;
    private static boolean hasSmoked;

    public static void setPlayerAbstinence(int playerAbstinence) {
        ClientAbstinenceData.playerAbstinence = playerAbstinence;
    }

    public static int getPlayerAbstinence() {
        return playerAbstinence;
    }

    public static void setHasSmoked(boolean hasSmoked) {
        ClientAbstinenceData.hasSmoked = hasSmoked;
    }

    public static boolean playerHasSmoked() {
        return hasSmoked;
    }
}
