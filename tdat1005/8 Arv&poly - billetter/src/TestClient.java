import qqlib.cli.Prynter;

import java.io.*;
import java.util.Arrays;


/**
 * Oppgavetekst:
 * @link http://www.aitel.hist.no/fag/vprg/TDAT1005/ovinger/TDAT1005_arvpoly2.php
 *
 * Oppgave D:
 *
 * Lag et lite testprogram der du oppretter to ståtribuner, en vanlig sittetribune og en VIP-tribune. Samle tribunene i en tabell av typen Tribune[].
 *
 * Kjøp billetter på alle tribunene, og skriv ut billettene.
 *
 * For hver tribune skal du også skrive ut tribunenavn, kapasitet, antall solgte billetter og inntekten. (Du kan gjerne la en toString()-metode i klassen Tribune gjøre jobben.)
 *
 * THE RESULT:
 * Et testprogram som kan falle sammen hvis det går galt én gang,
 * med kosmetisk bullshit og potensielt irriterende delays.
 *      (det siste la jeg inn helt til slutt, heldigvis)
 *
 */

class TestClient {

    private static Prynter p = new Prynter();
    private static final String feilnaem = "tribune.ser";

    private static boolean writeToFile(Tribune[] array) {

        try (FileOutputStream fos = new FileOutputStream(feilnaem);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(array);

            return true; // it worrrrrrk

        /*} catch (FileNotFoundException e) {
            printFail("hey we could not find da file (write)");*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    private static Tribune[] readFromFile() {

        try (FileInputStream fis = new FileInputStream(feilnaem);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            /*
            Object obj = ois.readObject();

            if (obj instanceof Tribune[]) {
                return (Tribune[]) obj;
            } else {
                return null;
            }
            */

            return (Tribune[]) ois.readObject();

        } catch (FileNotFoundException e) {
            printFail("hey we could not find da file (read)");
        } catch (ClassNotFoundException e) {
            printFail("not a damn Tribune[] in file"); // hva med null?
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    private static void printArray(Object[] array) {

        for (Object obj : array) {

            if (obj != null) {
                System.out.println(obj);
            } else {
                p.setColor(Prynter.RED);
                p.pryntln("Entry is null. Why?");
                p.reset();
            }

        }

    }

    private static void printFail(String msg) {
        p.setColor(Prynter.RED);
        p.setBold();
        p.pryntln(">>>>> " + msg + " <<<<<");
        p.reset();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void printSuccess(String msg) {
        p.setColor(Prynter.GREEN);
        p.setBold();
        p.pryntln("^\\__{ " + msg + " }__/^");
        p.reset();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void printMilestone(String msg) {
        p.setColor(Prynter.WHITE);
        p.setBold();
        p.pryntln("----[ " + msg + " ]----");
        p.reset();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        printMilestone("We will be running some tests.");
        printFail("If one fails, all may fail. I apologize.");

        Tribune stand1 = new Standing("St-A", 20, 100);
        Tribune stand2 = new Standing("St-B", 50, 50);
        Tribune seat = new Seated("Sea-Bed", 30, 150, 6);
        Tribune vip = new VIP("Sea-Viper", 10, 300, 2, 5);
        
        Tribune[] stadium = {stand1, stand2, seat, vip};

        printArray(stadium);
        printMilestone("Starting first round of ticketeering");

        for (Tribune trib : stadium) {

            p.setColor(Prynter.YELLOW);
            p.pryntln("Printing tickets for " + trib.getName());
            p.reset();

            Ticket[] bought = trib.buyTickets(10);

            if (bought != null) {
                printArray(bought);
            } else {
                p.setColor(Prynter.RED);
                p.pryntln("Ticket[] is null.");
                p.reset();
            }

        }

        if (stand1.getTicketsSold() == 10 &&
            stand2.getTicketsSold() == 10 &&
            seat.getTicketsSold() == 10 &&
            vip.getTicketsSold() == 0) {

            printSuccess("Tickets sold as expected (no VIPs)");

        } else {
            printFail("Ticketeering failed");
        }

        Arrays.sort(stadium);
        printArray(stadium);
        // exp:
        // VIP no tickets
        // All other 10 sold - BUG: Seated buying but not registering

        printMilestone("Starting second round of ticketeering - all shall succeed");

        for (Tribune trib : stadium) {

            p.setColor(Prynter.CYAN);
            p.pryntln("Printing tickets for " + trib.getName());
            p.reset();

            Ticket[] bought = trib.buyTickets(new String[] {"hey", "ho", "what", "now"});

            if (bought != null) {
                printArray(bought);
            } else {
                p.setColor(Prynter.RED);
                p.pryntln("Ticket[] is null.");
                p.reset();
            }

        }

        if (vip.getTicketsSold() == 4) {
            printSuccess("VIP tickets sold.");
        } else {
            printFail("VIP failure");
        }

        if (stand1.getIncome() == 100 * 14 &&
            stand2.getIncome() == 50 * 14 &&
            seat.getIncome() == 150 * 14 &&
            vip.getIncome() == 300 * 4) {
            printSuccess("Income calculated correctly");
        } else {
            printFail("Failure incoming with great calculation");
        }

        Arrays.sort(stadium);
        printArray(stadium);
        // exp:
        // VIP buys tickets, them others too - but NO - VIP buys but somehow the ticket[] entries are NULL...

        printMilestone("Starting third round of ticketeering (failing 1 and 4)");

        for (Tribune trib : stadium) {

            p.setColor(Prynter.BLUE);
            p.pryntln("Printing tickets for " + trib.getName());
            p.reset();

            Ticket[] bought = trib.buyTickets(new String[] {"a", "b", "c", "d", "e", "f", "xxx"});

            if (bought != null) {
                printArray(bought);
            } else {
                p.setColor(Prynter.RED);
                p.pryntln("Ticket[] is null.");
                p.reset();
            }

        }

        if (stand1.getTicketsSold() == 14 && // overshoots capacity
            stand2.getTicketsSold() == 21 &&
            seat.getTicketsSold() == 21 &&
            vip.getTicketsSold() == 4) { // overshoots row limit

            printSuccess("Tickets sold as expected (no VIPs)");

        } else {
            printFail("Ticketeering failed");
        }

        Arrays.sort(stadium);
        printArray(stadium);

        printMilestone("doing a lil' more of that seated ticketeering");

        p.setColor(Prynter.YELLOW);
        p.pryntln("Printing tickets for " + seat.getName());
        p.reset();

        Ticket[] bought = seat.buyTickets(11);

        if (bought != null) {
            printArray(bought);
        } else {
            p.setColor(Prynter.RED);
            p.pryntln("Ticket[] is null.");
            p.reset();
        }

        if (seat.getTicketsSold() == 21 && bought == null) {
            printSuccess("it gone well");
        } else {
            printFail("it gone fail");
        }

        printMilestone("Testing I/O");

        if (writeToFile(stadium)) {
            printSuccess("yay we wrote");
        } else {
            printFail("nah it not work");
        }

        Tribune[] sameStadium = readFromFile();

        if (sameStadium != null) {
            printArray(sameStadium);
            printSuccess("we read");
        } else {
            printFail("couldn't read");
        }

        printMilestone("End of testing");

    }

}
