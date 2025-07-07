import java.util.*;
public class subarray{
    public static void main(String args[]){
        Scanner sc=new Scanner (System.in);
        int n=sc.nextInt();
        int arr[]=new int[n];

        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        int k=3;
int max=Integer.MIN_VALUE;



    for(int i=0;i<n-k-1;i++){
        int sum=0;
        for(int j=i;j<k;j++){
sum+=arr[j];
        }
        k++;
        if(sum>max){
            max=sum;
        }
    }
System.out.print(max);
        sc.close();
         

    }
}