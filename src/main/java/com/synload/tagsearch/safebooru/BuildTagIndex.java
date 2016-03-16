package com.synload.tagsearch.safebooru;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import com.synload.tagsearch.searchAPI.fulltext.PartialSearch;
import sun.misc.IOUtils;

public class BuildTagIndex implements Runnable{
	private String url = "http://gelbooru.com/index.php?page=tags&s=list&pid=%page%";
	private int groupSize = 50;
	public void run(){
		int current = 0;
		while(true){
			HttpClient client = HttpClients.createDefault();
			HttpGet getPage = new HttpGet(url.replace("%page%", String.valueOf(current)));
			try {
				HttpResponse response = client.execute(getPage);
				byte[] data = IOUtils.readFully(response.getEntity().getContent(), (int)response.getEntity().getContentLength(), true);
				String html = new String(data, "UTF-8");
				try {
					Pattern regex = Pattern.compile("class=\"tag-type-(.*?)\"><a href=\"index\\.php\\?page=post&amp;s=list&amp;tags=(.*?)\">(.*?)</a></span></td>", Pattern.DOTALL);
					Matcher regexMatcher = regex.matcher(html);
					while (regexMatcher.find()) {
						PartialSearch.addIndexEntry(regexMatcher.group(2));
					}
				} catch (PatternSyntaxException ex) {
					// Syntax error in the regular expression
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			current += groupSize;
		}
	}

}
