package JavaAoo;

import java.util.ArrayList;

public abstract class Search {
	private ArrayList<String> keywords;
	private ArrayList<String> hashtags;
	private String language;
	
	public Search(){
		keywords = new ArrayList<String>();
		hashtags = new ArrayList<String>();
		language = "";

	}
	public Search(ArrayList<String> k,ArrayList<String> h,String l){
		keywords = k;
		hashtags = h;
		language = l;
		
	}

	public ArrayList<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}

	public ArrayList<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(ArrayList<String> hashtags) {
		this.hashtags = hashtags;
	}
		
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void add_kw(String kw){
		keywords.add(kw);
	}
	
	public void add_ht(String ht){
		hashtags.add(ht);
	}
}
