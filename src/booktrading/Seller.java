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
public class Seller {
    //Attributes of buyer
    int ID;
    ArrayList<Book> localCatalogue = new ArrayList<>();    

    int SELLER_WALLET = 0;
    String inName;
    int inVal = 0;
    
    //Object for use random numbers
    Random random = new Random();
    
    //Constructor method of buyer)
    Seller(int ID) throws FileNotFoundException{
        this.ID = ID;
        
        for(int i=1;i<=BookTrading.INITIAL_BOOKS_PER_SELLER;i++){
            newBook();
        }
    }
    
    //makes a new car add it to the seller inventory
    void newBook() throws FileNotFoundException{
        
        File f = new File("books");
        Scanner fstream = new Scanner(f);
        while(fstream.hasNext()){
                        
            inName = fstream.next();
            inVal = fstream.nextInt();
            BookTrading.book_names.add(inName);                
            BookTrading.book_prices.add(inVal);

        }
        String names = BookTrading.book_names.toString();            
        String prices = BookTrading.book_prices.toString();            
            
        String names2 = names.replace("[","");            
        String prices2 = prices.replace("[","");             
             
        String names3 = names2.replace("]","");             
        String prices3 = prices2.replace("]","");            
        String prices4 = prices3.replace(" ","");             
           
        String[] book_names = names3.split(",");            
        String[] book_prices = prices4.split(",");

        int randomNumber = random.nextInt(book_names.length);
        String name = book_names[randomNumber];
        String price2 = book_prices[randomNumber];        
        int price3 = Integer.parseInt(price2);        
        int price = 0;
        
        for (Book b : localCatalogue) {
            if (name.equals(b.name)) {
                price = b.price;
            } else {
                price = price3 - random.nextInt(price3) + 5;
            }
        }
        if (price == 0) {
            price = price3 - random.nextInt(price3) + 5;
        }
        
        Book book = new Book(name, price);
        localCatalogue.add(book);
        
    }
    
    //returns an offer if the requested book is in the sellers inventory
    int[] shareListing(String name){
        
        int[] offer = {0,0};
        
        for(Book b: localCatalogue){
            if(b.name.equals(name)){
                offer[0] = ID;
                offer[1] = b.price;
                
                System.out.println("Seller:"+ID+"  I can offer one for £"+b.price);
                
                break;
            }
        }
        
        return offer;
    }

    //confirms a sale and removes the book from sellers inventory
    void confirmSale(String name, int price) {
        
        for(Book b: localCatalogue){
            if(b.name.equals(name) && b.price == price){
                localCatalogue.remove(b);
                SELLER_WALLET = SELLER_WALLET + b.price;
                System.out.println("-----------------------------------");
                BookTrading.SELLER_INCOME[ID-1] = SELLER_WALLET;
                break;
            }
        }
        
    }
    

}
