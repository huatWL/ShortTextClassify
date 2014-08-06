package org.service.textPreProcess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
	 * ����ֵdouble��˼�� double[0]Ϊ���ݵı�ţ�double[1]Ϊ���ݵ�Ȩ��
	 * @param dictPath
	 * @return
	 */
	public static HashMap<String, double[]> getWordList(String dictPath) {
		HashMap<String,double[]> wordList=new HashMap<String,double[]>();
		int FeatureNum=0;	
			//read the wordlist into hashmap and make every word map a unique number.
		try{
			String word;
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(dictPath),Constant.encoding));
			while((word=reader.readLine())!=null){
				String[] tmp=word.split("\t");
				if(tmp.length!=2){
					throw new Exception("���ݸ�ʽ����");
				}
				if (tmp[0].length()==0||tmp[0].matches("[0-9]+"))
					continue;

				double[] d=new double[2];
				d[0]=FeatureNum;d[1]=Double.valueOf(tmp[1]);
				wordList.put(tmp[0], d);
					FeatureNum++;
				}
				System.out.println(FeatureNum);
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		return wordList;
	}

	/**
	 * ��POI�����зֳɵ�һ��һ�еĵ��ֶ����ļ��У�ɸѡ���ظ��ĵ��֣��õ�poi�г��ֵ����е��ֵĴʵ䣬����Ӣ�ĵ��ʺͺ��ﵥ�֡�</br>
	 * @revised ���ֵ��м������ı�ȫ����Ϣ���õ��ĵ���ÿ���ʶ�Ӧһ����ȫ���ĵ��г��ֵĸ���
	 * @param infileName
	 * @param outfile
	 */
	public static void createDict(String infileName,String outfile) {
        File file = new File(infileName);
        BufferedReader reader = null;
        HashMap<String,Double> wordSet = new HashMap<String,Double>();
        int docNum=0;
        try {
            System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
            reader =new BufferedReader(new InputStreamReader(new FileInputStream(file),Constant.encoding));
            String tempString = null;
            
            while ((tempString = reader.readLine()) != null) {
            	String [] tmp=tempString.split("\t");//test when \t\t what is the result
            	docNum++;
            	HashSet<String> unique=new HashSet<String>();
            	//����poi���֣��õ����ظ����ַ�����
            	for (String x:tmp){
            		if(x.length()!=0 
            				&&!x.matches("[_+\\-&|!,(){}\\[\\]��������^/\"~*?:��.@'%]+")
            				//&&!x.matches("[a-zA-Z]") this maybe useful for those name as  " D.D.K.S"
            				&&!unique.contains(x.toLowerCase())){
            			unique.add(x.toLowerCase());
            		}
            	}
            	for(String x:unique){
            		if(wordSet.containsKey(x)){
            			wordSet.put(x,wordSet.get(x)+1);
            		}else{
            			wordSet.put(x, 1.0);
            		}
            	}
            	
            }
            System.out.println("WordSet size"+wordSet.size()+"\n"+"docNum:"+docNum);
            
            ArrayList<Map.Entry<String,Double>> l = new ArrayList<Map.Entry<String,Double>>(wordSet.entrySet());
    		Collections.sort(l, new Comparator<Map.Entry<String,Double>>(){
                public int compare(Map.Entry<String,Double> o1, Map.Entry<String,Double> o2) {   
                    if(o2.getValue() - o1.getValue() > 0)
                    	return 1;
                    else
                    	return -1;
                }   
            });
            
            
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile),Constant.encoding)); 
            for(Map.Entry<String,Double> t:l){
            	bw.write(t.getKey()+"\t"+t.getValue()/docNum+"\n");
            }
            bw.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
	public static void main(String[] args){
//		createDict("D:\\ʵ���\\��Ŀ\\�ѹ���ͼ\\POI��������\\tianjin test\\test\\segment.txt", "D:\\ʵ���\\��Ŀ\\�ѹ���ͼ\\POI��������\\tianjin test\\test\\newDict.txt");
		getWordList("D:\\ʵ���\\��Ŀ\\�ѹ���ͼ\\POI��������\\tianjin test\\test\\newDict.txt");
	}
}
