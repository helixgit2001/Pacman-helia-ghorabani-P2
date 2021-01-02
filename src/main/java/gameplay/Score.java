package gameplay;

import java.util.prefs.Preferences;

/**
 * Class to manage the best score
 */
public class Score {

    private final Preferences preferences = Preferences.userRoot().node(Score.class.getName());

    /**
     * get the best score
     * @return score
     */
    public String getScoreFile(){
        return preferences.get("Score", "0");
    }

    /**
     * writes the best score locally.
     * @param score
     */
    public void setScoreFile(String score)  {
        preferences.put("Score",score);
    }
}
