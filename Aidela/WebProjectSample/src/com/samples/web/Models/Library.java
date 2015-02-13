package com.samples.web.Models;

import java.util.ArrayList;

public class Library {
	private static Library instance = null;
	
	public ArrayList<Category> Categories;

	public static Library getInstance() {
        if(instance == null) {
            instance = new Library();
        }
        return instance;
    } 
    
	protected Library() {
		Categories = new ArrayList<Category>();
		
		addCategory("Fiction");
		addCategory("Non-Fiction");
		addCategory("Philosophy");
		addCategory("Biography");
		addCategory("Travel");
	}

	public boolean addCategory(String name) {
		Category category = new Category(name);
		if(!Categories.contains(category)) {
			Categories.add(category);
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<Category> getCategories() {
		return Categories;
	}
	
}
