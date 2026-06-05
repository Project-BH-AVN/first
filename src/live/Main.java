package live;
import java.util.*;
import static utils.Base.*;
/*Formula object:
→ id : int
→ expression : String
→ type : String (identity / formula)
→ category : String (pythagorean / double angle / etc)
→ level1keywords: ArrayList (specific: sin²x, cos²x)
→ level2keywords: ArrayList (broad: sin, cos, tan)

The Formula object must store: id (int), 
expression (String), type (String — identity or formula),
 category (String), level1keywords (dynamic list of specific keywords),
  level2keywords (dynamic list of broad keywords).
Check for: correct field types, constructor completeness,
 whether ArrayList is used over static arrays for keywords, 
 and whether the class is sufficient to support add/search/list/edit operations.*/
class Formula
{
    int id;                    
    String expression;
    String type;
    String category;
    ArrayList<String> lvl1Keywords;
    ArrayList<String> lvl2Keywords;

    Formula(int id,String ex, String type , String category )
    {
        this.id=id;
        this.expression=ex;
        this.type=type;
        this.category=category;
        lvl1Keywords=new ArrayList<>();
        lvl2Keywords=new ArrayList<>();
        
    }

    void lvl1customkeywords()
    {
        // add custom keywords to lvl1keyword list 
    }

    void plLvl1Keywords()
    {
        //print lvl 1 keywords
    }

    void plLvl2Keywords()
    {
        //print lvl 2 keywords
    }
// sin2x=2SinAcos A


}
class Database 
{
    Scanner in =new Scanner(System.in);
    ArrayList<Formula> database= new ArrayList<>();
    int lastIndex = database.size()-1;
    ArrayList<String> trigList1 = new ArrayList<>(Arrays.asList("sin", "cos", "tan", "cot", "sec", "cosec"));

    void add()
    {
        pl("Enter Expression:");
        String ex=in.nextLine().replace(" ","").toLowerCase();
        pl("Enter Type:");
        String ty=in.nextLine().replace(" ","").toLowerCase();
         pl("Enter category:");
        String ca=in.nextLine().replace(" ","").toLowerCase();
        Formula ob=new Formula(lastIndex+1,ex,ty,ca);
        lastIndex++;
        generatelvl1Keywords(ob);
        generatelvl2Keywords(ob);
        
        pl("Auto Generated Level 1 Keywords= ");
        ob.plLvl1Keywords();
        p("Do you want to add more keywords?");
        if(checkYN()==0)
        ob.lvl1customkeywords();
        
        pl("Auto Generated Level 2 Keywords= ");
        ob.plLvl2Keywords();
        pl("Do you want to edit anything in this formula ? ");
        if(checkYN()==0)
        edit(ob);
        database.add(ob);  

        store(ob); // stores in file database.txt
        
   }

    

    void search(String searchWord)
    {
        // keywords sin , cos , sin2x , cos2x 
    }

    void list()
    {//id exp type cate
        for(int i=0;i<database.size();i++)
        {
            pl("\n\nID= "+database.get(i).id);
            pl("Expression= "+database.get(i).expression);
            pl("Type= "+database.get(i).type);
            pl("Category= \n\n"+database.get(i).category);
             
        }
    }

    void list(String type)
    {
        
    }

    void edit()
    {
         // edit anything except id
    }

    void edit(Formula ob)
    {
        // edit specific object
    }

    private void generatelvl1Keywords(Formula ob)
    {
        String terms[]=ob.expression.split("[+\\-=*/()^]");
        for(String s:terms)
        {
            if(isNumeric(s) || s.length()<2 || s.equals("") || s.equals("x")  || s.equals("y") || s.equals("z"))
            {
                continue;
            }
            else if( s.length()>=2)                
            {
              for(String s1:trigList1)
              {
                  if(s.contains(s1))
                  {
                    ob.lvl1Keywords.add(s);
                    break ; 
                  }
              }
            }
            else
            {
                continue;
            }
        }
    }
    private void generatelvl2Keywords(Formula ob)
    {
        //code here
    }

    void store(Formula ob)
    {
        // stores current formula and updates INDEX and LAST_ID at top of file
    }
}


public class Main 
{
    public static void main(String[] args) 
    {
        Scanner in = new Scanner(System.in);
        Database db = new Database(); 

        while (true) {
            System.out.println("1. Add Formula\n2. Search\n3. List All\n4. Edit Keywords\n5. Exit");
            int choice = in.nextInt();
            in.nextLine(); 

            
            if (choice == 3) {
                db.list(); 
            } else if (choice == 5) {
                break; 
            }
            
        }
        in.close();
    }//hi aviral can u read it , yes i can read it 
 
} //
