package com.synload.tagsearch.searchAPI.utils;

import java.util.LinkedList;

public class TagAnd {
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
		int prevLow = 0;
		int i;
		Tag t = new Tag(t1.getName()+"&"+t2.getName());
		int maxItems = t1.cap.length;
		while(pos<maxItems){
			if(high>=low){
				i = (int) Math.floor((low+high)/2);
				//System.out.println("i:"+i+" high:"+high+" low:"+low+" compare:"+t2.cap[i]+" key:"+t1.cap[pos]+" size1:"+t1.cap.length+" size2:"+t2.cap.length +" pos:"+pos);
				if(t2.cap[i].equals(t1.cap[pos])){ // found match set low to i+1
					t.getPosts().add(t2.cap[i]);
					low = i+1;
					prevLow = i+1;
					high = t2.cap.length-1;
					pos++;
				}else if(high==low){
					low = prevLow;
					high = t2.cap.length-1;
					pos++;
				}else if(t2.cap[i] < t1.cap[pos]){
					low = i+1;
				}else{
					high = i-1;
				}
			}else{
				low = prevLow;
				high = t2.cap.length-1;
				pos++;
			}
			maxItems = t1.cap.length;
		}
		t.cap();
		return t;
	}
}
