package com.synload.tagsearch.searchAPI.utils;

import java.util.LinkedList;

public class TagAnd {
	private LinkedList<Integer> posts = new LinkedList<Integer>();
	public TagAnd(){}
	public static Tag compare(Tag t1, Tag t2){
		if(t1.cap.length>t2.cap.length){
			return comparator(t2,t1);
		}else if(t1.cap.length<t2.cap.length){
			return comparator(t1,t2);
		}else{
			return comparator(t1,t2);
		}
	}
	public static Tag comparator(Tag t1, Tag t2){
		int pos = 0;
		int low = 0;
		int high = t2.cap.length-1;
		int prevLow = low;
		
		Tag t = new Tag(t1.getName()+"&"+t2.getName());
		
		int i = (int) Math.floor((low+high)/2);
		
		
		while(pos<t1.cap.length){
			if(low<=high){
				if(t2.cap[i].compareTo(t1.cap[pos])==0){ // found match set low to 1 + i
					t.getPosts().add(t2.cap[i]);
					low = i+1;
					high = t2.cap.length-1;
					i = (int) Math.floor((low+high)/2);
					pos++;
				}else if(t1.cap[pos] > t2.cap[i]){
					low = i+1;
					i = (int) Math.floor((low+high)/2);
				}else{
					high = i-1;
					i = (int) Math.floor((low+high)/2);
				}
			}else{
				pos++;
				low = prevLow;
				high = t2.cap.length-1;
				i = (int) Math.floor((low+high)/2);
			}
		}
		t.cap();
		return t;
	}
	public LinkedList<Integer> getPosts() {
		return posts;
	}
	public void setPosts(LinkedList<Integer> posts) {
		this.posts = posts;
	}
}
