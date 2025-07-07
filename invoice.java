import java.util.Scanner;

public class invoice {
public static void main(String[] args) {
    Scanner sc=new Scanner(System.in);
    int n =sc.nextInt();

    switch(n){

        case 1:
        System.out.println("Enter the product code");
        break;

        case 2 :
        System.out.println("Enter the Quantity");
        break;

        case 3 :
        System.out.println("Generate the bills");
    }
}
    
}