package linktester;

public class BrokenLink {
	
	private String page;
	private String target;
	private static int count;
	
	public BrokenLink(String page, String target) {
		this.page = page;
		this.target = target;
		count++;
	}
	
	public String getPage() { return page; }
	public String getTarget() { return target; }
	public static int getCount() { return count; }
	
	public String toString() {
		return "The page with the url " + page + " has the broken link " + target;
	}
}
