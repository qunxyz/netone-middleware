package com.jl.common;

public class SortUtil {
	/**
	 * 将存入的整形数组按从大到小的顺序排列
	 * @param data  int[]
	 * @param left  int	
	 * @param right int
	 */
	public void quickSort(Integer[] data, Integer left, Integer right){
		if(left > right-1)//退出遍历的条件
		return;
		Integer divNum = Partition(data,left,right);//获得1次排列后的数组中点
		quickSort(data,left,divNum-1);//迭代基准值左边的数组
		quickSort(data,divNum+1,right);//迭代基准值右边的数组
	}
	
	/**
	 * 划分数组，低端是比基准值大的值，高端是比基准值小的值
	 */
	private int Partition(Integer[] data,Integer left,Integer right){
		int sanderand = data[left];
		while(left < right){
			while(left < right && data[right]<= sanderand){//如果高端不存在比基准值小的数，下标向左移动
				right--;			
			}
			swap(data,left,right);//如果存在，则和高端当前的数交换位置
			while(left < right && data[left]>=sanderand){//如果低端不存在比基准值大的数，下标向右移动
				left++;				
			}
			swap(data,left,right);//如果存在，则和低端当前的数交换位置
		}
		
		data[left] = sanderand;//基准值放到中点
		return left;
	}
	
	/**
	 * 调换数组中2个指定位置的值
	 */
	private void swap(Integer[] data,Integer i,Integer j){
		int temp =data[i];
		data[i] = data[j];
		data[j] = temp;
	}
	
	/*
	 * 测试效果
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
