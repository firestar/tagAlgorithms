package com.synload.tagsearch;

import com.synload.tagsearch.safebooru.BuildIndex;

public class Application {

	public static void main(String[] args) {
		// start building index to search
		(new Thread(new BuildIndex("animal_ears",0))).start();
	}

}
