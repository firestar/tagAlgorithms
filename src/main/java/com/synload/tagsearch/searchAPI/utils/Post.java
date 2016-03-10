package com.synload.tagsearch.searchAPI.utils;

import java.util.HashMap;

import com.google.gson.internal.LinkedTreeMap;

public class Post {
	private static HashMap<Integer,Post> posts = new HashMap<Integer,Post>();
	private int id;
	private int height;
	private int width;
	private String directory;
	private String image;
	private String tags;
	private String rating;
	public Post(int id, String directory, String image, int width, int height, String tags, String rating){
		this.id = id;
		this.directory = directory;
		this.image = image;
		this.width = width;
		this.height = height;
		this.tags = tags;
		this.rating = rating;
	}
	public static void create(LinkedTreeMap p){
		int id = (int)Math.round(Double.valueOf(p.get("id").toString()));
		if(!posts.containsKey(id)){
			posts.put(
				id, 
				new Post(
					id, 
					p.get("directory").toString(), 
					p.get("image").toString(), 
					(int)Math.round(Double.valueOf(p.get("width").toString())),
					(int)Math.round(Double.valueOf(p.get("height").toString())),
					p.get("tags").toString(), 
					p.get("rating").toString()
				)
			);
		}
	}
}
