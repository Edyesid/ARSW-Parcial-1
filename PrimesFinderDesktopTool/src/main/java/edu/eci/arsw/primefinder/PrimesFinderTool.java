package edu.eci.arsw.primefinder;
import edu.eci.arsw.mouseutils.MouseMovementMonitor;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class PrimesFinderTool {
	private static int NUMTHREAD = 4;
	private static CopyOnWriteArrayList<PrimeThread> lista;
	private static boolean inicio;
	public static void main(String[] args) {
		    inicio = true;       
            int maxPrim = 100;
            
            PrimesResultSet prs = new PrimesResultSet("john");
            
            lista = new CopyOnWriteArrayList<PrimeThread>();
            
            boolean check = (maxPrim % NUMTHREAD == 0);
            
            int div = maxPrim / NUMTHREAD;
            
            int mod = (maxPrim % NUMTHREAD);
            
            if(check) {
            	
            	for (int i = 0; i < NUMTHREAD; i++) {
            		lista.add(new PrimeThread(i * div, (i * div) + div,prs));
            	}
            	
            } else {
            	for (int j = 0; j < NUMTHREAD; j++) {
            		if (j == NUMTHREAD - 1) {
            			lista.add(new PrimeThread(j * div, div + mod + (j * div), prs));
            		} else {
            			lista.add(new PrimeThread(j * div, (j * div) + div, prs));
            		}
            	}
            }

            for (PrimeThread thread : lista) {
            	thread.start();
            }
            
            
            boolean tarea = true;
            while(tarea) {
                try {
                    //check every 10ms if the idle status (10 seconds without mouse
                    //activity) was reached. 
                    Thread.sleep(10);
                    if (MouseMovementMonitor.getInstance().getTimeSinceLastMouseMovement() > 10000) {
                        System.out.println("Idle CPU sin actividad ");
                        for (PrimeThread thread : lista) {
                        	thread.reanudar();
                        }
                        
                        for (PrimeThread thread : lista) {
                        	if (thread.isAlive()) {
                        		tarea = true;
                        	} else {
                        		thread.join();
                        		tarea = false;
                        	}
                        	
                        }
                    }
                    else{
                        System.out.println("User working again! actividad");
                        for (PrimeThread thread : lista) {
                        	thread.pause();
                        }
                        System.out.println("Prime numbers found:");
                        System.out.println(prs.getPrimes());
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrimesFinderTool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Prime numbers found:");
            System.out.println(prs.getPrimes());
            System.exit(0);
	}
	
}


