package classes;

import java.io.*;
import java.util.*;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
@SuppressWarnings({"rawtypes","unchecked"})
public class Apriori implements Comparable{
	protected LinkedList list = new LinkedList();
	protected Object name;
	String uuid;
	public String minsupport; public String minconfidence;
	protected int size;
	protected FrequentItem _freqSet1;
	protected FrequentItem _freqSet2;
	int i =1,j=1;
	public ArrayList listRules;
	private static final String AMAZON_ACCESS_KEY = "YOUR AMAZON ACCESS KEY";
    private static final String AMAZON_SECRET_KEY = "AMAZON SECRET KEY";
	private static String bucketName     = "YOUR S3 BUCKET NAME WHICH SAVES THE PUBLIC DATA SET FILES";
    private static String bucketName2     = "YOUR S3 BUCKET NAME WHICH SAVES GENERATED ASSOCIATIONS RULE FILES CORRESPOSDIND TO PUBLIC DATA SET FILES ";
	protected static String keyName  ;
	protected static  String keyName2 ;
	BasicAWSCredentials awsCredentials;
    AmazonS3 s3;
	Apriori run;
	Boolean flag= false;

	public Apriori(Object name,int size) {
		this.name = name;
		this.size = size;
	}
	@Override
	public boolean equals(Object o) {
		return ((Apriori) o).name.equals(name);
	}
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	public String toStringto()
	{
		return name.toString();
	}

	public Apriori(FrequentItem _freqSet1, FrequentItem _freqSet2) {
		this._freqSet1 = _freqSet1;
		this._freqSet2 = _freqSet2;
	}
	public FrequentItem get_freqSet1() {
		return _freqSet1;
	}
	public FrequentItem get_freqSet2() {
		return _freqSet2;
	}
	@Override
	public int compareTo(Object o) {
		return 0;
	}
	@Override
	public String toString() {
		return "(" + _freqSet1 + ") => (" + _freqSet2 + ")";
	}
	
	@SuppressWarnings("static-access")
	public Apriori(String minsupport,String minconfidence,String uuid)throws IOException
	{
		this.awsCredentials = new BasicAWSCredentials(AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY);
        this.s3 = new AmazonS3Client(awsCredentials);
		this.keyName = uuid;
		this.keyName2 = "result-"+uuid;
		this.minconfidence = minconfidence;
		this.minsupport = minsupport;

try
{
s3.putObject(new PutObjectRequest(bucketName2, keyName2, createSampleFile()));
}
catch (AmazonServiceException ase) {
    System.out.println("Caught an AmazonServiceException, which " +
    		"means your request made it " +
            "to Amazon S3, but was rejected with an error response" +
            " for some reason.");
    System.out.println("Error Message:    " + ase.getMessage());
    System.out.println("HTTP Status Code: " + ase.getStatusCode());
    System.out.println("AWS Error Code:   " + ase.getErrorCode());
    System.out.println("Error Type:       " + ase.getErrorType());
    System.out.println("Request ID:       " + ase.getRequestId());
} catch (AmazonClientException ace) {
    System.out.println("Caught an AmazonClientException, which " +
    		"means the client encountered " +
            "an internal error while trying to " +
            "communicate with S3, " +
            "such as not being able to access the network.");
    System.out.println("Error Message: " + ace.getMessage());
}
}
	 private File createSampleFile() throws IOException {
		 Apriori apriori = new Apriori();
			listRules = apriori._apriori(Double.parseDouble(minsupport), Double.parseDouble(minconfidence));
	        File file = File.createTempFile("aws-java-sdk-", ".txt");
	        file.deleteOnExit();
	        //BufferedWriter writer = new BufferedWriter(new FileWriter(file), 32768);
	        PrintWriter writer = new PrintWriter(new FileOutputStream(file));
	        Iterator iterator = listRules.iterator();
			while (iterator.hasNext()) {
				run = (Apriori) iterator.next();
				writer.println(j+++" "+ run+"\t[SUPPORT: "+ apriori.support(run.get_freqSet1()._combination(
						run.get_freqSet2()))+ "\tCONFIDENCE: "+ apriori.confidence(run)+"]");
				}
			writer.println("--------END OF ASSOCIATION RULES----------");
	        writer.close();
	        return file;
	    }	
	
	public Apriori() throws IOException {
        try {
        	this.awsCredentials = new BasicAWSCredentials(AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY);
            this.s3 = new AmazonS3Client(awsCredentials);
            System.out.println("Downloading a new object to S3 from a file\n");
            S3Object object = s3.getObject(new GetObjectRequest(bucketName, keyName));
		LineNumberReader _out = new LineNumberReader(new InputStreamReader(object.getObjectContent()));
		String line = null;
		while ((line = _out.readLine()) != null) {
			FrequentItem freqSet = new FrequentItem();
			StringTokenizer token = new StringTokenizer(line, ",\t");
			while (token.hasMoreTokens()) {
				freqSet._addToItemSet(new Apriori(token.nextToken(),token.countTokens()));
			}
			if (freqSet.size() != 0) {
				list.add(freqSet);
			}
		}
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }  
	}
	public Iterator computeTran() {
		return list.iterator();
	}
	public int getTotalSupport() {
		return list.size();
	}
	public double support(FrequentItem itemset) {
		int _countItem = 0;
		Iterator itr = computeTran();
		while (itr.hasNext()) {
			FrequentItem _itemList = (FrequentItem) itr.next();
			if (_itemList._divide(itemset).size() == itemset.size()) {
				_countItem++;
			}
		}
		return ((double)_countItem) / getTotalSupport();
	}
	public double confidence(
			Apriori run) {
		FrequentItem union = run.get_freqSet1()._combination(
				run.get_freqSet2());
		return support(union)
				/ support(run.get_freqSet1());
	}
	public HashSet allItemSetOfSize1() {
		Iterator itr1 = computeTran();
		FrequentItem fItem1 = new FrequentItem();
		while (itr1.hasNext()) {
			FrequentItem freqItemSet = (FrequentItem) itr1.next();
			fItem1 = fItem1._combination(freqItemSet);
		}
		HashSet _allItemSet = new HashSet();
		Iterator itr2 = fItem1.getItemIterator();
		while (itr2.hasNext()) {
			Apriori item = (Apriori) itr2.next();
			FrequentItem fItem2 = new FrequentItem();
			fItem2._addToItemSet(item);
			_allItemSet.add(fItem2);
		}

		return _allItemSet;
	}
	public ArrayList _apriori(double minsup, double mincon) {
		HashSet[] newCand = new HashSet[600];
		newCand[1] = allItemSetOfSize1();
		ArrayList newArrayList = new ArrayList();
		for (int i = 1; i < 600 && !newCand[i].isEmpty(); i++)
		{
			newCand[i+1] = new LinkedHashSet();
			for (Iterator itr1 = newCand[i].iterator();itr1.hasNext();)
			{
				FrequentItem itemset1 = (FrequentItem) itr1.next();
				for (Iterator itr2 = newCand[i].iterator(); itr2.hasNext();)
				{
					FrequentItem itemset2 = (FrequentItem) itr2.next();
					if (itemset1._divide(itemset2).size() == i - 1) 
					{
						FrequentItem candItemSet = itemset1._combination(itemset2);
						assert(candItemSet.size() == i + 1);
						if (support(candItemSet) > minsup) {
							newCand[i + 1].add(candItemSet);
						}
					}
				}
			}
		}

		for (int i = 1; i < 600 && !newCand[i].isEmpty(); i++) {
			for (Iterator itrCand = newCand[i].iterator(); itrCand
					.hasNext();) {
				FrequentItem candItemSet = (FrequentItem) itrCand.next();
				for (Iterator itr = candItemSet
						.createNewItemsets().iterator(); itr
						.hasNext();) {
					FrequentItem fitem = (FrequentItem) itr.next();
					FrequentItem _freqSet1 = fitem;
					FrequentItem _freqSet2 = candItemSet._remove(fitem);
					Apriori candValue = new Apriori(_freqSet1, _freqSet2);
					if (confidence(candValue) > mincon) {
						newArrayList.add(candValue);

					}
				}
			}
		}
		return newArrayList;
	}
	

	
	
}
