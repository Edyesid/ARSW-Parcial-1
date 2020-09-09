package edu.eci.arsw.api.primesrepo;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.service.PrimeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@RestController
public class PrimesController
{
	@Autowired
    PrimeService primeService;


    @RequestMapping( value = "/primes", method = GET )
    public List<FoundPrime> getPrimes()
    {
        return primeService.getFoundPrimes();
    }
    
    @GetMapping("/primes/{primeNumber}")
    public ResponseEntity<?> primeNumberController(@PathVariable("primeNumber") String primeNumber) {
        try {
            FoundPrime data = primeService.getPrime(primeNumber);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Not Prime found.", HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/primes")
    public ResponseEntity<?> addPrimeController(@RequestBody FoundPrime primeObject) {
        try {
            primeService.addFoundPrime(primeObject);
            return new ResponseEntity<>(primeObject,HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Not possible", HttpStatus.CONFLICT);
        }
    }
}
