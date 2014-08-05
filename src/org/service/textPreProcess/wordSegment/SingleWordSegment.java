package org.service.textPreProcess.wordSegment;

import java.io.IOException;

public class SingleWordSegment {
	/**
	 * ͨ�������ַ��������ַ���ת��Ϊһ��һ���ĺ��֣�Ӣ�ĵ���Ҫ��һ�𲻽����з֡�
	 * @param oriStr
	 * @return
	 */
	public static String seg(String oriStr){
		String s;
		s=oriStr.replaceAll("(?i)No\\.", "-Num-").replaceAll("[0-9]+", "-nn-");
//        System.out.println(s);
        int index=0;
        
        char[] buff=new char[s.length()*3+1];
        char[] dstChar=new char[s.length()] ;//= s.toCharArray();
        s.getChars(0, s.length(), dstChar, 0);
        for (int j = 0; j < dstChar.length; j++) 
        {
        	if(dstChar[j] <= 0x1F || dstChar[j] >= 0x30 && dstChar[j] <= 0x39 || dstChar[j]>= 0x41 && dstChar[j] <= 0x5A || dstChar[j] >= 0x61 && dstChar[j]<= 0x7A )//|| ch == 0x2D || ch == 0x27
            {
                buff[index++]=dstChar[j] ;
        		//[a-zA-Z0-9]   //-'
        		//System.out.print(dstChar[j]+ "\t");
            }else if(dstChar[j]==0x20){
            	buff[index++]='\t';
            }else{
            	buff[index++]='\t';
            	buff[index++]=dstChar[j];
            	buff[index++]='\t';
            }
        }
        
//        buff[index] = '\0';
        return String.valueOf(buff).trim();
	}
	public static void main(String args[]) throws IOException {
        String s = "test��d��dsaf���д�3443n�й�43�й���0ewldfls=103NO.��007";
        System.out.println(seg(s));
    }
}
