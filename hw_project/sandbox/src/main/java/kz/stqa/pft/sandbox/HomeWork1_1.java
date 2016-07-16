package kz.stqa.pft.sandbox;

public class HomeWork1_1 {
	
	public static void main(String[] args){

	hello("work");

		Square s = new Square(5);
		//s.l = 5;
		System.out.println("Площадь квадрата равна " + s.area());

		Rectangle r = new Rectangle(4, 6);

		//r.a = 4;
		//r.b = 6;

		System.out.println("Площадь прямоугольника = " + r.area());
	}

	public static void hello(String somebody){

		System.out.println("My first Home "+ somebody + "!");
	}



}

