package com.synload.tagsearch.searchAPI.utils;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;

public class Tag implements Serializable {
	public LinkedList<Long> posts = new LinkedList<Long>();
	public Long[] cap;
	private String name;
	public Tag(String tag){
		this.name=tag;
	}
	public void add( long post ){
		posts.add(post);
		Collections.sort(posts);
		this.cap();
		/*int hi = posts.size()-1;
		int lo=0;
		int pos = 0;
		if(hi==-1){
			pos = 0;
		}else{
			int i=(int)Math.round((lo+hi)/2);
			while(true){
				//System.out.println("i:"+i+" = "+post+" hi:"+hi+" lo:"+lo+" size:"+posts.size());
				if(posts.get(i).compareTo(post)==0){
					pos=-1;
					break;
				}else if(i==lo){
					pos=i;
					break;
				}else if(i==hi){
					pos=i;
					break;
				}else if(posts.get(i) < post){
					lo=i;
					i=(int)Math.round((lo+hi)/2);
				}else{
					hi=i;
					i=(int)Math.round((lo+hi)/2);
				}
			}
		}
		if(pos!=-1){
			posts.add(pos, post);
		}*/
	}
	public void cap(){
		cap = posts.toArray(new Long[posts.size()]);
	}
	public LinkedList<Long> getPosts() {
		return posts;
	}
	public void setPosts(LinkedList<Long> posts) {
		this.posts = posts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
