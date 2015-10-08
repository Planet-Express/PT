package PT;

public class Counter extends Thread{

	Galaxie g;
	GUI gui;
	
	public void run(){
		while(true){
			try {
				Thread.sleep(1000);
				System.out.println("test");
				int c = (int)(Math.random()*5000);
				g.getPlanety().get(c).pop = 999999999;
				gui.prekresliPlatno();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void start(Galaxie g, GUI gui){
		super.start();
		this.g = g;
		this.gui = gui;
	}
	
}
