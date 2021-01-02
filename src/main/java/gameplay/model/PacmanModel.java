package gameplay.model;

/**
 * Class containing pacman stats information
 */
public class PacmanModel {

    private final int P = 10000;
    private int score, pv;
    private boolean isNoel;
    private boolean isDead;
    private int p;

    public PacmanModel(){
        score = 0;
        pv = 3;
        isNoel = false;
        isDead = false;
        p = P;
    }

    public void incrementPV(){
        pv++;
        System.out.println("remaining life : " + pv);
    }

    public void decrementPV(){
        if(checkNull()) return;
        pv--;
        System.out.println("remaining life : : " + pv);
    }

    public int getPV(){
        return pv;
    }
    public int getScore(){
        return score;
    }

    /**
     * function to detect the death of the pacman, triggering the death event
     * @return
     */
    public boolean checkNull(){
        return pv <= 0 ;
    }

    /**
     * Add score
     * check if the score is high enough to get a life
     * @param value
     */
    public void addScore(int value){
        score += value;
        if (this.score >= this.p){
            incrementPV();
            this.p += P;
        }
        System.out.println("Score : " + score);
    }

    /**
     * activating pacman's super mode, making him invisible
     * @param powPac
     */
    public void setRed(boolean powPac) {
        isNoel = powPac;
    }

    public boolean isRed() {
        return isNoel;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
