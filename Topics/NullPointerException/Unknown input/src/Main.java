class Util {
    // correct this method to avoid NPE
    public static void printLength(String name) {
        if (name != null)
            System.out.println(name.length());
    }

    public static void main(String[] args) {
        printLength(null);
    }
}