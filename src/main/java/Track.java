import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * author : Robert Blasetti
 * Date: 23/04/2014
 * Time: 6:06 PM
 */
public class Track {
    protected ArrayList<String> eventsInTrack;
    protected ArrayList<Talk> talksToAllocateToTrack;
    private int hours = 9;
    private int minutes = 0;
    private String period = "AM";

    public Track(ArrayList<Talk> masterOfTalksToBeSorted) {
        eventsInTrack = new ArrayList<String>();
        assembleTrack(masterOfTalksToBeSorted);
    }

    public void assembleTrack(ArrayList<Talk> talks) {
        talksToAllocateToTrack = talks;
        orderTalksByDuration(talksToAllocateToTrack);
        assembleTrack();
    }

    public void assembleTrack() {
        allocateSessionToTrack(talksToAllocateToTrack, "morning");
        allocateLunchToTrack();
        allocateSessionToTrack(talksToAllocateToTrack, "afternoon");
        allocateNetworkingEventToTrack();
    }

    public void allocateSessionToTrack(ArrayList<Talk> talksToIncludeInSession, String sessionSegment) {
        Session session = new Session(talksToIncludeInSession, sessionSegment);
        talksToIncludeInSession = sortTalksIntoSessionFormat(session, talksToIncludeInSession);
        session.updateTalkList(talksToIncludeInSession);
        addSessionToTrack(session);
        talksToAllocateToTrack = deleteSortedTalksFromMaster(talksToAllocateToTrack, talksToIncludeInSession);
    }

    public ArrayList<Talk> sortTalksIntoSessionFormat(Session sessionToSortFor, ArrayList<Talk> talksToBeSorted) {
        ArrayList<Talk> talksAllocatedForSession = new ArrayList<Talk>();
        int minutesToBeAllocated = sessionToSortFor.durationOfSessionToAllocateInMinutes;
        while (minutesToBeAllocated != 0) {
            for (Talk talk : talksToBeSorted) {
                if (minutesToBeAllocated >= talk.durationInMinutes) {
                    minutesToBeAllocated = minutesToBeAllocated - talk.durationInMinutes;
                    talksAllocatedForSession.add(talk);
                }
            }
            if (breakLoopConditions(sessionToSortFor, talksToBeSorted, minutesToBeAllocated)) {
                break;
            }
        }
        return talksAllocatedForSession;
    }

    public boolean breakLoopConditions(Session sessionToSort, ArrayList<Talk> talksToSort, int minutesToSortInto) {
        if (talksToSort.size() == 0) {
            return true;
        } else if (sessionToSort.durationOfSessionToAllocateInMinutes == 240 && minutesToSortInto <= 60) {
            return true;
        }
        return false;
    }

    public void addSessionToTrack(Session session) {
        ArrayList<Talk> eventsInSession = session.getEventList();
        for (Talk talk : eventsInSession) {
            String finalEvent = timeToString() + " " + talk.toString();
            eventsInTrack.add(finalEvent);
            updateTime(talk);
        }
    }

    public ArrayList<Talk> deleteSortedTalksFromMaster(ArrayList<Talk> master, ArrayList<Talk> toBeDeleted) {
        for (int x = 0; x < master.size(); x++) {
            for (Talk toDeleted : toBeDeleted) {
                if (master.get(x).equals(toDeleted)) {
                    master.remove(x);
                }
            }
        }
        return master;
    }

    public void orderTalksByDuration(ArrayList<Talk> talksToBeOrdered) {
        int maxTalkDuration = 120;
        ArrayList<Talk> orderedTalks = new ArrayList<Talk>();
        while (talksToBeOrdered.size() != 0) {
            for (int x = maxTalkDuration; x > 0; x--) {
                for (int y = 0; y < talksToBeOrdered.size(); y++) {
                    if (talksToBeOrdered.get(y).durationInMinutes == x) {
                        orderedTalks.add(talksToBeOrdered.get(y));
                        talksToBeOrdered.remove(y);
                        y--;
                    }
                }
            }
        }
        talksToAllocateToTrack = orderedTalks;
    }

    public int getAccumulatedTimeInMinutes() {
        int accumulatedTimeInMinutes = 0;
        if (period.equals("PM")) {
            accumulatedTimeInMinutes += 12 * 60;
        }
        accumulatedTimeInMinutes += hours * 60;
        accumulatedTimeInMinutes += minutes;
        return accumulatedTimeInMinutes;
    }

    public String updateTime(Talk talkJustAdded) {
        int accumulatedTime = getAccumulatedTimeInMinutes() + talkJustAdded.durationInMinutes;
        if (accumulatedTime >= (12 * 60)) {
            period = "PM";
            accumulatedTime = accumulatedTime - (12 * 60);
        } else {
            period = "AM";
        }
        hours = accumulatedTime / 60;
        minutes = accumulatedTime % 60;

        return timeToString();
    }

    public String timeToString() {
        String hourToPrint = "0";
        String minuteToPrint = "0";
        if (hours < 10) {
            hourToPrint += hours;
        } else {
            hourToPrint = Integer.toString(hours);
        }
        if (minutes < 10) {
            minuteToPrint += minutes;
        } else {
            minuteToPrint = Integer.toString(minutes);
        }
        return hourToPrint + ":" + minuteToPrint + period;
    }

    public void allocateLunchToTrack() {
        eventsInTrack.add("12:00PM Lunch");
        hours += 1;
    }

    public void allocateNetworkingEventToTrack() {
        checkNetworkEventTimeIsIn4to5PMBoundary();
        eventsInTrack.add(timeToString() + " Networking Event");
    }

    public void checkNetworkEventTimeIsIn4to5PMBoundary() {
        if (hours < 4 && period.equals("PM")) {
            hours = 4;
        }
    }

    public ArrayList<Talk> getUnallocatedTalks() {
        return talksToAllocateToTrack;
    }

    public void printTrackList() {
        for (String tracks : eventsInTrack) {
            System.out.println(tracks);
        }
        System.out.println();

    }
}
