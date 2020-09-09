package edu.eci.arsw.api.primesrepo.service;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@Service
public class PrimeServiceStub implements PrimeService {
	private List<FoundPrime> list = new ArrayList<>();
	
	public PrimeServiceStub() {
		list.add(new FoundPrime(1));
	}
	
    @Override
    public void addFoundPrime( FoundPrime foundPrime )
    {
        list.add(foundPrime);
    }

    @Override
    public List<FoundPrime> getFoundPrimes()
    {
        return list;
    }

    @Override
    public FoundPrime getPrime( String prime )
    {
    	
        for (int i = 0; i < list.size(); i++) {
        	if(list.get(i).getPrime() == prime) {
        		return list.get(i);
        	}
        }
        return null;
    }
}
