import java.util.*;
import java.io.*;

public class array_list
{
    public static void main(String[] args)
    {
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        ArrayList<Dnode> dictionary = new ArrayList<Dnode>(); //made "dictionary" variable is array list
        readLexitronFile(dictionary); //use readLexitronFile method to read file and put into "dictionary" array list
        System.out.println("Total Read = "+ dictionary.size() + " records"); //print value of all words can read


        Collections.sort(dictionary); //sort in array list


        int remcount = 0; //set start similar word count = 0
        for (int i=0; i<dictionary.size()-1; i++) //loop for all word
        {
            if (dictionary.get(i).compareAll(dictionary.get(i+1))) //if similar
                { 
                    dictionary.remove(i + 1); //delete 1 word
                    remcount++; //similar word count increase by 1 value
                } 
        }
        System.out.println("Total duplicate found = "+ remcount + " records"); //print all similar words
        System.out.println("Total remaining size = " + dictionary.size() + " records"); //print remaining words


        

        int samemean=0, maxmean=0,position=0; //set start samemean and maxmean = 0
        for (int i=0; i<dictionary.size()-1; i++) //loop for all remaining word
        {
            if (dictionary.get(i).compareMean(dictionary.get(i+1))) //if similar
                samemean++; //similar word count increase by 1 value
            
            else //if it isnt similar
            {
                if (samemean>maxmean) //and samemean > maxmean
                {    
                    maxmean=samemean; //chage maxmean
                    position = i; //chang position -> collect this word is maximum 
                }
                samemean=1; //value of similar word = 1 word
            }
        } 
        System.out.println("Maximum meaning word is : " + dictionary.get(position).getword() + " | have = " + maxmean + " meaning"); //print maxmean
        
        for (int k=0; k<=dictionary.size()-1; k++)//loop for print all similar words
        {
            if(dictionary.get(k).getword().equalsIgnoreCase(dictionary.get(position).getword())) //if "word in file" match with "maximum meaning word"
            {
                System.out.print("\t"+(k-position+maxmean)+") "); //print number list
                dictionary.get(k).printThree(); //print all similar words
            }
        }



		Scanner stdin = new Scanner(System.in); //input data from user 1 line
		String str = new String();
        do
        {
            System.out.print("\nEnter word >");
            str = stdin.nextLine(); //read data for 1 line
            str = Transform(str); //trim and delete not important space
            
            Dnode key = new Dnode(); //made key node for find str value
            key.word = str;
            int middle = Collections.binarySearch(dictionary,key); //middle = middle order of all desired word (collect as a number of position)
            
            if(middle>=0) //if have that word
            {
                int countDown = middle; //start count down from middle
                while(countDown>0 && dictionary.get(countDown-1).compareMean(dictionary.get(countDown)))
                    countDown--; //count down from middle to first

                int forwordcount = middle; //start count forward from middle
                while(forwordcount<dictionary.size()-1 && dictionary.get(forwordcount+1).compareMean(dictionary.get(forwordcount)))
                    forwordcount++; //count forward from middle to last
                
                if((forwordcount-countDown+1)==1)
                    System.out.println("found : "+ str + " | " + (forwordcount-countDown+1) + " word | at " + countDown + "-" + forwordcount);
                else
                    System.out.println("found : "+ str + " | " + (forwordcount-countDown+1) + " words | at " + countDown + "-" + forwordcount);

                for (int k=0; k<=dictionary.size()-1; k++)//loop for print all similar words
                {
                    if(dictionary.get(k).getword().equalsIgnoreCase(dictionary.get(forwordcount).getword())) //if "word in file" match with "that word"
                    {
                        System.out.print((k-countDown+1)+") "); //print number list
                        dictionary.get(k).printThree(); //print all similar words
                    }
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


    
    public static int readLexitronFile(ArrayList<Dnode> data) //read file method
    {
        String buff = null;
        int count = 0;
        try 
        {   //set FileInputStream & encoding
            FileInputStream fr = new FileInputStream("utf8lexitron.csv");
            InputStreamReader csv = new InputStreamReader(fr,"UTF-8");
            BufferedReader fsc = new BufferedReader(csv);
            int BOM = fsc.read();

            while ((buff = fsc.readLine()) != null) 
            {
                Dnode x = new Dnode(buff);
                data.add(x);
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
        return count;
    }
}


class Dnode implements Comparable <Dnode> //class for manage 1 data
{
    String word;
    String mean;
    String type;

    public Dnode(String buff) //constructor for get string -> for read data 1 serie and send to make object
    {
        //split string
        buff = buff.trim().replaceAll("\\s+"," " );
        String [] str = buff.split(",");  

        //collect in minor of class
        word=str[0];
        mean=str[1];
        type=str[2];
    }

    public Dnode() //for made key node for find str value
    {
        word="";
        mean="";
        type="";
    }

    public void printThree() //print data
    {
        System.out.println(word + "\t" + mean + " (" + type + ")" );
    }

    String getword() //specify word
    {
        return this.word;
    }

    public int compareTo(Dnode x) //compare key and return <0 , 0 , >0 -> for manage 1 data
    {
        return (int) this.word.compareToIgnoreCase(x.word);
    }

    boolean compareAll(Dnode x) //compare all -> for pop
    {
        if (this.word.equalsIgnoreCase(x.word) && this.mean.equalsIgnoreCase(x.mean)&& this.type.equalsIgnoreCase(x.type))
            return true;
        else 
            return false;
    }

    boolean compareMean(Dnode x) //compare same word
    {
        if (this.word.equalsIgnoreCase(x.word))
            return true;
        else 
            return false;
    }

}


