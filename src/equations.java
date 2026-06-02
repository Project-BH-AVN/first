import java.util.*;

// 1. Tree Node Class for the Abstract Syntax Tree (AST)
class BN {
    String data;
    BN left;
    BN right;
    
    BN(String data) {
        this.data = data;
        left = right = null;
    }
}

public class equations {
    // Global variables must be static to be accessed directly from the static main method
    static HashMap<String, Double> knownVars = new HashMap<>();
    static Scanner in = new Scanner(System.in);

    // 2. Standardization: Converts LHS = RHS into LHS - (RHS) = 0
    static String standard(String s1) {
        s1 = s1.replace(" ", ""); // Good practice to clear spaces first
        StringBuffer sb = new StringBuffer(s1);
        int equalIndex = sb.indexOf("=");
        sb.insert(equalIndex, "-(");
        sb.deleteCharAt(equalIndex + 2); // Removes the original '=' sign
        sb.append(")");
        return sb.toString();
    }

    // 3. Parser: Converts Infix expression to Postfix using Shunting-Yard
    static String shunting_yard(String s1) {
        StringBuffer output = new StringBuffer("");
        Stack<Character> stack = new Stack<>();
        
        for (int i = 0; i < s1.length(); i++) {
            char x = s1.charAt(i);

            if (Character.isLetterOrDigit(x)) {
                output.append(x); // Operands go straight to output
            } 
            else if (x == '(') {
                stack.push(x);
            } 
            else if (x == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop(); // Safely discard the '('
                }
            } 
            else if (x == '+' || x == '-' || x == '*' || x == '/') {
                while (!stack.isEmpty() && stack.peek() != '(' && precendenceHE(x, stack.peek())) {
                    output.append(stack.pop());
                }
                stack.push(x);  
            }       
        }
        
        // Rule 5: Empty the remaining stack
        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }
        
        return output.toString();
    }

    // Helper method for Shunting-Yard operator precedence
    static boolean precendenceHE(char x, char y) {
        int a = (x == '-' || x == '+') ? 1 : 2;
        int b = (y == '-' || y == '+') ? 1 : 2;        
        return a <= b; // True if stack top (y) has >= precedence than current (x)
    }

    // 4. Build AST: Constructs a Binary Tree from the Postfix expression
    static BN postToTree(String pf) {
        Stack<BN> stack = new Stack<>();

        for (int i = 0; i < pf.length(); i++) {
            char x = pf.charAt(i);
            
            if (Character.isLetterOrDigit(x)) {
                BN node = new BN("" + x);
                stack.push(node);
            } 
            else if (x == '+' || x == '-' || x == '*' || x == '/') {
                BN node = new BN("" + x);
                // The first popped element is strictly the right child
                node.right = stack.pop();
                node.left = stack.pop();
                stack.push(node);
            }
        }
        return stack.pop(); // The final remaining node is the root of the tree
    }   

    // 5. Evaluate Engine: Recursively calculates the mathematical value of the tree
    static double evaluate(BN node, double x) {
        // Base Case: Leaf node (variable or number)
        if (node.left == null && node.right == null) {
            
            // 1. Is it exactly our target variable "x"?
            if (node.data.equals("x")) {
                return x; 
            }
            // 2. Is it a known user-defined variable? (O(1) lookup)
            else if (knownVars.containsKey(node.data)) {
                return knownVars.get(node.data);
            }
            // 3. Fallback: If it's not 'x' and not in the map, it MUST be a number.
            else {
                return Double.parseDouble(node.data);
            }
        }

        // Recursive Step: Evaluate the left and right branches
        double leftVal = evaluate(node.left, x);
        double rightVal = evaluate(node.right, x);

        // Process Root: Apply the mathematical operator
        switch (node.data) {
            case "+": return leftVal + rightVal;
            case "-": return leftVal - rightVal;
            case "*": return leftVal * rightVal;
            case "/": return leftVal / rightVal;
        }
        
        return 0; // Default fallback
    }

    // 6. The Mathematical Solver: Orchestrates the pipeline
    static void solve(String equation) {
        // Run the 3-step pipeline
        String standardEq = standard(equation);
        String postfix = shunting_yard(standardEq);
        BN root = postToTree(postfix);
        
        // Find C and M using the expression tree
        double C = evaluate(root, 0.0);
        double f1 = evaluate(root, 1.0);
        double M = f1 - C;
        
        System.out.println("\n--- Calculating ---");
        System.out.println("Standardized: " + standardEq);
        System.out.println("Postfix: " + postfix);
        
        // Handle mathematical edge cases
        if (M == 0) {
            if (C == 0) {
                System.out.println("Result: The equation has infinitely many solutions (0 = 0)");
            } 
            else {
                System.out.println("Result: The equation has no solution (e.g., 5 = 0)");
            }
        } 
        else {
            double x = -C / M;
            System.out.println("Result: x = " + x);
        }
        System.out.println("-------------------\n");
    }

    // 7. Program Main Execution
    public static void main(String args[]) {
        boolean enterEquation = true;
        String s1 = "";
        
        while (true) {
            if (enterEquation) {
                System.out.println("Enter Equation (use 'x' as target variable):");
                s1 = in.nextLine();
            }
            if (s1.trim().isEmpty() || !s1.contains("=")) {
                    System.out.println("Error: Invalid input. Equation must contain an '=' sign.");
                    continue; //
            }
            // Look for custom variables (a, b, v, etc.) and prompt the user
            for (int i = 0; i < s1.length(); i++) {
                char x = s1.charAt(i);
                
                if (Character.isLetter(x) && x != 'x') {
                    if (!knownVars.containsKey("" + x)) {
                        System.out.print("Enter the value of " + x + ": ");
                        knownVars.put("" + x, in.nextDouble());
                    }
                }
            }
            
            solve(s1);
            
            // Post-evaluation Menu
            System.out.println("1 - New Equation");
            System.out.println("2 - Same Equation, different values");
            System.out.println("3 - Exit");
            System.out.print("Select an option: ");
            int choice = in.nextInt();
            
            in.nextLine(); // Clear the Scanner buffer to prevent skipped inputs
            
            switch (choice) {
                case 1: 
                    enterEquation = true;
                    knownVars.clear(); // Empty map for a brand new equation
                    continue; // Loops back to the start
                case 2: 
                    enterEquation = false;
                    knownVars.clear(); // Empty map to force fresh prompts for the same formula
                    continue; 
                case 3:
                    System.out.println("Exiting Program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Error, invalid value. Stopping the run!");
                    System.exit(0);
            }
        }
    }
}