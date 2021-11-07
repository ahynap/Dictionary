import java.util.*;
import java.io.*;


public class hashmap
{
    public static void main(String[] args)
    {
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

        HashMap<String,Bnode>  dictionary = new HashMap <String,Bnode>();

        readLexitronFile(dictionary); //use readLexitronFile method to read file and put into "dictionary" array list

        int maxmean=0;
        String collectwordmaxmean =""; //make empty string fot collect the word that have maximum meaning
        for (String nodeofword : dictionary.keySet()) //loop for compare between nodeofword and all word in this file > if similar,then collect in nodeofword 
        {  
            Bnode samemean = dictionary.get(nodeofword);

            //until it isnt similar -> found new word
            if (samemean.mean.size() > maxmean) //if quantity of nodeofword more than quantity of maxmean
                {    
                    maxmean = samemean.mean.size(); //change samemean to maxmean
                    collectwordmaxmean = nodeofword; //give string we made = the word that have maximun meaning in each round
                }
        }
        System.out.println("Maximum meaning word is : " + collectwordmaxmean + " | have " + maxmean + " meaning"); //print word that have maximum meaning
        
        for (int i=1; i<= dictionary.get(collectwordmaxmean).mean.size(); i++)//loop for print all similar words
        {
            System.out.println(i+") "+dictionary.get(collectwordmaxmean).mean.get(i-1)); //print all similar words
        }


		Scanner stdin = new Scanner(System.in); //input data from user 1 line
		String str = new String();
        do
        {
            System.out.print("\nEnter word >");
            str = stdin.nextLine(); //read data for 1 line
            str = Transform(str); //trim and delete not important space
            str = str.toLowerCase(); //change to lower case
            
            Bnode key = new Bnode(); //made key node for find str value
            key.word = str;

            if(dictionary.containsKey(str)) //if have similar word
            {
                Bnode have = dictionary.get(str); //add that word to "have"
                System.out.println("found : "+ str + " | " + have.mean.size() + " word" );

                for (int i=1; i<=have.mean.size(); i++)//loop for print all similar words
                    System.out.println(i+") "+ str + "\t" + have.mean.get(i-1));
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
    
    public static int readLexitronFile(HashMap<String,Bnode> data) //read file method
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
                Bnode dict = new Bnode(buff); //send read line to make node

                if (data.containsKey(dict.word)) //if have similar word
                {
                    Bnode nodeofword = data.get(dict.word); //add meaning to that word(node)
                    
                    if(!nodeofword.mean.contains(dict.mean.get(0))) //check node(word) if dont have same meaning before -> same word not same meaning
                    {
                        //put back in map
                        nodeofword.mean.addAll(dict.mean);
                        data.put(dict.word,nodeofword); //add meaning
                    }
                    else //check word if have same meaning -> same word same meaning
                        remcount++; //similar node(word) count increase by 1 value -> prepare to delete
                }
                else //if dont have similar word -> dont have same word
                    data.put(dict.word,dict); //add node
                
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
        System.out.println("Total word size = " + data.size() + " words"); //print all words (not inculde meaning)
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
        word = str[0].toLowerCase(); //chang all capital letter to lower case
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




