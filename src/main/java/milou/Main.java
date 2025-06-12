package milou;

import milou.command.*;
import milou.framework.SingletonSessionFactory;

public class Main {
    public static void main(String[] args) {
        SingletonSessionFactory.get().inTransaction(session -> {});
        new CommandLine().start();
    }
}