package com;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.io.File;

public class TestClass {
	static final int TOTE_VOL = 35*40*45;
	static List<Product> products = new ArrayList<>();
	static Map<Integer, Selection> map = new ConcurrentHashMap<>();
	public static void main(String[] args) {
		try {
			System.setOut(new PrintStream("out.log"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("small.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(scanner.hasNext()) {
			String input = scanner.next();
			String[] inputArr = input.split(",");
			int[] inp = new int[inputArr.length];
			for(int i=0; i<inputArr.length; i++) {
				inp[i] = Integer.parseInt(inputArr[i]);
			}
			Product product = new Product(inp[0], inp[1], inp[2], inp[3], inp[4], inp[5]);
			products.add(product);
			System.out.println(product.getId()+"->"+product.getVolume()+"->"+product.getWeight());
		}
		scanner.close();
		System.out.println(maxPrice());
	}
	public static int maxPrice() {
		Selection selection = new Selection(new HashSet<Product>());
		map.put(TOTE_VOL, selection);
		for(Product product: products) {
			for(int volume: map.keySet()) {
				Selection selection2 = map.get(volume); 
				int leftVol = volume - product.getVolume();
				if(map.containsKey(leftVol)) {
					Selection currentSelection = map.get(leftVol);
				} else {
					Selection newSelection = selection2.addToSelection(product);
					if(newSelection.getTotalVolume() < TOTE_VOL) {
						map.put(leftVol, newSelection);
					}
				}
			}
		}
		return 0;
	}
}
class Selection {
	private Set<Product> products;
	private int totalWeight;
	private int totalPrice;
	private int totalVolume;
	public Selection(Set<Product> products) {
		super();
		this.products = products;this.totalWeight = 0;this.totalPrice = 0;this.totalVolume = 0;
		for(Product product: products) {
			this.totalWeight += product.getWeight();this.totalPrice += product.getPrice(); this.totalVolume += product.getVolume();
		}
	}
	public String toString() {
		StringBuilder productString = new StringBuilder();
		for(Product product: this.products) productString.append(product.getId()).append(",");
		return "Total Weight: "+this.totalWeight+" *** Total Price: "+this.getTotalPrice()+" *** Product IDs: "+productString.toString().replaceAll(".$", "");
	}
	public Selection addToSelection(Product product) {
		Selection newSelection = this.deepCopy();
		Set<Product> products = newSelection.getProducts();
		if(!products.contains(product)) {
			products.add(product);
			this.totalWeight += product.getWeight();
			this.totalVolume += product.getVolume();
			this.totalPrice  += product.getPrice();
		}
		return newSelection;
	}
	public Selection deepCopy() {
		return new Selection(this.products);
	}
	public Set<Product> getProducts() {
		return products;
	}
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	public int getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(int totalWeight) {
		this.totalWeight = totalWeight;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getTotalVolume() {
		return totalVolume;
	}
	public void setTotalVolume(int totalVolume) {
		this.totalVolume = totalVolume;
	}
}
class Product {
	private int id, price, length, width, height, weight, volume;

	public Product(int id, int price, int length, int width, int height, int weight) {
		super();
		this.id = id;
		this.price = price;
		this.length = length;
		this.width = width;
		this.height = height;
		this.weight = weight;
		this.volume = this.length * this.width * this.height;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
}
