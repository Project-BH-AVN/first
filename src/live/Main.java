package live;
import java.util.*;
import static utils.Base.*;

class Formula
{
    Scanner in =new Scanner(System.in);
    int id;                    
    String expression;
    String type;
    String category;
    ArrayList<String> key;

    Formula(int id,String ex, String type , String category )
    {
        this.id=id;
        this.expression=ex;
        this.type=type;
        this.category=category;
        key=new ArrayList<>();

    }

    void addKeywords()
    { int count =1;

        while(true)
        {
            pl("Enter Keyword "+count+":");count++;
            String s=in.nextLine().trim().replace(" ","").toLowerCase();
            if(s.isEmpty())
                continue;
            this.key.add(s);              
            pl("1-Add more keyword\n2-Exit ");
            if(in.nextInt()==1)
            {
                in.nextLine();
                continue;
            }
            else
            {  
                return; 
            }

        }
    }
    void plKeywords() {
    p("Keyword - {");
    for(int i = 0; i < key.size(); i++) {
        if(i != key.size() - 1) {
            p(key.get(i) + ", ");
        } else {
            p(key.get(i));
        }
    }
    pl("}");
}

   
}
class Database 
{
    Scanner in =new Scanner(System.in);
    ArrayList<Formula> database= new ArrayList<>();

    int lastIndex = database.size()-1;

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

        p("Do you want to add more keywords? (y/n)");
        if(checkYN()==0)
            ob.addKeywords();

        pl("Auto Generated Level 2 Keywords= ");

        pl("Do you want to edit anything in this formula ? ");
        if(checkYN()==0)
            edit(ob);
        database.add(ob);  

        store(ob);
    }


    void search_Ex(String searchWord)
    {
        for(int i=0;i<database.size();i++)
        {
            if(database.get(i).expression.contains(searchWord))
            {
                pl(database.get(i).expression);            
            }
        }
    }

    void search_Key(String searchWord)
    {
        for(int i=0;i<database.size();i++)
        {
            for(int j=0;j<database.get(i).key.size();j++)
            {
                if( database.get(i).key.get(j).contains(searchWord))
                {
                    System.out.println();
                }
            }
        }
    }

    void list()
    {
        for(int i=0;i<database.size();i++)
        {
            pl("\n\nID= "+database.get(i).id);
            pl("Expression= "+database.get(i).expression);
            pl("Type= "+database.get(i).type);
            pl("Category= \n\n"+database.get(i).category);

        }
    }

    void list(int type)
    {
        String cat;
        switch(type)
        {
            case 1: cat="type"; break;
            case 2: cat="category"; break;

            default : cat=""; break;
        }
        pl("Enter "+cat);
        {
            String s=in.nextLine();

        }

    }

    void edit(Formula ob)
    {
        //code here
    }

    void edit()
    {
        //code here
    }

    void store(Formula ob)
    {
        //code here
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
    }

}
