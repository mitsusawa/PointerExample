package pointerExample;

import java.util.Scanner;

class Fruit implements Cloneable {
	private String name;
	private int price;
	private int stockCount;
	
	public Fruit(String name, int price, int stockCount) {
		this.name = name;
		if (price < 0) {
			this.price = 0;
		} else {
			this.price = price;
		}
		if (stockCount < 0) {
			this.stockCount = 0;
		} else {
			this.stockCount = stockCount;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		if (price < 0) {
			price = 0;
		}
		this.price = price;
	}
	
	public int getStockCount() {
		if (stockCount < 0) {
			stockCount = 0;
		}
		return stockCount;
	}
	
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	
	public void showInfo() {
		System.out.printf("価格: %6d, 商品名: %8s, 在庫数: %5d\n", this.price, this.name, this.stockCount);
	}
	
	public void bought(int theNumberOfThing) {
		if (theNumberOfThing < 0) {
			theNumberOfThing = 0;
		}
		setStockCount(getStockCount() - theNumberOfThing);
		if (getStockCount() < 0) {
			setStockCount(0);
		}
	}
	
	public static void checkStock(Fruit[] fruits) {
		boolean flag = false;
		for (int i = 0; i < fruits.length; i++) {
			if (fruits[i].getStockCount() <= 0) {
				for (int j = 0; j < fruits.length; j++) {
					if (fruits[i] == fruits[j] || fruits[j].getStockCount() <= 0) {
						continue;
					} else {
						System.out.print(fruits[i].getName() + "の代わりに");
						fruits[i] = fruits[j];
						System.out.println("在庫から" + fruits[i].getName() + "を陳列しました。");
						flag = true;
						break;
					}
				}
			}
			if (!flag) {
				checkStock(fruits, fruits[(int) (Math.random() * fruits.length)]);
			}
		}
	}
	
	public static void checkStock(Fruit[] fruits, Fruit additionalFruit) {
		for (int i = 0; i < fruits.length; i++) {
			if (fruits[i].getStockCount() <= 0) {
				System.out.print(fruits[i].getName() + "の代わりに");
				fruits[i] = new Fruit(additionalFruit.getName(), additionalFruit.getPrice(), additionalFruit.getStockCount());
				if (fruits[i].getStockCount() <= 0) {
					fruits[i].setStockCount(1 + (int) (Math.random() * 6.));
				}
				System.out.println("新たに入荷した" + fruits[i].getName() + "を陳列しました。");
			}
		}
	}
	
	public static void tryToSwapFruits(Fruit fruit1, Fruit fruit2) {
		System.out.println(fruit1.getName() + "と" + fruit2.getName() + "の入れ替えを試みます。");
		Fruit tmpFriut = fruit2;
		fruit2 = fruit1;
		fruit1 = tmpFriut;
	}
	
	public static void showAllInfo(Fruit[] fruits) {
		for (Fruit fruit : fruits) {
			fruit.showInfo();
		}
	}
	
	public static void showAllNum(Fruit[] fruits) {
		for (int i = 0; i < fruits.length; i++) {
			System.out.print(i + ": " + fruits[i].getName() + ", ");
		}
		System.out.println();
	}
	
	public Fruit clone() {
		return new Fruit(this.getName(), this.getPrice(), this.getStockCount());
	}
	
	public static Fruit[] clone(Fruit[] fruits) {
		Fruit[] fruitsArray = new Fruit[fruits.length];
		for (int i = 0; i < fruits.length; i++) {
			fruitsArray[i] = fruits[i].clone();
		}
		return fruitsArray;
	}
}

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Fruit[] fruits = new Fruit[5];
		fruits[0] = new Fruit("イチゴ", 300, 5 + (int) (Math.random() * 6.));
		fruits[1] = new Fruit("リンゴ", 150, 5 + (int) (Math.random() * 6.));
		fruits[2] = new Fruit("ミカン", 100, 5 + (int) (Math.random() * 6.));
		fruits[3] = new Fruit("メロン", 1000, 5 + (int) (Math.random() * 6.));
		fruits[4] = new Fruit("スイカ", 650, 5 + (int) (Math.random() * 6.));
		Fruit[] clonedFruts = Fruit.clone(fruits);
		System.out.println("初期状態: ");
		Fruit.showAllInfo(fruits);
		int n = 0;
		System.out.println("\n以下、-1を入力すると終了します。\n\n");
		while (n >= 0) {
			int num;
			try {
				Fruit.showAllNum(fruits);
				System.out.print("果物の種類を選択してください: ");
				n = sc.nextInt();
				System.out.print("購入する個数を選択してください: ");
				fruits[n].bought(sc.nextInt());
				if (Math.random() < 0.5) {
					Fruit.checkStock(fruits);
				} else {
					Fruit.checkStock(fruits, fruits[(int) (Math.random() * fruits.length)]);
				}
				System.out.println();
				Fruit.showAllInfo(fruits);
				System.out.println('\n');
			} catch (Exception e) {
				break;
			}
		}
		System.out.println("\n\n");
		fruits = clonedFruts;
		System.out.println("果物を初期状態に戻しました。");
		Fruit.showAllInfo(fruits);
		System.out.println('\n');
		int x = (int) (Math.random() * (fruits.length / 2));
		int y = (int) (Math.random() * (fruits.length / 2) + (fruits.length / 2 + 1));
		Fruit.tryToSwapFruits(fruits[x], fruits[y]);
		System.out.println("\n結果:");
		Fruit.showAllInfo(fruits);
		sc.close();
	}
}
