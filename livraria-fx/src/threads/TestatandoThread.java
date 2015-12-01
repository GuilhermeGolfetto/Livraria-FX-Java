package threads;

public class TestatandoThread {
	
	public static void main(String[] args){
		
		new Thread(()-> System.out.println("Roda em paralelo")).start();
		System.out.println("Terminei de rodar o main!");
	}

}
