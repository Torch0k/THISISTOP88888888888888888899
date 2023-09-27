package com.example.myenotherwebregister.Services;

import com.example.myenotherwebregister.Repository.BunkerPostRepository;
import com.example.myenotherwebregister.model.BunkerPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BunkerPostService {
    @Autowired
    private BunkerPostRepository bunkerPostRepository;
    public void saveBunkerPost(BunkerPost bunkerPost){
        bunkerPostRepository.save(bunkerPost);
    }
    public List<BunkerPost> getAllBunkerMessage(){return bunkerPostRepository.findAll();}

}
