import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * author : Robert Blasetti
 * Date: 23/04/2014
 * Time: 8:46 PM
 */
public class ProposalFileReader {
    private ArrayList<Talk> formattedTalks;

    public ProposalFileReader(String file) {
        ArrayList<String> talksToBeFormattedFromFile = readProposal(file);
        formatAllTalksInProposal(talksToBeFormattedFromFile);
    }

    public ArrayList<String> readProposal(String input) {
        ArrayList<String> talksFormattedToStrings = new ArrayList<String>();
        try {
            String workingDirectory = System.getProperty("user.dir") + input.substring(59);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(workingDirectory));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                talksFormattedToStrings.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: The file was not found.");
        } catch (IOException e) {
            System.out.println("ERROR: General I/O error.");
        }
        return talksFormattedToStrings;
    }

    public ArrayList<Talk> formatAllTalksInProposal(ArrayList<String> talksToBeFormatted) {
        formattedTalks = new ArrayList<Talk>();
        for (String unformattedTalk : talksToBeFormatted) {
            Talk formattedTalk = new Talk(unformattedTalk);
            formattedTalks.add(formattedTalk);
        }
        return formattedTalks;
    }

    public ArrayList<Talk> getTalkData() {
        return formattedTalks;
    }
}

