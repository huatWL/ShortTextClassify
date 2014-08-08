package org.service.textPreProcess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import org.util.Constant;
/**
 * 
 }  
 * @author Victor
 *
 */
public class Segment2Vector {
	/**
	 * �����ֵ佫�����POI�����ı�ת��ΪmySVMform��libsvmForm��shortForm����ʽ��������ʾ
	 * @param docTermFile
	 * @param docVecFileString
	 * @param form
	 */
	public static void getDocVecFromQiefenText(String dictFile,String specialDictPath,String docTermFile,String docVecFileString,String form){
		HashMap<String,Integer> wordList=DictGenerator.getWordList(dictFile);
		HashSet<String> specialDict=DictGenerator.getSpecialWordList(specialDictPath);
		System.out.println("Feature num:"+wordList.size()+"Special DictSize:"+specialDict.size());
		//read docTerm.txt to get the doc vector
		try{
			BufferedWriter out=null;
	    	File docVecFile=new File(docVecFileString);
	    	 if (!docVecFile.exists()) {
	    		 docVecFile.createNewFile();
	    	 }
	        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docVecFile),Constant.encoding));  
	        String termString;
			BufferedReader reader=new BufferedReader(new InputStreamReader (new FileInputStream(docTermFile),Constant.encoding));
			while((termString=reader.readLine())!=null){
				TreeMap<Integer,Integer> docArray=new TreeMap<Integer,Integer>();
				String[] docTermList=termString.split("\t");
				Integer pos=null;
				int length=docTermList.length;
				//int blankCount=0;
				int half=length/2;
				for(int i=0;i<length;i++){
					String term=docTermList[i].trim().toLowerCase();
					if(term.length()!=0 && (pos=wordList.get(term))!=null){
						//�ô��ڴʵ��д���
						int weight=1;
						if(i>half && specialDict.contains(term)){
							weight=i;
						}
						if(docArray.containsKey(pos)){
							docArray.put(pos,docArray.get(pos)+weight);
						}else{
							docArray.put(pos,weight);
						}
					}
				}
				//write to docVec.txt
				String docVec="";
				String className="";
					
				if(form.equalsIgnoreCase("shortForm")){
					docVec=shortForm(docArray);
				}else if(form.equalsIgnoreCase("libSvm")){
					docVec=libsvmForm(className, docArray);
				}
				out.write(docVec);
			}
			out.close();
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * �����ֵ佫�����POI�����ı�ת��ΪmySVMform��libsvmForm��shortForm����ʽ��������ʾ
	 * @param segmentResult  POI�����ַ���
	 * @param docVecFileString ������ı�λ��
	 * @param form
	 */
	public static void getDocVecFromSegmentString(String dictPath,String segmentResult,String docVecFileString,String form){
		HashMap<String,Integer> wordList=DictGenerator.getWordList(dictPath);
		int FeatureNum=wordList.size();
		System.out.println("Feature num:"+FeatureNum);
		
		//read docTerm.txt to get the doc vector
		try{
			BufferedWriter out=null;
	    	File docVecFile=new File(docVecFileString);
	    	 if (!docVecFile.exists()) {
	    		 docVecFile.createNewFile();
	    	 }
	        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docVecFile),Constant.encoding));  
	        TreeMap<Integer,Integer> docArray=new TreeMap<Integer,Integer>();
			String[] docTermList=segmentResult.split("\t");
			Integer pos=null;
			for(String term:docTermList){
				if((pos=wordList.get(term.trim().toLowerCase())) != null){
					if(docArray.containsKey(pos)){
						docArray.put(pos,docArray.get(pos)+1);
					}else{
						docArray.put(pos,1);
					}
				}
			}
				
			//write to docVec.txt
			String docVec="";
			String className="-1";
				
			if(form.equalsIgnoreCase("shortForm")){
				docVec=shortForm(docArray);
			}else if(form.equalsIgnoreCase("libSvm")){
				docVec=libsvmForm(className, docArray);
			}
			out.write(docVec);
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * �������ָ�ʽ������̫ռ�ط��ˣ����������ˣ����������ˡ�
	private static String mySvmForm(String className,TreeMap<Integer,Integer>docArray){
		String docVec="";
		if(className!="")
			docVec+=className+"\t";
		
		for(Integer x:docArray){
			docVec+=x.toString()+"\t";
		}
		docVec+="\n";
		return docVec;
	}*/
	private static String libsvmForm(String className,TreeMap<Integer,Integer>docArray){
		String docVec="";
		if(className!="")
			docVec+=className+"\t";
		
		for(Map.Entry<Integer,Integer> t:docArray.entrySet()){
			docVec+=String.valueOf(t.getKey()+1)+":"+String.valueOf(t.getValue())+" ";
		}
		docVec+="\n";
		return docVec;
	}
	private static String shortForm(TreeMap<Integer,Integer>docArray){
		String docVec="";
		for(Map.Entry<Integer,Integer> t:docArray.entrySet()){
			int key=t.getKey();
			int count=t.getValue();
			for(int j=0;j<count;j++){
				docVec+=String.valueOf(key+1)+" ";
			}
		}

		if(docVec==""){
			System.out.println("zero");
			//return "";  ��ʹ�ǿ�ֵ��Ҳд���ļ��У��������ж�Ӧ�������Ļ��Ƚ������ҵ���Ӧ��������ĸ���
		}
		docVec+="-1"+"\n";
		return docVec;
	}
	public static void main(String[] args){
		//System.out.println(System.getProperty("java.class.path")+"\n"+System.getProperty("user.dir"));
		//String s = "test��d��dsaf���д�3443n�й�43�й���0ewldfls=103NO.��007";  
		//getDocVecFromText("D:\\Project\\Java\\PoiClassify\\tmp\\data\\dict.txt", SingleWordSegment.seg(s), "D:\\Project\\Java\\PoiClassify\\tmp\\data\\vec.txt", "libSvm");
		//getDocVecFromQiefenText("D:\\ʵ���\\��Ŀ\\�ѹ���ͼ\\POI��������\\tmp\\dict.txt","D:\\ʵ���\\��Ŀ\\�ѹ���ͼ\\POI��������\\tmp\\segment.txt", "D:\\ʵ���\\��Ŀ\\�ѹ���ͼ\\POI��������\\tmp\\docVec.txt", "libsvm");
		getDocVecFromSegmentString("D:\\ʵ���\\��Ŀ\\�ѹ���ͼ\\POI��������\\tmp\\dict.txt","��		��		��		��		��		��		·		��		��		��		��		��		��		˾", "D:\\ʵ���\\��Ŀ\\�ѹ���ͼ\\POI��������\\tmp\\docVec.txt", "libsvm");
	}
}
