package JavaApp;

public class User_info {

	private int ut_id;
	private int iua_id;
	private String consumer_key;
	private String consumer_secret;
	private String access_token;
	private String access_token_secret;
	
	
	
	public User_info() {
		super();
	}

	public User_info(int ut_id,int iua_id,String consumer_key, String consumer_secret,
			String access_token, String access_token_secret) {
		super();
		this.ut_id = ut_id;
		this.iua_id = iua_id;
		this.consumer_key = consumer_key;
		this.consumer_secret = consumer_secret;
		this.access_token = access_token;
		this.access_token_secret = access_token_secret;
	}

	public String getConsumer_key() {
		return consumer_key;
	}

	public void setConsumer_key(String consumer_key) {
		this.consumer_key = consumer_key;
	}

	public String getConsumer_secret() {
		return consumer_secret;
	}

	public void setConsumer_secret(String consumer_secret) {
		this.consumer_secret = consumer_secret;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getAccess_token_secret() {
		return access_token_secret;
	}

	public void setAccess_token_secret(String access_token_secret) {
		this.access_token_secret = access_token_secret;
	}

	public int getId() {
		return iua_id;
	}

	public void setId(int id) {
		this.iua_id = id;
	}

	public int getUt_id() {
		return ut_id;
	}

	public void setUt_id(int ut_id) {
		this.ut_id = ut_id;
	}

	public int getIua_id() {
		return iua_id;
	}

	public void setIua_id(int iua_id) {
		this.iua_id = iua_id;
	}
	
	
	
}
