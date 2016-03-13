package com.synload.tagsearch.safebooru;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.synload.tagsearch.searchAPI.utils.Post;
import com.synload.tagsearch.searchAPI.utils.Tag;

import sun.misc.IOUtils;

public class BuildIndex implements Runnable{
	private String url = "http://safebooru.org/index.php?page=dapi&s=post&q=index&pid=%page%&tags=%tag%&json=1";
	private Tag tag;
	private int page;
	public BuildIndex( Tag tag, int page){
		this.tag = tag;
		this.page = page;
	}
	public void run() {
		if(page>20){
			return;
		}
		HttpClient client = HttpClients.createDefault();
		HttpGet getPage = new HttpGet(url.replace("%page%", String.valueOf(this.page)).replace("%tag%", this.tag.getName()));
		try {
			HttpResponse response = client.execute(getPage);
			byte[] data = IOUtils.readFully(response.getEntity().getContent(), (int)response.getEntity().getContentLength(), true);
			Gson gson = new Gson();
			String jsonString = new String(data);
			if(!jsonString.equals("") && !jsonString.equals("[]")){
				List<LinkedTreeMap> ul = gson.fromJson(jsonString, ArrayList.class);
				for(LinkedTreeMap s : ul){
					Post.create(s);
					long id = (long) Math.round(Double.valueOf(s.get("id").toString()));
					this.tag.add(id);
				}
				(new BuildIndex( tag, page+1)).run();
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
