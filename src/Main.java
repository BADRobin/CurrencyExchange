import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, BigDecimal> rates = null;
        File file = new File("Click OK");
        while(rates == null) {
            String pathToFile = JOptionPane.showInputDialog(file, "src/data.csv");

            rates = readCSV(pathToFile);
        }

        BigDecimal amount = null;
        boolean number = false;
        while(!number) {
            try {
                amount = new BigDecimal(JOptionPane.showInputDialog("Bitcoin price today - 21000$." + " " + " Amount of the currency you have: "));
                number = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Enter the amount in a number format, please!");
            }
        }

        String initialTick = "";
        boolean exists = false;
        while(exists != true) {
            initialTick = JOptionPane.showInputDialog("You want to change: ", "USD");
            initialTick = initialTick.toUpperCase();
            if(!rates.containsKey(initialTick)) {
                JOptionPane.showMessageDialog(null, "The provided currency is not supported. Not yet. Sorry...");
                System.out.println("Supported ticks:");
                for(String i: rates.keySet()) {
                    System.out.println(i);
                }
                JOptionPane.showMessageDialog(null, "Try to enter the tick of the currency again. ");
            }else {
                exists = true;
            }
        }

        String finalTick = "";
        exists = false;
        while(exists != true) {
            finalTick = JOptionPane.showInputDialog("To ", "BTC");
            finalTick = finalTick.toUpperCase();
            if(!rates.containsKey(finalTick)) {
                JOptionPane.showMessageDialog(null, "The provided currency is not supported. Not yet. Sorry... ");
                System.out.println("Supported ticks:");
                for(String i: rates.keySet()) {
                    System.out.println(i);
                }
                JOptionPane.showMessageDialog(null, "Try to enter the tick of the currency again. ");
            }else {
                exists = true;
            }
        }

        BigDecimal finalAmount = amount.divide(rates.get(initialTick), 30, RoundingMode.HALF_UP);
        finalAmount = finalAmount.multiply(rates.get(finalTick));
        finalAmount = finalAmount.setScale(18, RoundingMode.HALF_UP).stripTrailingZeros();
        JOptionPane.showMessageDialog(null, amount + " " + initialTick + " is equivelent to " + finalAmount.toPlainString() + " " + finalTick);


    }

    public static HashMap<String, BigDecimal> readCSV(String pathToFile){


        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(pathToFile));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Either CSV file with rates is not existent or the given path is wrong");
            return null;
        }


        String row;
        HashMap<String, BigDecimal> rates = new HashMap<String, BigDecimal>();

        try {
            while( (row = reader.readLine()) != null ) {

                String[] data = row.split(",", 2);
                data[1] = data[1].replace("\"", "");
                data[1] = data[1].replace(",", ".");
                BigDecimal rate = new BigDecimal(data[1]);
                rates.put(data[0], rate);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rates;

    }


}

