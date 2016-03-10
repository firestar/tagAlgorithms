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

import sun.misc.IOUtils;

public class BuildIndex implements Runnable{
	private String url = "http://safebooru.org/index.php?page=dapi&s=post&q=index&pid=%page%&tags=%tag%&json=1";
	private String tag;
	private int page;
	private int totalPages;
	public BuildIndex( String tag, int page){
		this.tag = tag;
		this.page = 0;
	}
	public void run() {
		HttpClient client = HttpClients.createDefault();
		HttpGet getPage = new HttpGet(url.replace("%page%", String.valueOf(this.page)).replace("%tag%", this.tag));
		try {
			HttpResponse response = client.execute(getPage);
			byte[] data = IOUtils.readFully(response.getEntity().getContent(), (int)response.getEntity().getContentLength(), true);
			Gson gson = new Gson();
			String jsonString = new String(data);
			if(!jsonString.equals("") && !jsonString.equals("[]")){
				List<LinkedTreeMap> ul = gson.fromJson(jsonString, ArrayList.class);
				for(LinkedTreeMap s : ul){
					int id = (int) Math.round(Double.valueOf(s.get("id").toString()));
				}
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
