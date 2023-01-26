/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booktrading;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author x6y48
 */
public class Buyer {

    //Attributes of buyer
    int ID;
    String currentRequest;
    int BUYER_WALLET = 50;
    String inName;
    int inVal = 0;

    //random object to use random numbers
    Random random = new Random();

    //Initiator
    Buyer(int ID) {
        this.ID = ID;
    }

    int[] RequestBook() throws FileNotFoundException {

        File f = new File("books");
        Scanner fstream = new Scanner(f);
        while (fstream.hasNext()) {

            inName = fstream.next();
            inVal = fstream.nextInt();
            BookTrading.book_names.add(inName);
            BookTrading.book_prices.add(inVal);

        }
        String names = BookTrading.book_names.toString();

        String names2 = names.replace("[", "");

        String names3 = names2.replace("]", "");

        String[] book_names = names3.split(",");

        int randomNumber = random.nextInt(book_names.length);
        String name = book_names[randomNumber];
        currentRequest = name;

        System.out.println("Buyer:" + ID + "  I would like buy " + name + " Book");

        //collect all the offers from various sellers
        ArrayList<int[]> offers = new ArrayList<>();

        for (Seller s : BookTrading.sellers) {
            int[] offer = s.shareListing(name);

            if (offer[0] != 0) {
                offers.add(offer);
            }
        }

        //workout which offer is the best
        int[] cheapestOffer = {0, 0};
        for (int[] offer : offers) {
            if (offer[1] < cheapestOffer[1] || cheapestOffer[0] == 0) {
                cheapestOffer[0] = offer[0];
                cheapestOffer[1] = offer[1];
            }
        }

        return cheapestOffer;

    }

    void buyBook(int[] cheapestOffer) {
        for (Seller s : BookTrading.sellers) {

            //use the seller ID to make we buy from the right seller
            if (s.ID == cheapestOffer[0]) {
                s.confirmSale(currentRequest, cheapestOffer[1]);

                System.out.println("Buyer:" + ID + " I bought from Seller " + s.ID);

                currentRequest = "";
                break;
            }

        }
    }

}
