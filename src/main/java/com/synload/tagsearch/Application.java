package com.synload.tagsearch;

import com.synload.tagsearch.safebooru.BuildIndex;
import com.synload.tagsearch.searchAPI.utils.Tag;
import com.synload.tagsearch.searchAPI.utils.TagAnd;

public class Application {

	public static void main(String[] args) {
		// start building index to search
		Tag animalEars = new Tag("animal_ears");
		Tag bunny = new Tag("bunny");
		Tag cat = new Tag("cat");
		Tag policeCar = new Tag("police_car");
		
		Thread f1 = new Thread(new BuildIndex(animalEars, 0));
		Thread f2 = new Thread(new BuildIndex(bunny, 0));
		Thread f3 = new Thread(new BuildIndex(cat, 0));
		Thread f4 = new Thread(new BuildIndex(policeCar, 0));
		
		f1.start();f2.start();f3.start();f4.start();
		
		while(f1.isAlive() || f2.isAlive() || f3.isAlive() || f4.isAlive()){
			System.out.println("waiting "+f1.isAlive()+":"+f2.isAlive()+":"+f3.isAlive()+":"+f4.isAlive());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		animalEars.cap();
		bunny.cap();
		cat.cap();
		policeCar.cap();
		
		System.out.println("done waiting! "+f1.isAlive()+":"+f2.isAlive()+":"+f3.isAlive()+":"+f4.isAlive());
		long start = System.currentTimeMillis() % 1000;
		for(int i :TagAnd.compare(animalEars, bunny).getPosts()){
			System.out.println(i);
		}
		long end = System.currentTimeMillis() % 1000;
		System.out.println(end-start+"ms");
		
		start = System.currentTimeMillis() % 1000;
		for(int i :TagAnd.compare(animalEars, policeCar).getPosts()){
			System.out.println(i);
		}
		end = System.currentTimeMillis() % 1000;
		System.out.println(end-start+"ms");
		
		start = System.currentTimeMillis() % 1000;
		for(int i :TagAnd.compare(animalEars, cat).getPosts()){
			System.out.println(i);
		}
		end = System.currentTimeMillis() % 1000;
		System.out.println(end-start+"ms");
		
		start = System.currentTimeMillis() % 1000;
		for(int i :TagAnd.compare(TagAnd.compare(animalEars, bunny),cat).getPosts()){
			System.out.println(i);
		}
		end = System.currentTimeMillis() % 1000;
		System.out.println(end-start+"ms");
	}

}
