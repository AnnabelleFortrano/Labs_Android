package com.example.androidlab5;

import java.util.LinkedList;
import java.util.List;

public class BreedRepository {
    private static BreedRepository instance;
    private List<Breed> breeds;
    private static Breed currentBreed;
    private BreedRepository() {

    }
    public static BreedRepository createInstance(List<Breed> breeds) {
        if(instance == null) {
            instance = new BreedRepository();
            instance.setBreeds(breeds);
        }
            return instance;
    }

    private void setBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }
    public String getBreedId(String name) {
        for(Breed breed : breeds) {
            if(breed.getName() == name) {
                return breed.getId();
            }
        }
        return "";
    }

    public static BreedRepository getInstance() {
        return instance;
    }
    public List<String> getBreedNames() {
        List<String> names = new LinkedList<>();
        for(Breed breed : breeds) {
            names.add(breed.getName());
        }
        return names;
    }
    public void setBreed(String name) {
        for(Breed breed : breeds) {
            if(breed.getName() == name) {
                BreedRepository.setCurrentBreed(breed);
            }
        }
    }


    public static void setCurrentBreed(Breed breed) {
        BreedRepository.currentBreed = breed;
    }
}
