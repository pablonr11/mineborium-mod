package com.mundobachata.mineborium.client;

public class ClientAbstinenceData {
    private static int playerAbstinence;

    public static void set(int playerAbstinence) {
        ClientAbstinenceData.playerAbstinence = playerAbstinence;
    }

    public static int get() {
        return playerAbstinence;
    }
}
