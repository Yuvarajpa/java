public class oddeve {
    public static void main(String[] args) {
        // int n=1481;
        // String[] res ={"Even", "Odd"};
        // System.out.println(res[n%2]);



        int a []={0,0,1,1};
        int b[]={0,1,0,1};
for(int i=0;i<a.length;i++){
    for(int j=i+1;j<a.length;j++){
        if(a[i]==a[j] && b[i]==b[j]){
            System.out.println("Same");
        }else{
            System.out.println("Different");
        }
    }
}
    }}



