package org.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * ������ϵ�Ľ����������ܵ�׼ȷ�ʣ��ڸ��������ϵ�׼ȷ�ʣ��ٻ��ʺ�F1ֵ��</br>
 * ��������Ҫ��ӵ��Ǻ�ƽ����΢ƽ����
 * @author Victor
 *
 */
public class Criteria {
	/**
	 * ��ȡԭʼ�ĺ�Ԥ���ÿ����������𣬶Աȼ��㣬���õ��������ķ���ָ�ꡣ
	 * @param oriClassFile
	 * @param predictClassFile
	 * @param resultFile
	 */
	public static void calCriteria(String oriClassFile,String predictClassFile,String resultFile){
		ArrayList<Integer> oriClass=new ArrayList<Integer>();
		ArrayList<Integer> predClass=new ArrayList<Integer>();
		try {
			BufferedReader   inOri   =   new   BufferedReader(new InputStreamReader (new FileInputStream(oriClassFile),Constant.encoding));
			BufferedReader   inPred   =   new   BufferedReader(new InputStreamReader (new FileInputStream(predictClassFile),Constant.encoding));
			String tmp;
			while((tmp=inOri.readLine())!=null){
				if(tmp.trim()!=null){
					oriClass.add(Integer.valueOf(tmp.trim()));
				}
			}
			while((tmp=inPred.readLine())!=null){
				if(tmp.trim()!=null){
					predClass.add(Integer.valueOf(tmp.trim()));
				}
			}
			
			inPred.close();
			inOri.close();
			if(oriClass.size()!=predClass.size()){
				System.out.println("ԭʼ��Ԥ���ʵ������ͬ���˳�...");
				return ;
			}
			
			//begin to cal the criteria  
			int labelNum=19;
			
			int[] right= new int[labelNum];
			int[] predict= new int[labelNum];
			int[] test= new int[labelNum];
			int length=oriClass.size();
			int pos,pos1;
			for(int i=0;i<length;i++){
				pos=predClass.get(i)-1;
				predict[pos]++;
				pos1=oriClass.get(i)-1;
				test[pos1]++;
				if(pos==pos1){
					right[pos]++;
				}
			}
			float [][] criteria=new float[labelNum][3];
			File f=new File(resultFile);
			if(!f.exists()){
				f.createNewFile();
			}
			BufferedWriter out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),Constant.encoding));
			out.write("class\t oriCount\t predCount\t rightCount\t precision\t recall\t F1\n");
			
			int totalRight=0;
			for(int i=0;i<labelNum;i++){
				out.write((i+1)+"\t"+test[i]+"\t"+predict[i]+"\t"+right[i]+"\t");
				if(right[i]!=0){
					criteria[i][0]=(float)right[i]/predict[i];
					criteria[i][1]=(float)right[i]/test[i];
					if(criteria[i][0]+criteria[i][1]>0){
						criteria[i][2]=2*criteria[i][0]*criteria[i][1]/(criteria[i][0]+criteria[i][1]);
					}
					out.write(criteria[i][0]+"\t"+criteria[i][1]+"\t"+criteria[i][2]);
					totalRight+=right[i];
				}
				out.write("\n");
			}
			out.write("total precision rate is "+(float)totalRight/length);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public static void main(String[] args){
		calCriteria("D:\\ʵ���\\��Ŀ\\�ѹ���ͼ\\POI��������\\1000_oriClass_num.txt", "D:\\ʵ���\\��Ŀ\\�ѹ���ͼ\\POI��������\\1000_predict.txt", "D:\\ʵ���\\��Ŀ\\�ѹ���ͼ\\POI��������\\assessment.txt");
		
	}

}
