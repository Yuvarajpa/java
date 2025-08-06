public class zero {

    public static void main(String[] args) {
        int arr[]={2,0,1,5,0,2,0,3,4,0,6};
        int n = arr.length;

        for(int i=0;i<n-1;i++){
                if(arr[i]!=0){
                    arr[i]=arr[i+1];

                }
        }

        for(int i=0;i<n;i++){
            System.out.print(arr[i] + " ");
        }
    }
}