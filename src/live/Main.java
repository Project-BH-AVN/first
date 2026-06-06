package live;

import java.io.*;
import java.util.*;
import static utils.Base.*;

class Formula {
    Scanner in = new Scanner(System.in);
    int id;
    String expression;
    String type;
    String category;
    ArrayList<String> key;

    Formula(int id, String ex, String type, String category, ArrayList<String> s) {
        this.id = id;
        this.expression = ex;
        this.type = type;
        this.category = category;
        key = s;

    }

    void addKeywords() {
        int count = 1;

        while (true) {
            pl("Enter Keyword " + count + ":");
            count++;
            String s = in.nextLine().trim().replace(" ", "").toLowerCase();
            if (s.isEmpty())
                continue;
            this.key.add(s);
            pl("1-Add more keyword\n2-Exit ");
            if (in.nextInt() == 1) {
                in.nextLine();
                continue;
            } else {
                return;
            }

        }
    }

    void plKeywords() {
        p("Keyword - {");
        for (int i = 0; i < key.size(); i++) {
            if (i != key.size() - 1) {
                p(key.get(i) + ", ");
            } else {
                p(key.get(i));
            }
        }
        pl("}");
    }

    void info() {
        p("Id => " + this.id + " | ");
        p("Expression => " + this.expression + " | ");
        p("Type => " + this.type + " | ");
        pl("Category => " + this.category + " |");
    }

}

class Database {
    Scanner in = new Scanner(System.in);
    ArrayList<Formula> database = new ArrayList<>();
    int lastIndex = 0;

    void load() {
        if ((new File("Formula_database.txt")).exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader("Formula_database.txt"))) {
                String line;
                String firstLine = br.readLine();
                if (firstLine != null) {
                    lastIndex = Integer.parseInt(firstLine.substring(firstLine.indexOf(":") + 1));
                }
                while ((line = br.readLine()) != null) {
                    int id;
                    String expression, type, category;
                    ArrayList<String> keywords = new ArrayList<>();
                    String temp1[] = line.split("\\|");
                    // ID:1|EXPRESSION:a+b|TYPE:type|CATEGORY:category|KEYWORDS:{A,B,C}

                    id = Integer.parseInt(temp1[0].substring(temp1[0].indexOf(":") + 1));
                    expression = temp1[1].substring(temp1[1].indexOf(":") + 1);
                    type = temp1[2].substring(temp1[2].indexOf(":") + 1);
                    category = temp1[3].substring(temp1[3].indexOf(":") + 1);
                    ArrayList<String> list = new ArrayList<>(Arrays
                            .asList((temp1[4].substring(temp1[4].indexOf("{") + 1, temp1[4].indexOf("}"))).split(",")));
                    Formula ob = new Formula(id, expression, type, category, list);
                    database.add(ob);

                }
            } catch (IOException e) {
                System.out.println("Error loading file , please contact administrator!\nExiting Program..");
                System.exit(0);
            }
        } else {
            try (PrintWriter pw = new PrintWriter(new FileWriter("Formula_database.txt"), true);) {
                String default_Formula = "ID:1|EXPRESSION:sin^2x+cos^2x=1|TYPE:identity|CATEGORY:pythagorean identity|KEYWORDS:{sin^2,cos^2,1}";
                pw.println("LASTINDEX:1");
                pw.println(default_Formula);
                lastIndex = 1;
                ArrayList<String> temp = new ArrayList<>(List.of("sin^2", "cos^2", "1"));
                database.add(new Formula(1, "sin^2x+cos^2x=1", "identity", "pythagorean identity", temp));
            } catch (IOException e) {
                System.out.println("Error loading file , please contact administrator!\nExiting Program");
                System.exit(0);
            }
        }
    }

    void add() {
        pl("Enter Expression:");
        String ex = in.nextLine().replace(" ", "").toLowerCase();
        pl("Enter Type:");
        String ty = in.nextLine().replace(" ", "").toLowerCase();
        pl("Enter category:");
        String ca = in.nextLine().replace(" ", "").toLowerCase();

        ArrayList<String> keywords = new ArrayList<>();
        Formula ob = new Formula(lastIndex + 1, ex, ty, ca, keywords);
        p("Do you want to add keywords? (y/n)");
        if (checkYN() == 0)

            ob.addKeywords();

        pl("Do you want to edit anything in this formula ? ");
        if (checkYN() == 0)
            edit(ob);
        database.add(ob);

    }

    void search_Ex(String searchWord) {
        int count = 0;
        pl("Search Results");
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i).expression.contains(searchWord)) {
                pl(database.get(i).expression);
            }
        }
        if (count == 0)
            pl("None !");
    }

    void search_Key(String searchWord) {
        int count = 0;
        pl("Search Results");
        for (int i = 0; i < database.size(); i++) {
            for (int j = 0; j < database.get(i).key.size(); j++) {
                if (database.get(i).key.get(j).contains(searchWord)) {
                    database.get(i).info();
                    count++;
                }
            }
        }
        if (count == 0)
            pl("None !");
    }

    void list() { // Id => 1 | Expression => sin2x=2sinxcosx | Type => identity | Category =>
                  // double angle |
        for (int i = 0; i < database.size(); i++) {
            database.get(i).info();
        }
    }

    /*
     * void list(int type)
     * {
     * String cat;int flag =0;int flag2=0;
     * 
     * switch(type)
     * {
     * case 1: flag =1;cat="type"; break;
     * case 2: flag=2; cat="category"; break;
     * 
     * default : cat=""; break;
     * }
     * pl("Enter "+cat+":");
     * 
     * String s=in.nextLine();
     * for(int i=0;i<database.size();i++)
     * {
     * if(flag==1)
     * {
     * if(database.get(i).type.equalsIgnoreCase(cat))
     * {
     * System.out.println(database.get(i).expression); flag2++;
     * }
     * 
     * }
     * else if(flag==2)
     * 
     * {
     * if(database.get(i).category.equalsIgnoreCase(cat))
     * {
     * System.out.println(database.get(i).expression); flag2++;
     * }
     * 
     * }
     * 
     * 
     * }
     * if(flag2==0)
     * pl(cat+" Not Found");
     * 
     * }
     * 
     */

    void edit(Formula ob) {
        // code here
    }

    void save() {
        // code here
        try (PrintWriter pw = new PrintWriter(new FileWriter("Formula_database.txt"));) {

            pw.println("LASTINDEX:" + lastIndex);
            String type, category, expression;
            int id;
            String keywords;

            for (int i = 0; i < database.size(); i++) {
                Formula ob = database.get(i);
                id = ob.id;
                type = ob.type;
                category = ob.category;
                expression = ob.expression;
                keywords = ob.key.toString().replace("[", "").replace("]", "");
                pw.println("ID:" + id + "|EXPRESSION:" + expression + "|TYPE:" + type + "|CATEGORY:" + category
                        + "|KEYWORDS:{" + keywords + "}");

            }

        } catch (IOException e) {
            System.out.println("Error saving the formulas , contact administrator!\nExiting Program..");
            System.exit(0);
        }
    }

}

public class Main {
    public static void main(String[] args) {
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
