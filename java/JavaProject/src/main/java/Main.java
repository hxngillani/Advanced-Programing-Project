/*
HASSAN SHABIR
MASTER OF COMPUTER SCIENCE AND NETWROKING
2021-2022
*/


public class Main {
    
    public static void main(String[] args) {
        // Creates a new scheduler and starts its execution
        JobSchedFramework scheduler  = new JobSchedFramework(new AnagramsCounter());
        scheduler.runMain();
    }
}