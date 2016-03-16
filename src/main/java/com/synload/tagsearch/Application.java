package com.synload.tagsearch;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synload.tagsearch.safebooru.BuildIndex;
import com.synload.tagsearch.safebooru.BuildTagIndex;
import com.synload.tagsearch.searchAPI.fulltext.PartialSearch;
import com.synload.tagsearch.searchAPI.utils.Tag;
import com.synload.tagsearch.searchAPI.utils.TagAnd;

public class Application {
	public static String generateRandomWord()
	{
	    Random random = new Random();
	    char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
        for(int j = 0; j < word.length; j++)
        {
            word[j] = (char)('a' + random.nextInt(26));
        }
	    return new String(word);
	}
	public static void main(String[] args) {
		// start building index to search
		Tag animalEars = new Tag("animal_ears");
		Tag bunny = new Tag("bunny");
		Tag cat = new Tag("cat");
		Tag policeCar = new Tag("police_car");
		
		ObjectMapper mapper = new ObjectMapper();
		
		PartialSearch.addIndexEntry("police_car");
		PartialSearch.addIndexEntry("bunny");
		PartialSearch.addIndexEntry("cat");
		
		try {
			System.out.println(mapper.writeValueAsString(PartialSearch.positions));
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		/*for(int i=0;i<20000;i++){
			String word = generateRandomWord();
			PartialSearch.addIndexEntry(word);
			
		}*/
		(new Thread(new BuildTagIndex())).start();
		//Test partial search
		long start = System.currentTimeMillis() % 1000;
		System.out.print(PartialSearch.partialText("cat"));
		long end = System.currentTimeMillis() % 1000;
		System.out.println(end-start+"ms");
		
		Scanner keyboard = new Scanner(System.in);
		boolean close = false;
		while(!close){
			String cmd = keyboard.nextLine();
			if(!cmd.equals("end")){
				try {
					start = System.currentTimeMillis() % 1000;
					LinkedList<String> partials = PartialSearch.partialText(cmd);
					end = System.currentTimeMillis() % 1000;
					System.out.println(mapper.writeValueAsString(partials));
					System.out.println(end-start+"ms");
					System.out.println("Words: "+PartialSearch.wordList.size());
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				//PartialSearch.partialText(cmd)
			}else{
				close=true;
			}
		}
		
		//System.exit(1);
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
		start = System.currentTimeMillis() % 1000;
		for(long i :TagAnd.compare(animalEars, bunny).getPosts()){
			System.out.println(i);
		}
		end = System.currentTimeMillis() % 1000;
		System.out.println(end-start+"ms");
		
		start = System.currentTimeMillis() % 1000;
		for(long i :TagAnd.compare(animalEars, policeCar).getPosts()){
			System.out.println(i);
		}
		end = System.currentTimeMillis() % 1000;
		System.out.println(end-start+"ms");
		
		start = System.currentTimeMillis() % 1000;
		for(long i :TagAnd.compare(animalEars, cat).getPosts()){
			System.out.println(i);
		}
		end = System.currentTimeMillis() % 1000;
		System.out.println(end-start+"ms");
		
		start = System.currentTimeMillis() % 1000;
		for(long i :TagAnd.compare(TagAnd.compare(animalEars, bunny),cat).getPosts()){
			System.out.println(i);
		}
		end = System.currentTimeMillis() % 1000;
		System.out.println(end-start+"ms");
	}

}
