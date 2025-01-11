import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;

public class BaseballElimination {
    private final HashMap<String, Integer[]> divisionTeamsData;
    private final int teamsNum;

    public BaseballElimination(String filename) {
        In in = new In(filename);

        divisionTeamsData = new HashMap<>();

        teamsNum = in.readInt();

        int id = 0;
        while (!in.isEmpty()) {
            String teamName = in.readString();
            Integer[] data = new Integer[teamsNum + 4];

            data[0] = id;
            for (int i = 1; i < data.length; i++) data[i] = in.readInt();

            divisionTeamsData.put(teamName, data);
            id++;
        }
    }

    public int numberOfTeams() {
        return teamsNum;
    }

    public Iterable<String> teams() {
        return divisionTeamsData.keySet();
    }

    public int wins(String team) {
        validateTeamExistence(team);
        return divisionTeamsData.get(team)[1];
    }

    public int losses(String team) {
        validateTeamExistence(team);
        return divisionTeamsData.get(team)[2];
    }

    public int remaining(String team) {
        validateTeamExistence(team);
        return divisionTeamsData.get(team)[3];
    }

    public int against(String team1, String team2) {
        validateTeamExistence(team1);
        validateTeamExistence(team2);

        int idT2 = divisionTeamsData.get(team2)[0];
        return divisionTeamsData.get(team1)[4 + idT2];
    }

    public boolean isEliminated(String team) {
        return certificateOfElimination(team) != null;
    }

    public Iterable<String> certificateOfElimination(String team) {
        validateTeamExistence(team);

        HashSet<String> teamsCert = new HashSet<>();
        for (String t : teams()) {
            if (!t.equals(team) && (wins(team) + remaining(team) < wins(t))) teamsCert.add(t);
        }

        if (!teamsCert.isEmpty()) return teamsCert;

        int gamesNum = teamsNum * (teamsNum - 1) / 2;
        int t = gamesNum + teamsNum + 1;

        FlowNetwork fn = buildNetwork(team, gamesNum, t);
        FordFulkerson maxflow = new FordFulkerson(fn, 0, t);

        for (int i = gamesNum + 1; i < t; i++) {
            if (!maxflow.inCut(i)) continue;

            int teamIdx = i - (gamesNum + 1);

            for (String tm : teams()) if (divisionTeamsData.get(tm)[0] == teamIdx) {
                teamsCert.add(tm);
                break;
            }
        }

        return teamsCert.isEmpty() ? null : teamsCert;
    }

    private FlowNetwork buildNetwork(String team, int gameVertices, int t) {
        int x = divisionTeamsData.get(team)[0];

        FlowNetwork fn = new FlowNetwork(t + 1);

        int gameIdx = 1;
        int teamsStartIdx = gameVertices + 1;

        HashSet<String> alreadyProcessed = new HashSet<>();

        for (String t1 : teams()) {
            int t1Idx = divisionTeamsData.get(t1)[0];

            if (t1Idx == x) continue;

            for (String t2 : teams()) {
                int t2Idx = divisionTeamsData.get(t2)[0];

                if (t2Idx == t1Idx || t2Idx == x) continue;

                if (!alreadyProcessed.add(Math.min(t1Idx, t2Idx) + "," + Math.max(t1Idx, t2Idx))) continue;

                fn.addEdge(new FlowEdge(0, gameIdx, against(t1, t2)));

                fn.addEdge(new FlowEdge(gameIdx, teamsStartIdx + t1Idx, Double.POSITIVE_INFINITY));
                fn.addEdge(new FlowEdge(gameIdx, teamsStartIdx + t2Idx, Double.POSITIVE_INFINITY));

                gameIdx++;
            }

            int cap = wins(team) + remaining(team) - wins(t1);
            fn.addEdge(new FlowEdge(teamsStartIdx + t1Idx, t, cap));
        }

        return fn;
    }

    private void validateTeamExistence(String team) {
        if (!divisionTeamsData.containsKey(team)) throw new IllegalArgumentException("Team `" + team + "` not found");
    }

    // Test
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                System.out.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) System.out.print(t + " ");
                System.out.println("}");
            }

            else System.out.println(team + " is not eliminated");
        }
    }
}
