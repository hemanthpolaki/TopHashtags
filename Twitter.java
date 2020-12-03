import java.util.*;

// Custom comparator to sort in descending order
class SortDescendingly implements Comparator<Map.Entry<String, Integer>>{
    @Override
    public int compare(Map.Entry<String,Integer> e1, Map.Entry<String,Integer> e2){
        return (e2.getValue()).compareTo(e1.getValue());
    }
}

class TweetHandler {

    private Map<String, Integer> hmap = new HashMap<String, Integer>();
    private List<Map.Entry<String, Integer>> list;
    private HashMap<String, Integer> top10Hashtags;

    // Function to process tweet text
    // Takes tweet as input, splits, filters hashtags (tokens that're starting with '#').
    // Filtered hashtag frequencies are maintained in hashmap(hmap)
    public void processTweet(String tweet) {
        for( String token: tweet.split(" ") ) {
            if( token.charAt(0) == '#' ) {
                String hashtag = token.substring(1);
                if( hmap.get(hashtag) != null ) {
                    hmap.put( hashtag, hmap.get(hashtag) + 1 );
                }
                else {
                    hmap.put( hashtag, 1 );
                }
            }
        }
    }

    // Function to find top 10 hashtags
    // Idea is to move entries of hashmap(hmap) to linkedlist(list), sort them and move top 10 entries 
    //      to another hashmap(top10HashTags).
    public void findTop10Hashtags () {
        list = new LinkedList<Map.Entry<String, Integer>>( hmap.entrySet() );
        Collections.sort( list, new SortDescendingly() );
        top10Hashtags = new LinkedHashMap<String, Integer>();
        for( int i=0; i<list.size() && i<10; i++ ) {
            top10Hashtags.put( list.get(i).getKey(), list.get(i).getValue() );
        }
    }

    // Function to print top 10 hashtags
    public void printTop10Hashtags() {
        System.out.println("\n\nTop 10 hashtags with their counts: ");
        for( Map.Entry<String, Integer> entry: top10Hashtags.entrySet() ) {
            System.out.println(entry.getKey() + "\t\t\t" + entry.getValue());
        }
    }
}

class Twitter {

    // Necessary variables declarations
    static Scanner sc = new Scanner(System.in);
    static String tweet;
    static Long noOfTweets;

    public static void main( String args[] ) {

        // Taking the tweets as input and passing each tweet for processing
        System.out.print("Enter no. of tweets: ");
        noOfTweets = sc.nextLong();
        sc.nextLine();
        TweetHandler tweetHandler = new TweetHandler();
        for( Long i=0L; i<noOfTweets; i++ ) {
            tweet = sc.nextLine();
            tweetHandler.processTweet( tweet );                                      // Passing tweet text for processing
        }

        // To find out top 10 hashtags among all hashtags
        tweetHandler.findTop10Hashtags();

        // To print top 10 hashtags
        tweetHandler.printTop10Hashtags();
        
        sc.close();
    }
}
