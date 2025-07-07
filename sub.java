import java.util.*;
public class sub{
    public static void main(String args[]){
        Scanner sc=new Scanner (System.in);
        int n=sc.nextInt();
        int arr[]=new int[n];

        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        
int max=0;



    for(int i=0;i<n;i++){
        int count=0;
        for(int j=i;j<n;j++){
            if(arr[i]==arr[j]){
                count++;
            }
            else{
                break;  
            }
        }
    
        if(count>max){
            max=count;
        }
    }
System.out.print(max);
        sc.close();
         

    }
}