package com.synload.tagsearch.searchAPI.fulltext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.synload.tagsearch.searchAPI.utils.Tag;
import com.synload.tagsearch.searchAPI.utils.TagAnd;

public class PartialSearch {
	public static HashMap<String, ArrayList<String>> frontIndex = new HashMap<String, ArrayList<String>>();
	public static HashMap<String, Tag> positions = new HashMap<String, Tag>();
	public static void addIndexEntry(String word){
		for(int i=0;i<word.length();i++){
			word.charAt(i);
		}
	}
	public static LinkedList<Tag> partialText(String searchFor){
		// Initial position
		char initialCondition = searchFor.charAt(0);
		if(frontIndex.containsKey(initialCondition)){
			ArrayList<String> initials = frontIndex.get(initialCondition);
			LinkedList<Tag> returnedValues = new LinkedList<Tag>();
			for(String cond : initials){
				if(!positions.containsKey(cond)){
					break;
				}
				Tag[] tags = buildList(cond, searchFor);
				Tag tmp = tags[0];
				for(int i=1;i<tags.length;i++){
					tmp = TagAnd.comparator(tags[i], tmp);
				}
				returnedValues.add(tmp);
			}
			return returnedValues;
		}
		return null;
	}
	public static Tag[] buildList( String cond, String searchFor){
		int pos = Integer.valueOf(cond.substring(1));
		Tag[] toCombine = new Tag[searchFor.length()];
		toCombine[0] = positions.get(cond);
		for(int i=1;i<searchFor.length();i++){
			String lookFor = searchFor.charAt(i)+""+(pos+i);
			if(positions.containsKey(lookFor)){
				toCombine[i]= positions.get(lookFor);
			}else{
				return null;
			}
		}
		return toCombine;
	}
}
