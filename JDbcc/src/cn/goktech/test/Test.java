package cn.goktech.test;

public class Test {
	//abc1231xd1111111
	public static void main(String[] args) {
		//测试
		int[] a ={5,1,2,3,8,1,4};
		for (int i = 0; i < a.length; i++) {
//			for (int k = 0; k < a.length; k++) {
//				System.out.print(a[k]+" ");
//			}
			System.out.println();
			for (int j = 0; j < a.length-1; j++) {
				if(a[j]>a[j+1]){
					int temp = a[j+1];
					a[j+1] = a[j];
					a[j] = temp;
				}
			}
			for (int k : a) {
				System.out.println(k);
			}
		}
	
	}
}
