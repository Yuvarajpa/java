import java.util.Arrays;
public class min{
    public static  void main(String args[]){
        int arr[]={0,4,3,2,1,6};
        int k=3;


int temp;
        for(int i=0;i<arr.length;i++){

            for(int j=i+1;j<arr.length;j++){
                if(arr[i]>arr[j]){
                    temp=arr[j];
                    arr[j]=arr[i];
                    arr[i]=temp;
                }
            }

        }


        Arrays.sort(arr);
        
     System.out.println(arr[k-1]);



    }

    
}