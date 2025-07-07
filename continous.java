public class continous {
    public static void main(String args[]){
        int arr[] ={1,2,3,4,5,6,7,8};
        int count=0;
        int count1=0;
        int value=0;

        for(int i=0;i<arr.length;i++){
            for(int j=i;j<arr.length;j++){

                if(arr[i]%2==0){
                    count=1;
                }
                else if(arr[i]%2!=0){
                    count1=1;
                }

                if(count==count1){
                    value++;
                }


            }
        }
        System.out.print(value);

int n=20;
int a=1;

        while(n>=a){
            System.out.println(n+a);

            
            a++;

        }
    }

  
    
}
