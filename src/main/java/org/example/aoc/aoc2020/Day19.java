package org.example.aoc.aoc2020;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Day19 extends AoC2020Day<Day19.Input> {

    private interface RuleDefinition {
    }

    private record CharacterRule(Character c) implements RuleDefinition {
    }

    private record Rule(List<Long> identifiers) implements RuleDefinition {
    }

    protected record Input(Map<Long, ? extends List<? extends RuleDefinition>> rules, List<String> messages) {
    }

    @Override
    protected Input parseInput(String strInput) {

        String[] split = strInput.split(System.lineSeparator() + "{2}");

        final Map<Long, ? extends List<? extends RuleDefinition>> rules = parseRules(split[0]);
        final List<String> messages = split[1].lines().toList();

        return new Input(rules, messages);
    }

    private Map<Long, ? extends List<? extends RuleDefinition>> parseRules(String rules) {

        return rules.lines()
                .map(line -> {
                    final String[] split = line.split(": ");
                    final long identifier = Long.parseLong(split[0]);

                    if (split[1].startsWith("\"")) {

                        return Map.entry(identifier, List.of(new CharacterRule(split[1].charAt(1))));
                    } else {

                        final List<Rule> lists = Arrays.stream(split[1].split(" \\| "))
                                .map(identifiers -> Arrays.stream(identifiers.split(" "))
                                        .map(Long::parseLong)
                                        .toList())
                                .map(Rule::new)
                                .toList();

                        return Map.entry(identifier, lists);
                    }
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    protected Long partOne(Input input) {

        return input.messages.stream()
                .filter(message -> {
                    int i = match(input.rules.get(0L), input.rules, message, 0);

                    return i != -1 && i == message.length();
                })
                .count();
    }

    private int match(List<? extends RuleDefinition> ruleDefinitions,
                      Map<Long, ? extends List<? extends RuleDefinition>> rules,
                      String message,
                      int index) {

        for (RuleDefinition ruleDefinition : ruleDefinitions) {

            if (ruleDefinition instanceof CharacterRule characterRule) {

                return index < message.length() && characterRule.c() == message.charAt(index) ? index + 1 : -1;
            } else if (ruleDefinition instanceof Rule rule) {

                boolean failed = false;
                int indexAux = index;

                for (Long identifier : rule.identifiers) {

                    int i = match(rules.get(identifier), rules, message, indexAux);

                    if (i == -1) {

                        failed = true;
                        break;
                    }

                    indexAux = i;
                }

                if (!failed) {

                    return indexAux;
                }
            }
        }

        return -1;
    }

    @Override
    protected Long partTwo(Input input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day19";
    }
}
