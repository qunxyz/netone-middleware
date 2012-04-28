package com.jl.common;

public class SortUtil {
	/**
	 * ��������������鰴�Ӵ�С��˳������
	 * @param data  int[]
	 * @param left  int	
	 * @param right int
	 */
	public void quickSort(Integer[] data, Integer left, Integer right){
		if(left > right-1)//�˳�����������
		return;
		Integer divNum = Partition(data,left,right);//���1�����к�������е�
		quickSort(data,left,divNum-1);//������׼ֵ��ߵ�����
		quickSort(data,divNum+1,right);//������׼ֵ�ұߵ�����
	}
	
	/**
	 * �������飬�Ͷ��ǱȻ�׼ֵ���ֵ���߶��ǱȻ�׼ֵС��ֵ
	 */
	private int Partition(Integer[] data,Integer left,Integer right){
		int sanderand = data[left];
		while(left < right){
			while(left < right && data[right]<= sanderand){//����߶˲����ڱȻ�׼ֵС�������±������ƶ�
				right--;			
			}
			swap(data,left,right);//������ڣ���͸߶˵�ǰ��������λ��
			while(left < right && data[left]>=sanderand){//����Ͷ˲����ڱȻ�׼ֵ��������±������ƶ�
				left++;				
			}
			swap(data,left,right);//������ڣ���͵Ͷ˵�ǰ��������λ��
		}
		
		data[left] = sanderand;//��׼ֵ�ŵ��е�
		return left;
	}
	
	/**
	 * ����������2��ָ��λ�õ�ֵ
	 */
	private void swap(Integer[] data,Integer i,Integer j){
		int temp =data[i];
		data[i] = data[j];
		data[j] = temp;
	}
	
	/*
	 * ����Ч��
	 */
	public static void main(String[] args) {
		SortUtil su = new SortUtil();
		Integer[] data = {20,22,30,9,1,50,100,57,3,10};
		System.out.print("before sort: ");
		for(Integer i=0;i<data.length;i++){
			System.out.print(data[i]+",");
		}
		System.out.println("");
		
		System.out.print("after sorted :");
		su.quickSort(data, 0, data.length-1);
		
		for(int i=0;i<data.length;i++){
			System.out.print(data[i]+",");
		}
	}
}
