/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import common.Library;
import java.util.ArrayList;
import java.util.Hashtable;
import model.Fruit;
import model.Order;
import view.Menu;

/**
 *
 * @author DiepTCNN
 */
public class FruitController extends Menu {

    static String[] options = {"Create Fruit", "View Orders", "Shopping (for buyer)", "Exit"};
    Library l;
    ArrayList<Fruit> listFruit;
    Hashtable<String, ArrayList<Order>> ht;

    public FruitController() {
        super("FRUIT SHOP SYSTEM", options);
        l = new Library();
        listFruit = new ArrayList<>();
        ht = new Hashtable<>();
    }

    @Override
    public void execute(int n) {
        switch (n) {
            case 1:
                createFruit();
                break;
            case 2:
                viewListOrder();
                break;
            case 3:
                shopping();
                break;
            case 4:
                System.exit(0);
        }
    }

    private void createFruit() {
        int id = generateID();
        String name = l.getString("Enter fruit name: ");
        double price = l.getDouble("Enter fruit price");
        int quantity = l.getInt("Enter fruit quantity", 1, 100);
        String origin = l.getString("Enter fruit origin: ");
        listFruit.add(new Fruit(id, name, price, quantity, origin));
        String ch = l.getString("Do you want to continue (Y/N)? ");
        if ("Y".equals(ch) || "y".equals(ch)) {
            createFruit();
        }
    }

    private int generateID() {
        int id = 0;
        if (listFruit.size() != 0) {
            return listFruit.size() + 1;
        }
        return 1;
    }

    private void viewListOrder() {
        if (!ht.isEmpty()) {
            for (String name : ht.keySet()) {
                System.out.println("Customer: " + name);
                ArrayList<Order> arrOrder = ht.get(name);
                displayListOrder(arrOrder);
            }
        } else {
            System.err.println("No order.");
        }
    }

    private void displayListOrder(ArrayList<Order> arrOrder) {
        int total = 0;
        for (Order o : arrOrder) {
            System.out.println("Id: " + o.getId() + " - Customer of name: " + o.getName() + " - quanlity: " + o.getQuantity() + " - price: " + o.getPrice());
            total += o.getPrice() * o.getQuantity();
        }
        System.out.println("Total: " + total);
    }

    private void shopping() {
        displayFruit();
        ArrayList<Order> listOrder = new ArrayList<>();
        String name = l.getString("Enter fruit name: ");
        int quantityOrder = l.getInt("Please input quantity", 1, 10);
        String s = l.getString("Do you want to order now (Y/N)?");
        if (s == "Y" || s == "y")
            for (Fruit f : listFruit)
                if (name.equals(f.getName())) {
                    int id = f.getId();
                    double price = f.getPrice();
                    int quantity = f.getQuantity();
                    if (quantityOrder > quantity) {
                        System.out.println("Quantity Order more than quantity");
                        shopping();
                    }
                    listOrder.add(new Order(id, name, quantityOrder, price));
                    f.setQuantity(quantity - quantityOrder);
                    displayListOrder(listOrder);
                    String customer = l.getString("Enter Customer of name: ");
                    ht.put(customer, listOrder);
                    System.out.println("Added successfully");
                }
    }

    private void displayFruit() {
        for (Fruit f : listFruit) {
            System.out.println("Id: " + f.getId() + " - Name: " + f.getName() + " - Price: " + f.getPrice() + " - quantity:" + f.getQuantity() + " - origin: " + f.getOrigin());
        }
    }
}