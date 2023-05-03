package problem;

public class RateLimitingUsingTokenBucketFilter {

	public static void main(String[] args) throws InterruptedException {
		TokenBucketFilter filter=new TokenBucketFilter(3);
		
		Thread.sleep(3000);
		for(int i=0;i<10;i++)
			new Consumer(filter).start();
		
	}	
	
}
class Consumer extends Thread{
	TokenBucketFilter filter;
	
	public Consumer(TokenBucketFilter filter){
		this.filter=filter;
	}
	
	
	public void run() {
		
		try {
			filter.getToken();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class TokenBucketFilter
{
	
	private long MAX_TOKENS;
	private long lastRequestedTime=System.currentTimeMillis();
	private long possibleTokenLeft=0;
	public TokenBucketFilter(long maxTokens) {
		this.MAX_TOKENS=maxTokens;
	}
	
	
	public synchronized void getToken() throws InterruptedException {
		
		
		possibleTokenLeft+=(System.currentTimeMillis()-lastRequestedTime)/1000;
		possibleTokenLeft=Math.min(MAX_TOKENS, possibleTokenLeft);
		
		
		if(possibleTokenLeft>0) {
			possibleTokenLeft--;
		}else {
			Thread.sleep(1000);
		}
		
		lastRequestedTime=System.currentTimeMillis();
		System.out.println("Issued token to "+Thread.currentThread().getName()+" at "+lastRequestedTime/1000+" : "+possibleTokenLeft);
	}
	
	
	
}
