package application;

import java.io.IOException;
import java.io.PrintStream;

public class TesteSaida {
	
	public static void main(String[] args) throws IOException{
		
		PrintStream out = new PrintStream("saida.txt");
		out.println("Guilherme Golfetto");
		out.println("É o cara");
		out.close();
		
	}

}
