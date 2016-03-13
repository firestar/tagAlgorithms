package com.synload.tagsearch.searchAPI.fulltext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synload.tagsearch.searchAPI.utils.Tag;
import com.synload.tagsearch.searchAPI.utils.TagAnd;

public class PartialSearch {
	
	public static HashMap<String, ArrayList<String>> frontIndex = new HashMap<String, ArrayList<String>>();
	public static HashMap<String, Tag> positions = new HashMap<String, Tag>();
	public static long wordId=0;
	public static HashMap<Long, String> wordList = new HashMap<Long, String>();
	public static void addIndexEntry(String word){
		if(wordList.containsValue(word)){
			return;
		}
		//System.out.println(word);
		
		wordList.put(wordId, word);
		long id = wordId;
		for(int i=0;i<word.length();i++){
			String letter = String.valueOf(word.charAt(i));
			String pos = String.valueOf(i);
			if(!frontIndex.containsKey(letter)){
				frontIndex.put(letter, new ArrayList<String>());
			}
			if(!frontIndex.get(letter).contains(letter+""+pos)){
				frontIndex.get(letter).add(letter+""+pos);
			}
			
			if(positions.containsKey(letter+""+pos)){
				positions.get(letter+""+pos).add(id);
			}else{
				Tag t = new Tag(letter+""+pos);
				t.add(wordId);
				positions.put(letter+""+pos,t);
			}
		}
		wordId++;
	}
	public static LinkedList<String> partialText(String searchFor){
		// Initial position
		searchFor = searchFor.toLowerCase();
		LinkedList<String> foundTags = new LinkedList<String>();
		String initialCondition = String.valueOf(searchFor.charAt(0));
		if(frontIndex.containsKey(initialCondition)){
			ArrayList<String> initials = frontIndex.get(initialCondition);
			for(String cond : initials){
				if(!positions.containsKey(cond)){
					break;
				}
				Tag[] tags = buildList(cond, searchFor);
				if(tags!=null){
					Tag tmp = tags[0];
					for(int i=1;i<tags.length;i++){
						tmp = TagAnd.comparator(tags[i], tmp);
					}
					for(Long i : tmp.getPosts()){
						foundTags.add(wordList.get(i));
					}
				}
			}
			return foundTags;
		}
		return null;
	}
	public static Tag[] buildList( String cond, String searchFor){
		int pos = Integer.valueOf(cond.substring(1));
		Tag[] toCombine = new Tag[searchFor.length()];
		toCombine[0] = positions.get(cond);
		for(int i=1;i<searchFor.length();i++){
			String lookFor = searchFor.charAt(i)+""+String.valueOf(pos+i);
			if(positions.containsKey(lookFor)){
				toCombine[i]= positions.get(lookFor);
			}else{
				return null;
			}
		}
		return toCombine;
	}
}
