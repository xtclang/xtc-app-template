module HelloWorld {
    void run(String[] args = []) {
        @Inject Console console;
        //assert:debug;
        console.print($"{args[0]}{args[1]}{args[2]}!");
    }
}
