package org.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.util.Constant;

public class MapClass {
	private HashMap<String,Integer> map;

	
	// TODO ��ɵ���ģʽ
	public MapClass(){
		map=new HashMap<String,Integer>();
		map.put("����", 1);
		map.put("���ݷ���",2);
		map.put("��������",3);
		map.put("���ݻ���",4);
		map.put("���ز�",5);
		map.put("��˾��ҵ",6);
		map.put("���ﳡ��",7);
		map.put("��ͨ����",8);
		map.put("��������",9);
		map.put("���ξ���",10);
		map.put("��������",11);
		map.put("��������",12);
		map.put("����ý��",13);
		map.put("��������",14);
		map.put("ѧУ����",15);
		map.put("ҽ������",16);
		map.put("��������",17);
		map.put("��������",18);
		map.put("����",19);

	}
	/**
	 * ��map.txt�ж�ȡ���������ֵ�map
	 * ��Ԥ�������ļ����еõ�������match����Ӧ������������ļ���ͦ���Ŀ��������ⳤ�ȡ�
	 * ����Ӧ�����д��classNamefile�С���������Ǻ��ֵ�������ơ�
	 * @param numFile  Ԥ������
	 * @param classFile
	 */
    public static void getClassFromNum(String mapFile,String numFile,String classNameFile){
    	try{
	    	//read in map
	    	BufferedReader inMap=new BufferedReader(new InputStreamReader (new FileInputStream(mapFile),Constant.encoding));
	    	ArrayList<String> al= new ArrayList<String>();
			String tmp;
			while((tmp=inMap.readLine())!=null){
				String[] split=tmp.split(",");
				if(tmp.length()!=0&&split.length==2){
					al.add(Integer.valueOf(split[1])-1,split[0]);
				}
			}
			inMap.close();
			BufferedReader inNum=new BufferedReader(new InputStreamReader (new FileInputStream(numFile),Constant.encoding));
			
			String num=null;			
			BufferedWriter out=null;
			File f=new File(classNameFile);
			if(!f.exists()){
				f.createNewFile();
			}
			out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),Constant.encoding));
			while((num=inNum.readLine())!=null){
				out.write(al.get((int) (Double.valueOf(num)-1))+"\n");
			}
			inNum.close();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
    }
    /**
	 * ��map.txt�ж�ȡ���������ֵ�map
	 * ����ҳ�����Ԥ�������ļ��еõ�������match����Ӧ����� �������ļ��Ǽ�С�� ֻ��һ�����ݡ�
	 * ��󷵻�ֵΪ���ֶ�Ӧ�����
	 * @param numFile  Ԥ������
	 * @param classFile
	 */
    public static String getClassFromNum(String mapFile,String numFile){
    	try{
	    	//read in map
	    	BufferedReader inMap=new BufferedReader(new InputStreamReader (new FileInputStream(mapFile),Constant.encoding));
	    	ArrayList<String> al= new ArrayList<String>();
			String tmp;
			while((tmp=inMap.readLine())!=null){
				String[] split=tmp.split(",");
				if(tmp.length()!=0&&split.length==2){
					
					al.add(Integer.valueOf(split[1])-1,split[0]);
				}
			}
			inMap.close();
			BufferedReader inNum=new BufferedReader(new InputStreamReader (new FileInputStream(numFile),Constant.encoding));
			
			String num;
			num=inNum.readLine();
			inNum.close();
			return al.get((int)(Double.valueOf(num)-1));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
    }
    /**
     * ���������ӳ�������д��numFile��
     * @param classFile
     * @param numFile
     */
    public void Class2Num(String classFile,String numFile){
    	try {
			BufferedReader in=new BufferedReader(new InputStreamReader (new FileInputStream(classFile),Constant.encoding));
			String tmp;
			BufferedWriter out=null;
			File f=new File(numFile);
			if(!f.exists()){
				f.createNewFile();
			}
			out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),Constant.encoding));
			
			while((tmp=in.readLine())!=null){
				String name=tmp.trim();
				if(name.length()!=0&&map.containsKey(name)){
				    //System.out.println(name);
					out.write(map.get(name).toString());
				}
				out.write("\n");
			}
			in.close();
			out.close();
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
