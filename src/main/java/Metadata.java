public class Metadata {
    private String startAt;
    private int lastFrame;
    private Player[] players;
    private String playedOn;
    private String consoleNick;

    public Metadata(String start, int last, String played) {
        startAt = start;
        lastFrame = last;
        playedOn = played;
    }

    public Metadata(String start, int last, String played, String nickname) {
        startAt = start;
        lastFrame = last;
        playedOn = played;
        consoleNick = nickname;
    }

    public Metadata(String start, int last, Player[] playerArray, String played, String nickname) {
        startAt = start;
        lastFrame = last;
        players = playerArray;
        playedOn = played;
        consoleNick = nickname;
    }

    public Metadata(String start, int last, Player[] playerArray, String played) {
        startAt = start;
        lastFrame = last;
        players = playerArray;
        playedOn = played;
    }

    public Metadata() {
        return;
    }
}
