package com.shorty.shortener.service;

import com.shorty.shortener.dao.URLDao;
import com.shorty.shortener.entity.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Service
public class ShortenerService {
    @Autowired
    URLDao urlDao;

    @Autowired
    URLVerification urlVerification;

    @Value("${short.url.domain}")
    String shortURLDomain;


    public String shorten(String longUrl) throws Exception {
        //checking if the user provided URL is valid or not
        if(!isValidUrl(longUrl)){
            throw new Exception("Invalid url format.");
        }

        //Checking if the USER provided URL is already present in the Database.
        //If present then return the corresponding ShortURL
        Optional<URL> existing = urlDao.findByLongUrl(longUrl);
        if(existing.isPresent()){
            return existing.get().getShortUrl();
        }

        //Hashing the URL using SHA256 algorithm
        String SHA256 = SHAtoHex(getSHA(longUrl));

        //Encoding the SHA26 hash using base64
        String base64 = Base64.getEncoder().encodeToString(SHA256.getBytes());


        //The logic here is: we will select a window of length 7 from the base64 encoding to represent the short URL
        //Base64 uses 64 different characters. Hence, a 7 length string can 64*64*64*64*64*64*64 combinations
        //This number is huge and will suffice our needs
        //This substring should not be already existing in the Database. We need to verify that as well
        int i = 0;
        while(i+7<base64.length()){
            if(urlDao.findByShortUrl(base64.substring(i, i+7)).isEmpty())break;
        }

        URL url = new URL();
        url.setLongUrl(longUrl);
        url.setShortUrl(base64.substring(i, i+7));
        urlDao.save(url);
        return shortURLDomain+"/"+url.getShortUrl();
    }

    //Takes a stream of bytes as input and returns its corresponding Hexadecimal representation.
    String SHAtoHex(byte[] seq){
        BigInteger bigInteger = new BigInteger(1, seq);
        return bigInteger.toString(16);
    }

    //Used to verify if the user given URL is valid or not.
    private boolean isValidUrl(String longUrl) throws  Exception{
        return urlVerification.checkIfValidURL(longUrl);
    }

    //Hashes the given string using SHA-256 algorithm and returns a byte stream.
    byte[] getSHA(String s) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(s.getBytes(StandardCharsets.UTF_8));
    }


    public String redirect(String shortUrl){

        Optional<URL> url = urlDao.findByShortUrl(shortUrl);

        if(url.isPresent())return url.get().getLongUrl();
        return "";

    }
}
