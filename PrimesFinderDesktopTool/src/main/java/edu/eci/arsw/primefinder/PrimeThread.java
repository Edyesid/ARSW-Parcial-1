package edu.eci.arsw.primefinder;

import java.math.BigInteger;

public class PrimeThread extends Thread{
	private BigInteger _a;
	private BigInteger _b;
	private PrimesResultSet prs;
	private boolean flag;
	
	public PrimeThread(int _a, int _b, PrimesResultSet prs) {
		this._a = new BigInteger(Integer.toString(_a));
		this._b = new BigInteger(Integer.toString(_b));
		this.prs = prs;
		flag = true;
	}
	
	public void run() {
		while (flag) {
			synchronized(this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				flag = false;
			}
		}
			
		PrimeFinder.findPrimes(_a, _b, prs);
        //System.out.println("Prime numbers found:");
        //System.out.println(prs.getPrimes());
	}
	
	public void pause() {
		flag = true;
	}
	
	public void reanudar() {
		synchronized(this) {
			notify();
		}
	}
	
}
