class Hydrogen implements Runnable{
	ReactionArea buff;
	
	public Hydrogen(ReactionArea buff){
		this.buff = buff;
	}
	public void run(){
		for(int i = 0;i < 20;i++){
			try{
				buff.increWHydrogen(i);
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}//end class Hydrogen

class Oxygen implements Runnable{
	ReactionArea buff;
	
	public Oxygen(ReactionArea buff){
		this.buff = buff;
	}
	public void run(){
		for(int i = 0;i < 10;i++){
			try{
				buff.increWOxygen(i);
				Thread.sleep(2000);
			}catch(InterruptedException e){
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}//end class Oxygen

class Reactor implements Runnable{
	ReactionArea buff;
	
	public Reactor(ReactionArea buff){
		this.buff = buff;
	}
	public void run(){
		for(int i = 0;i <10 ;i++){
			try{
				if(i == 0){
					Thread.sleep(2000);
				}else{
					buff.react(i);
					Thread.sleep(50);
				}
			}catch(InterruptedException e){
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}//end class Reactor

class ReactionArea{
	public int waitingHydrogen = 0;
	public int waitingOxygen = 0;
	public int assignedHydrogen = 0;
	public int assignedOxygen = 0;
	
	public synchronized void increWHydrogen(int index){
		while(waitingHydrogen > 5){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		waitingHydrogen ++;
		System.out.println("The "+index+"th Hydrogen atom was added.");
		notifyAll();
	}
	
	public synchronized void increWOxygen(int index){
		while(waitingOxygen > 2){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		waitingOxygen ++;
		System.out.println("The "+index+"th Oxygen atom was added.");
		notifyAll();
	}
	
	public synchronized void react(int index){
		while(waitingHydrogen < 2 || waitingOxygen < 1){
			try{
				this.wait();
			}catch (InterruptedException e) {
				e.printStackTrace();
				
			}
			
		}
		waitingHydrogen -=2;
		waitingOxygen -=1;
		System.out.println("The "+index+"th water molecule was added.");
		notifyAll();
	}
}//end class Hydrogen


public class Lab5_6347 {

	public static void main(String[] args) {
			ReactionArea reactarea = new ReactionArea();
			Thread hydrogen = new Thread(new Hydrogen(reactarea));
			Thread oxygen = new Thread(new Oxygen(reactarea));
			Thread reactor = new Thread(new Reactor(reactarea));
			oxygen.start();
			hydrogen.start();
			reactor.start();

	}

}
