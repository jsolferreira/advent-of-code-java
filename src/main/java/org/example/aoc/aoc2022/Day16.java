package org.example.aoc.aoc2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day16 extends AoC2022Day<Map<String, Day16.FlowRate>> {

    protected record FlowRate(String name, int flowRate, List<String> valves) {
    }

    @Override
    protected Map<String, FlowRate> parseInput(String strInput) {

        final Pattern pattern = Pattern.compile("Valve (\\w\\w) has flow rate=(\\d+); tunnels? leads? to valves? (.*)");

        return strInput.lines()
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> new FlowRate(matcher.group(1),
                                             Integer.parseInt(matcher.group(2)),
                                             Arrays.stream(matcher.group(3).split(", ")).toList()))
                .collect(Collectors.toMap(valve -> valve.name, Function.identity()));
    }

    @Override
    protected Long partOne(Map<String, FlowRate> input) {

        FlowRate flowRate = input.get("AA");

        HashMap<String, Integer> map = new HashMap<>();

        ArrayList<String> a = new ArrayList<>();
        a.add("BB");
        a.add("CC");
        a.add("DD");
        a.add("EE");
        a.add("HH");
        a.add("JJ");

        List<Integer> integers = bfsHope(input, new ArrayList<>(), "AA", 30, 0).stream().sorted(Comparator.reverseOrder()).toList();

        List<List<String>> aa = x(input, List.of(""), "AA", 30, map);

        List<Integer> aax = xx(input, List.of(""), "AA", 30, map);

        map = new HashMap<>();

        x(input, List.of(""), "JJ", 27, map);

        map = new HashMap<>();

        x(input, List.of(""), "DD", 23, map);

        map = new HashMap<>();

        x(input, List.of(""), "HH", 18, map);

        map = new HashMap<>();

        x(input, List.of(""), "BB", 11, map);

        // A -> J -> D -> H -> B -> E
        // 0 -> 567 -> 460 -> 396 -> 143 -> 18

        // A -> D -> B -> J -> H -> E
        // 0 -> 560 -> 325 -> 441 -> 286 -> 27

        return null;
    }

    private List<Integer> bfsHope(Map<String, FlowRate> input, List<String> openedValves, String valve, int minutesRemaining,
                                  int pressureFrom) {

        if (/*unvisitedNodes.isEmpty() || */minutesRemaining <= 0) {

            return List.of(pressureFrom);
        }

        FlowRate flowRate = input.get(valve);
        ArrayList<Integer> x = new ArrayList<>();

        //unvisitedNodes.remove(valve);
        minutesRemaining--;

        if (openedValves.contains(valve)) {

            for (String s : flowRate.valves) {

                List<Integer> integersNoOpening = bfsHope(input, openedValves, s, minutesRemaining, pressureFrom);
                x.addAll(integersNoOpening);
            }
        } else {

            if (flowRate.flowRate == 0) {

                openedValves.add(valve);
                for (String s : flowRate.valves) {

                    List<Integer> integersNoOpening = bfsHope(input, openedValves, s, minutesRemaining, pressureFrom);
                    x.addAll(integersNoOpening);
                }
            } else {

                // open valve
                openedValves.add(valve);
                int pressure = flowRate.flowRate * (minutesRemaining - 1) + pressureFrom;
                for (String s : flowRate.valves) {

                    List<Integer> integers = bfsHope(input, openedValves, s, minutesRemaining - 1, pressure);
                    x.addAll(integers);
                }

                // do not open valve
                for (String s : flowRate.valves) {

                    List<Integer> integersNoOpening = bfsHope(input, openedValves, s, minutesRemaining, pressureFrom);
                    x.addAll(integersNoOpening);
                }
            }
        }

        return x;
    }

    private List<List<String>> x(Map<String, FlowRate> input, List<String> way, String valve, int minutesRemaining,
                                 Map<String, Integer> cost) {

        if (way.contains(valve)) {
            return List.of(way);
        }

        ArrayList<List<String>> xx = new ArrayList<>();
        List<String> newWay = Stream.concat(way.stream(), Stream.of(valve)).toList();

        for (String s : input.get(valve).valves) {

            List<List<String>> x = x(input, newWay, s, minutesRemaining - 1, cost);

            xx.addAll(x);
        }
        cost.merge(valve, (minutesRemaining - 1) * input.get(valve).flowRate, Math::max);

        return xx;
    }

    private List<Integer> xx(Map<String, FlowRate> input, List<String> way, String valve, int minutesRemaining,
                             Map<String, Integer> cost) {

        if (way.contains(valve)) {
            return List.of((minutesRemaining - 1) * input.get(valve).flowRate);
        }

        ArrayList<Integer> xx = new ArrayList<>();
        List<String> newWay = Stream.concat(way.stream(), Stream.of(valve)).toList();

        for (String s : input.get(valve).valves) {

            List<Integer> x = xx(input, newWay, s, minutesRemaining - 1, cost);

            xx.addAll(x);
        }
        cost.merge(valve, (minutesRemaining - 1) * input.get(valve).flowRate, Math::max);

        return xx;
    }

    @Override
    protected Integer partTwo(Map<String, FlowRate> input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day16";
    }
}
