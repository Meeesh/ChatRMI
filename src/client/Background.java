package thePack;

public class Background implements Runnable{
    ClientGui client;
    
    public Background(ClientGui cli){
        client = cli;
    }
    
    
    @Override
    public void run(){
        //here ask every 3 seconds for the new messages.
        try{
        	while(true){
	        	client.clientUpdate();
	            Thread.sleep(3000);
        	}
        }
        catch(InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
}
