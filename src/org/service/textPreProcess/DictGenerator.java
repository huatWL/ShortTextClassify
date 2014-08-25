package org.service.textPreProcess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.util.Constant;
/**
 * The DictGenerator is used to get the dictionary of</br>
 * total corpus by reading the text from textSement </br>  
 * result file and filtering same words , strange </br>
 * symbols  and single character like A,x... then </br>
 * write them to a new file named like "dict.txt".
 * @author Victor
 *
 */
public class DictGenerator {
	/**
	 * word index start with zero 
	 * @param dictPath
	 * @return
	 */
	public static HashMap<String, Integer> getWordList(String dictPath) {
		HashMap<String,Integer> wordList=new HashMap<String,Integer>();
		int FeatureNum=0;	
			//read the wordlist into hashmap and make every word map a unique number.
		try{
			String word;
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(dictPath),Constant.encoding));
			while((word=reader.readLine())!=null){
				if (word.length()==0 || word.matches("[0-9]+") )
					continue;
				wordList.put(word, FeatureNum);
				FeatureNum++;
				}
				//System.out.println(FeatureNum);
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		return wordList;
	}
	public static HashSet<String> getSpecialWordList(String dictPath) {
		HashSet<String> wordList=new HashSet<String>();
		//read the wordlist into hashmap and make every word map a unique number.
		try{
			String word;
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(dictPath),Constant.encoding));
			while((word=reader.readLine())!=null){
				if (word.length()==0||word.matches("[0-9]+"))
					continue;
				wordList.add(word);
			}
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return wordList;
	}
	/**
	 * ��POI�����зֳɵ�һ��һ�еĵ��ֶ����ļ��У�ɸѡ���ظ��ĵ��֣��õ�poi�г��ֵ����е��ֵĴʵ䣬����Ӣ�ĵ��ʺͺ��ﵥ�֡�
	 * @param infileName
	 * @param outfile
	 */
	public static void createDict(String infileName,String outfile) {
        File file = new File(infileName);
        BufferedReader reader = null;
        Set<String> wordSet = new HashSet<String>();
        try {
            System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
            reader =new BufferedReader(new InputStreamReader(new FileInputStream(file),Constant.encoding));
            String tempString = null;
            
            while ((tempString = reader.readLine()) != null) {
            	String [] tmp=tempString.split("\t");//test when \t\t what is the result
            	
            	for (String x:tmp){
            		if(x.length()!=0 
            				&&!x.matches("[_+\\-&|!,(){}\\[\\]��������^/\"~*?:��.@'%]+")
            				//&&!x.matches("[a-zA-Z]") this maybe useful for those name as  " D.D.K.S"
            				&&!wordSet.contains(x.toLowerCase())){
            			wordSet.add(x.toLowerCase());//�Ѿ�ע�⵽��Сд�����������ܵ���
            		}
            	}
            }
            System.out.println("WordSet"+wordSet.size());
            reader.close();
            storeDict(outfile,wordSet);
            
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
	private static void storeDict(String outFile,Set<String> wordSet){
		try{
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),Constant.encoding)); 
	        for(String t:wordSet){
	        	bw.write(t+"\n");
	        }
	        bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void dictMerge(String dict1,String dict2,String finalDict){
		//
		HashSet<String> d1=getSpecialWordList(dict1);
		HashSet<String> d2=getSpecialWordList(dict2);
		if(d1.size()>d2.size()){//make dict size d2>d1
			HashSet<String> d3;
			d3=d1;
			d1=d2;
			d2=d3;
		}
		for(String term:d1){
			if(!d2.contains(term)){
				d2.add(term);
			}
		}
		storeDict(finalDict, d2);
	}
	public static void main(String[] args){
		String path="D:\\ʵ���\\��Ŀ\\�ѹ���ͼ\\POI��������\\";
//		String oriFile=path+"resource\\all_suffix.txt";
//		String segFile=path+"tmp\\all_suffix.seg";
		String dictFile=path+"tmp\\all_suffix_specialDict.txt";
		String finalDict=path+"tmp\\finalDict.txt";
		String dict2=path+"tmp\\dict.txt";
//		TextSegment.segment(oriFile,segFile);
//		createDict(segFile, dictFile);
		dictMerge(dictFile, dict2, finalDict);
	}
}
