package sudoku;

import java.util.Scanner;

/**
 *
 * @author Candice Liu 20125448
 * @author Mingxuan Gao 20117616
 */
public class cuiMain {
    
    public static void main(String[] args) 
    {
        Scanner SC = new Scanner(System.in);
        while (true) 
        {
            System.out.println("please input the filename about a sudoku puzzle:");
            String filename = SC.next();

            Model model = new Model(filename);
            model.printGrids();
            System.out.println("do you want to solve it?(y|N):");
            String ans = SC.next();
            
            if (ans.equals("y") || ans.equals("Y")) 
            {
                Solver solver = model.getSolver();
                solver.solve();
                model.printGrids();
                System.out.println("do you want to save the solution?(y|N):");
                ans = SC.next();
                
                if (ans.equals("y") || ans.equals("Y"))
                {
                    model.saveSolution();
                }                  
                else if (ans.equals("n") || ans.equals("N"))
                {
                    System.out.println("you choose N/n.");
                }                   
                else
                {
                    System.out.println("invalid input.");
                }                    
            } 
            else if (ans.equals("n") || ans.equals("N")) 
            {
                System.out.println("you choose N/n.");
            } 
            else 
            {
                System.out.println("invalid input.");
            }
            
            System.out.println("do you want to continue?(y|N):");
            
            ans = SC.next();
            
            if (ans.equals("y") || ans.equals("Y"))
            {
                model.saveSolution();
            }               
            else if (ans.equals("n") || ans.equals("N")) 
            {
                System.out.println("you choose N/n.");
                break;
            } 
            else
            {
                System.out.println("invalid input.");
            }               
        }
    }    
}
