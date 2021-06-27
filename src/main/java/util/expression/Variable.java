package util.expression;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Tibor Racman
 * ADVANCED PROGRAMMING PROJECT 2020/21 - A Class modeling a node associated with a variable in a mathematical expression
 */

public class Variable extends Node {
  private final String name;

  public Variable(String name) {
    super(Collections.emptyList());
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Variable variable = (Variable) o;
    return Objects.equals(name, variable.name);
  }

  @Override
  public double calculate(Map<String, Double> tuple) throws IllegalArgumentException {
    for (String variable : tuple.keySet()) {
      if (variable.equals(this.getName())) {
        return tuple.get(variable);
      }
    }
    throw new IllegalArgumentException("Unknown Variable " + this.getName());

  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }

  @Override
  public String toString() {
    return name;
  }


}