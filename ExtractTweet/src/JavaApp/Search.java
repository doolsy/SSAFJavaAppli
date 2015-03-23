package JavaApp;

import java.util.ArrayList;
import java.util.Date;

public abstract class Search {
	
	protected String search_title;
	protected Date search_time;
	protected ArrayList<String> keywords;
	protected ArrayList<String> hashtags;
	protected String language;
	protected ArrayList<String> users;
	protected ArrayList<String> in_reply_to;
	protected int nb_rt_min;
	protected int nb_rt_max;
	
	
	public Search(){
		search_title ="";
		search_time = new Date();
		keywords = new ArrayList<String>();
		hashtags = new ArrayList<String>();
		users = new ArrayList<String>();
		in_reply_to = new ArrayList<String>();
		language = "";
		nb_rt_min = nb_rt_max =0;
 
	}

	public Search(ArrayList<String> keywords, ArrayList<String> hashtags,
			String language, ArrayList<String> users,
			ArrayList<String> in_reply_to, int nb_rt_min, int nb_rt_max) {
		super();
		this.keywords = keywords;
		this.hashtags = hashtags;
		this.language = language;
		this.users = users;
		this.in_reply_to = in_reply_to;
		this.nb_rt_min = nb_rt_min;
		this.nb_rt_max = nb_rt_max;
	}


	public String getSearch_title() {
		return search_title;
	}

	public void setSearch_title(String search_title) {
		this.search_title = search_title;
	}

	
	public Date getSearch_time() {
		return search_time;
	}

	public void setSearch_time(Date search_time) {
		this.search_time = search_time;
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

	public ArrayList<String> getusers() {
		return users;
	}


	public void setusers(ArrayList<String> users) {
		this.users = users;
	}


	public ArrayList<String> getin_reply_to() {
		return in_reply_to;
	}


	public void setin_reply_to(ArrayList<String> in_reply_to) {
		this.in_reply_to = in_reply_to;
	}

	

	public ArrayList<String> getUsers() {
		return users;
	}


	public void setUsers(ArrayList<String> users) {
		this.users = users;
	}


	public ArrayList<String> getIn_reply_to() {
		return in_reply_to;
	}


	public void setIn_reply_to(ArrayList<String> in_reply_to) {
		this.in_reply_to = in_reply_to;
	}

	public int getNb_rt_min() {
		return nb_rt_min;
	}

	public void setNb_rt_min(int nb_rt_min) {
		this.nb_rt_min = nb_rt_min;
	}

	public int getNb_rt_max() {
		return nb_rt_max;
	}

	public void setNb_rt_max(int nb_rt_max) {
		this.nb_rt_max = nb_rt_max;
	}

	public void add_user(String e)
	{
		users.add(e);
	}
	public void add_in_reply(String e)
	{
		in_reply_to.add(e);
	}
	public void add_kw(String kw){
		keywords.add(kw);
	}
	
	public void add_ht(String ht){
		hashtags.add(ht);
	}
}
