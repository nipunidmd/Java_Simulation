/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booktrading;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author x6y48
 */
public class BookTrading {
    
    //constant values
    static final int TOTAL_BUYERS = 5;    
    static final int TOTAL_SELLERS = 10;
    static final int TOTAL_BOOKS = 20;
    static final int INITIAL_BOOKS_PER_SELLER = 5;    
    static final int PURCHASES_PER_BUYER = 3;
    static String[] BOOK_NAMES;     
    static int[] BOOK_PRICES = {};      
    static int[] SELLER_INCOME = new int[TOTAL_SELLERS];    
    static int[] BUYER_SPEND = new int[TOTAL_BUYERS];

    
    //Data structure to all difference agents
    static ArrayList<Buyer> buyers = new ArrayList<>();        
    static ArrayList<Seller> sellers = new ArrayList<>();    
    static ArrayList<Book> books = new ArrayList<>();
    static final ArrayList<String> book_names = new ArrayList<>();    
    static final ArrayList<Integer> book_prices = new ArrayList<>();



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        int inPrice = 0;
        String inName = "";             
        String inChoice = "";
        char choice = 'y';
        int inVal = 0;

        
        //generate buyers and sellers for system
        //make buyers
        for(int i=1;i<=TOTAL_BUYERS;i++){
            Buyer buyer = new Buyer(i);
            buyers.add(buyer);
        }
        
        //make sellers
        for(int i=1;i<=TOTAL_SELLERS;i++){
            Seller seller = new Seller(i);
            sellers.add(seller);
        }
        
        Scanner input = new Scanner(System.in);
        try{     
            System.out.println("Seller Book List:");
            File f = new File("books");

            FileWriter fr = new FileWriter(f, true);


            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter ofstream = new PrintWriter(br);
            Scanner fstream = new Scanner(f);        

            do{
                while(fstream.hasNext()){

                    inName = fstream.next();
                    inVal = fstream.nextInt();
                    System.out.println(inName+ "  £"+inVal);
                }

                System.out.print("Add more? Y/N: ");
                inChoice = input.nextLine();
                choice = inChoice.charAt(0);
                if (choice == 'Y' || choice == 'y') {

                    System.out.print("Enter name: ");
                    String inName_d = input.nextLine();
                    inName = inName_d.replace(" ","-");

                    System.out.print("Enter price in £: ");
                    inPrice = input.nextInt();
                    input.nextLine();

                writeFileData(inName, inPrice, ofstream);
                }
                else{
                    break;
                }
            }while(true);

            ofstream.close(); 
        }
        catch(IOException e){
            
            System.out.append("File Error");
        }


        //Each buyer will purchase upto a set number of books
        for(int i=1;i<=PURCHASES_PER_BUYER;i++){
            for(Buyer b: buyers){
                
                int[] cheapestOffer = b.RequestBook();

                //If the book is available, cheapest offer is accepted
                if(cheapestOffer[0] == 0 ){
                    System.out.println("None available");
                }
                else{
                    if(b.BUYER_WALLET >= cheapestOffer[1] ){
                        b.buyBook(cheapestOffer);
                        
                        b.BUYER_WALLET = b.BUYER_WALLET - cheapestOffer[1];
                        BUYER_SPEND[b.ID-1] = 50 - b.BUYER_WALLET;
                    }
                    else if(b.BUYER_WALLET < cheapestOffer[1]){
                        System.out.println("-----------------------------------");
                        System.out.println("Buyer:"+b.ID+" Insufficient funds! Only £"+b.BUYER_WALLET+" available");
                    }
                    else{
                        System.out.println("-----------------------------------");
                        System.out.println("Buyer:"+b.ID+" Wallet is Empty");
                    }  
                }

                //After a sale add more space and slow down the system
                System.out.println();            
                System.out.println();
                try{
                    Thread.sleep(1000);
                }
                catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
              
            
        }
        
        System.out.println("----Income Statement/ Sellers----");
        
        //income for each seller
        for(Seller s: sellers){
            
            System.out.println("Seller:"+s.ID+" Income is £"+SELLER_INCOME[s.ID-1]);
            try{
                Thread.sleep(10);
            }
            catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }

        }
        System.out.println();
        System.out.println("----Statement of spending / Buyers----");
        
        //spent amount for each buyer
        for(Buyer b: buyers){
            
            System.out.println("Buyer:"+b.ID+" Spent amount is £"+BUYER_SPEND[b.ID-1]);
            try{
                Thread.sleep(10);
            }
            catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }

        }

    }
    
    public static void writeFileData(String inBookName, int inBookPrice,PrintWriter outStream){
        outStream.printf("%40s %10d%n",inBookName,inBookPrice);
    }
    
    
}
