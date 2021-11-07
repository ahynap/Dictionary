import java.util.*;
import java.io.*;


public class treeset
{
    public static void main(String[] args)
    {
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        
        TreeSet<Bnode> dictionary = new TreeSet<Bnode>();
        readLexitronFile(dictionary); //use readLexitronFile method to read file and put into "dictionary" array list


        Bnode maxmean = new Bnode();
        maxmean = dictionary.first(); //set maxmean = fist word of this file
        for (Bnode nodeofword : dictionary) //loop for compare between nodeofword and all word in this file > if similar,then collect in nodeofword 
        {  
            //until it isnt similar -> found new word
            if (nodeofword.mean.size() > maxmean.mean.size()) //if quantity of nodeofword more than quantity of maxmean
                {    
                    maxmean = nodeofword; //change nodeofword to maxmean
                }
        } 
        System.out.println("Maximum meaning word is : " + maxmean.word + " | have " + maxmean.mean.size() + " meaning"); //print maxmean
        
        for (int i=1; i<=maxmean.mean.size(); i++)//loop for print all similar words
        {
            System.out.println(i+") "+maxmean.mean.get(i-1)); //print all similar words
        }




		Scanner stdin = new Scanner(System.in); //input data from user 1 line
		String str = new String();
        do
        {
            System.out.print("\nEnter word >");
            str = stdin.nextLine(); //read data for 1 line
            str = Transform(str); //trim and delete not important space
            
            Bnode key = new Bnode(); //made key node for find str value
            key.word = str;

            if(dictionary.contains(key)) //if have similar word
            {
                TreeSet<Bnode> have = (TreeSet <Bnode>) dictionary.subSet(key, true, key, true); //add that word to "have"
                System.out.println("found : "+ str + " | " + have.first().mean.size() + " word" );
                  for (int i=1; i<=have.first().mean.size(); i++)//loop for print all similar words
                    {
                        System.out.println(i+") "+ have.first().mean.get(i-1)); //print all similar words
                    }
            }
            else //if doesnt have that word
            {
                System.out.println("the word \"" + str + "\" not found");
            }
        }
        while (!str.equalsIgnoreCase("end")); //loop until string=end
        
        stdin.close();
        System.out.println("End Program");
        System.out.println("by 62070501010 Chatchanok Vithoondej\n");
    }


    public static String Transform(String x) //manage space between word
	{
		x = x.replaceAll("\\s+", " "); //adjust the space
		x = x.trim(); //trim front space and back space
		return x;
    }
    
    public static int readLexitronFile(TreeSet<Bnode> data) //read file method
    {
        String buff = null;
        int count = 0;
        int remcount=0; //set start similar word count = 0
        try 
        {   //set FileInputStream & encoding
            FileInputStream fr = new FileInputStream("utf8lexitron.csv");
            InputStreamReader csv = new InputStreamReader(fr,"UTF-8");
            BufferedReader fsc = new BufferedReader(csv);
            
            fsc.read(); //BOM 

            while ((buff = fsc.readLine()) != null) 
            {
                Bnode nodeofword = new Bnode(buff); //send read line to make node

                if (data.contains(nodeofword)) //if have similar word
                {
                    TreeSet<Bnode> nodeofmean = (TreeSet<Bnode>) data.subSet(nodeofword, true, nodeofword, true); //add meaning to that word(node)
                    
                    if (!nodeofmean.first().mean.contains(nodeofword.mean.get(0))) //check node(word) if dont have same meaning before -> same word not same meaning
                        nodeofmean.first().mean.add(nodeofword.mean.get(0)); //add meaning
                    else //check word if have same meaning -> same word same meaning
                        remcount++; //similar node(word) count increase by 1 value -> prepare to delete
                }
                else //if dont have similar word -> dont have same word
                    data.add(nodeofword); //add node
                
                count++;
            }
            fsc.close();
        } 
        catch (FileNotFoundException e)  //show only "cant open file" case
        {
            System.out.println(e.getMessage());
            System.out.println("File not found");
        } 
        catch (Exception e) //other case
        {
            System.out.println("Error " + e.getMessage());
            System.out.println("Operation error");
        }

        System.out.println("Total Read = "+ count + " records");  //print value of all datas can read from this file (both of word and meanings)
        System.out.println("Total word size = "+ data.size() + " words"); //print all words (not inculde meaning)
        System.out.println("Total meaning size = "+ (count - remcount) + " words"); //print remaining word -> after delete word that same both of word and meaning
        
        return count;
    }
}

class Bnode implements Comparable <Bnode> 
{
    String word;
    ArrayList<String> mean; 

    public Bnode(String buff) //constructor for get string -> for read data 1 serie and send to make object
    {
        //split string
        buff = buff.trim().replaceAll("\\s+", " ");
        String [] str = buff.split(",");

        //collect in minor of class
        word = str[0]; 
        mean = new ArrayList<String>();
        String meaning = str[1] + "(" + str[2] + ")";
        mean.add(meaning);
    }

    public Bnode() //for made key node for find str value
    {
        word = "";
    }

    String getword() //specify word
    {
        return this.word;
    }

    public int compareTo(Bnode x) //compare key and return <0 , 0 , >0 -> for manage 1 data
    {
        return (int) this.word.compareToIgnoreCase(x.word);
    }
}





