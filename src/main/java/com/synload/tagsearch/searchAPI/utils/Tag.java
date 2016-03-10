package com.synload.tagsearch.searchAPI.utils;

import java.util.LinkedList;

public class Tag {
	private LinkedList<Integer> posts = new LinkedList<Integer>();
	public Integer[] cap;
	private String name;
	public Tag(String tag){
		this.name=tag;
	}
	public void add( int post ){
		int hi = posts.size()-1;
		int lo=0;
		int pos = 0;
		if(hi==-1){
			pos = 0;
		}else{
			int i=(int)Math.floor((lo+hi)/2);
			while(true){
				if(posts.get(i) == post){
					pos=-1;
					break;
				}else if(i == hi){
					pos = i;
					break;
				}else if(posts.get(i) < post){
					lo=i;
					i=(int)Math.floor((lo+hi)/2);
				}else if(posts.get(i) > post){
					hi=i;
					i=(int)Math.floor((lo+hi)/2);
				}
			}
		}
		if(pos!=-1){
			posts.add(pos, post);
		}
	}
	public void cap(){
		cap = posts.toArray(new Integer[posts.size()]);
	}
	public LinkedList<Integer> getPosts() {
		return posts;
	}
	public void setPosts(LinkedList<Integer> posts) {
		this.posts = posts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
